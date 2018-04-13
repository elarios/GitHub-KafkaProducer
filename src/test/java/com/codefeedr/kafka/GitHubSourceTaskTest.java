package com.codefeedr.kafka;

import com.github.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.time.Instant;
import java.util.*;

import static com.codefeedr.kafka.GitHubSourceSchemas.*;

public class GitHubSourceTaskTest {

    GitHubSourceTask gitHubSourceTask = new GitHubSourceTask();
    private Integer batchSize = 100;

    private Map<String, String> initialConfig() {
        Map<String, String> baseProps = new HashMap<>();
        baseProps.put(GitHubSourceConnectorConfig.OWNER_CONFIG, "apache");
        baseProps.put(GitHubSourceConnectorConfig.REPO_CONFIG, "kafka");
        baseProps.put(GitHubSourceConnectorConfig.SINCE_CONFIG, "2017-01-01T01:23:44Z");
        baseProps.put(GitHubSourceConnectorConfig.BATCH_SIZE_CONFIG, batchSize.toString());
        baseProps.put(GitHubSourceConnectorConfig.TOPIC_CONFIG, "github-events");
        return baseProps;
    }

    @Test
    public void test() throws UnirestException {

        Properties properties = new Properties();
        // normal producer
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        properties.setProperty("acks", "all");
        properties.setProperty("retries", "0");
        // avro part
        properties.setProperty("key.serializer", KafkaAvroSerializer.class.getName());
        properties.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
        properties.setProperty("schema.registry.url", "http://127.0.0.1:8081");

        gitHubSourceTask.config = new GitHubSourceConnectorConfig(initialConfig());
        gitHubSourceTask.nextPageToVisit = 1;
        gitHubSourceTask.nextQuerySince = Instant.parse("2017-01-01T00:00:00Z");
        gitHubSourceTask.gitHubHttpAPIClient = new GitHubSourceAPIHttpClient(gitHubSourceTask.config);
        String url = gitHubSourceTask.gitHubHttpAPIClient.constructUrl(gitHubSourceTask.nextPageToVisit,
                gitHubSourceTask.nextQuerySince);
        System.out.println(url);
        HttpResponse<JsonNode> httpResponse = gitHubSourceTask.gitHubHttpAPIClient.getNextEventsAPI(gitHubSourceTask.nextPageToVisit,
                gitHubSourceTask.nextQuerySince);

        Producer<String, PushEvent> producer = new KafkaProducer<String, PushEvent>(properties);

        String topic = "github-events";

        if (httpResponse.getStatus() != 403) {

            for (int i = 0; i < httpResponse.getBody().getArray().length() ; i++) {

                JSONObject obj = (JSONObject) httpResponse.getBody().getArray().get(i);

                Repo repo = Repo.newBuilder()
                        .setId(obj.getJSONObject(REPO_FIELD).getInt(REPO_ID_FIELD))
                        .setName(obj.getJSONObject(REPO_FIELD).getString(REPO_NAME_FIELD))
                        .build();

                Actor actor = Actor.newBuilder()
                        .setId(obj.getJSONObject(ACTOR_FIELD).getInt(ACTOR_ID_FIELD))
                        .setLogin(obj.getJSONObject(ACTOR_FIELD).getString(ACTOR_LOGIN_FIELD))
                        .setAvatarUrl(obj.getJSONObject(ACTOR_FIELD).getString(ACTOR_AVATAR_URL_FIELD))
                        .setDisplayLogin(obj.getJSONObject(ACTOR_FIELD).getString(DISPLAY_LOGIN_FIELD))
                        .build();

                Organization org = null;

                if (obj.has(ORG_FIELD)) {

                    org = Organization.newBuilder()
                            .setId(obj.getJSONObject(ORG_FIELD).getInt(ORG_ID_FIELD))
                            .setLogin(obj.getJSONObject(ORG_FIELD).getString(ORG_LOGIN_FIELD))
                            .build();

                }

                Payload payload = null;

                if (obj.getString(EVENT_TYPE_FIELD).equals("PushEvent")) {

                    List<PushCommit> commits = new ArrayList<>();

                    if (obj.getJSONObject(PAYLOAD_FIELD).has(PAYLOAD_COMMITS_FIELD)) {

                        JSONArray listCommits = obj.getJSONObject(PAYLOAD_FIELD).getJSONArray(PAYLOAD_COMMITS_FIELD);

                        for (Object o : listCommits) {

                            JSONObject jsonCommit = (JSONObject) o;

                            PushAuthor author = PushAuthor.newBuilder()
                                    .setEmail(jsonCommit.getJSONObject(AUTHOR_FIELD).getString(AUTHOR_EMAIL_FIELD))
                                    .setName(jsonCommit.getJSONObject(AUTHOR_FIELD).getString(AUTHOR_NAME_FIELD))
                                    .build();

                            PushCommit commit = PushCommit.newBuilder()
                                    .setSha(jsonCommit.getString(COMMIT_SHA_FIELD))
                                    .setAuthor(author)
                                    .setMessage(jsonCommit.getString(COMMIT_MESSAGE_FIELD))
                                    .setDistinct(jsonCommit.getBoolean(COMMIT_DISTINCT_FIELD))

                                    .build();

                            commits.add(commit);
                        }
                    }

                    payload = Payload.newBuilder()
                            .setPushId(obj.getJSONObject(PAYLOAD_FIELD).getInt(PAYLOAD_PUSH_ID_FIELD))
                            .setSize(obj.getJSONObject(PAYLOAD_FIELD).getInt(PAYLOAD_SIZE_FIELD))
                            .setDistinctSize(obj.getJSONObject(PAYLOAD_FIELD).getInt(PAYLOAD_DISTINCT_SIZE_FIELD))
                            .setRef(obj.getJSONObject(PAYLOAD_FIELD).getString(PAYLOAD_REF_FIELD))
                            .setHead(obj.getJSONObject(PAYLOAD_FIELD).getString(PAYLOAD_HEAD_FIELD))
                            .setBefore(obj.getJSONObject(PAYLOAD_FIELD).getString(PAYLOAD_BEFORE_FIELD))
                            .setCommits(commits)
                            .build();

                }

                PushEvent event = PushEvent.newBuilder()
                        .setPublic$(obj.getBoolean(EVENT_PUBLIC_FIELD))
                        .setRepo(repo)
                        .setActor(actor)
                        .setOrg(org)
                        .setCreatedAt(obj.getString(EVENT_CREATED_AT_FIELD))
                        .setId(obj.getString(EVENT_ID_FIELD))
                        .setPayload(payload)
                        .build();

                ProducerRecord<String, PushEvent> producerRecord = new ProducerRecord<String, PushEvent>(
                        topic, event.getId(), event);

                producer.send(producerRecord, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception exception) {
                        if (exception == null) {
                            System.out.println(metadata);
                        } else {
                            System.out.println("Error producing to topic " + metadata.topic());
                            exception.printStackTrace();
                        }
                    }
                });

                System.out.println("GitHub Event: " + event.toString());
            }

        }
        producer.close();
    }
}