package com.codefeedr.kafka;

import com.github.*;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;
import java.util.*;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import static com.codefeedr.kafka.GitHubSourceSchemas.*;


public class GitHubSourceTask extends SourceTask {
    private static final Logger log = LoggerFactory.getLogger(GitHubSourceTask.class);
    public GitHubSourceConnectorConfig config;

    protected Instant nextQuerySince;
    protected Integer lastEventNumber;
    protected Integer nextPageToVisit = 1;
    protected Instant lastCreatedAt;
    protected String lastEventType = "";
    protected String lastEventId = "";

    GitHubSourceAPIHttpClient gitHubHttpAPIClient;

    @Override
    public String version() {
        return VersionUtil.getVersion();
    }

    @Override
    public void start(Map<String, String> map) {
        //Do things here that are required to start your task. This could be open a connection to a database, etc.
        config = new GitHubSourceConnectorConfig(map);
        initializeLastVariables();
        gitHubHttpAPIClient = new GitHubSourceAPIHttpClient(config);
    }

    private void initializeLastVariables(){
        Map<String, Object> lastSourceOffset = null;

        lastSourceOffset = context.offsetStorageReader().offset(sourcePartition(lastEventType, lastEventId));

        if (lastSourceOffset == null) {
            // we haven't fetched anything yet, so we initialize to 7 days ago
            nextQuerySince = config.getSince();
            lastEventNumber = -1;
        } else {

            Object createdAt = lastSourceOffset.get(GitHubSourceSchemas.EVENT_CREATED_AT_FIELD);
            Object eventNumber = lastSourceOffset.get(GitHubSourceSchemas.EVENT_ID_FIELD);
            Object nextPage = lastSourceOffset.get(GitHubSourceSchemas.NEXT_PAGE_FIELD);
            if (createdAt != null && (createdAt instanceof String)) {
                nextQuerySince = Instant.parse((String) createdAt);
            }
            if (eventNumber != null && (eventNumber instanceof String)) {
                lastEventNumber = Integer.valueOf((String) eventNumber);
            }
            if (nextPage != null && (nextPage instanceof String)) {
                nextPageToVisit = Integer.valueOf((String) nextPage);
            }

        }

    }


    @Override
    public List<SourceRecord> poll() throws InterruptedException {

        gitHubHttpAPIClient.sleepIfNeed();
        // fetch data
        final ArrayList<SourceRecord> records = new ArrayList<>();

        Properties properties = new Properties();
        // normal producer
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        properties.setProperty("acks", "all");
        properties.setProperty("retries", "10");
        // avro part
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
        properties.setProperty("schema.registry.url", "http://127.0.0.1:8081");


        // fetch data
        Producer<String, PushEvent> producer = new KafkaProducer<String, PushEvent>(properties);

        String topic = "github-events";

        JSONArray lisEvents = gitHubHttpAPIClient.getNextEvents(nextPageToVisit, nextQuerySince);
        // we'll count how many results we get with i
        int i = 0;
        for (Object obj : lisEvents) {

            Repo repo = Repo.newBuilder()
                    .setId(((JSONObject) obj).getJSONObject(REPO_FIELD).getInt(REPO_ID_FIELD))
                    .setName(((JSONObject) obj).getJSONObject(REPO_FIELD).getString(REPO_NAME_FIELD))
                    .build();

            Actor actor = Actor.newBuilder()
                    .setId(((JSONObject) obj).getJSONObject(ACTOR_FIELD).getInt(ACTOR_ID_FIELD))
                    .setLogin(((JSONObject) obj).getJSONObject(ACTOR_FIELD).getString(ACTOR_LOGIN_FIELD))
                    .setAvatarUrl(((JSONObject) obj).getJSONObject(ACTOR_FIELD).getString(ACTOR_AVATAR_URL_FIELD))
                    .build();

            Organization org = Organization.newBuilder()
                    .setId(((JSONObject) obj).getJSONObject(ORG_FIELD).getInt(ORG_ID_FIELD))
                    .setLogin(((JSONObject) obj).getJSONObject(ORG_FIELD).getString(ORG_LOGIN_FIELD))
                    .build();


            JSONArray listCommits = ((JSONObject) obj).getJSONArray(PAYLOAD_COMMITS_FIELD);
            List<PushCommit> commits = new ArrayList<>();
            /*
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
            */
            Payload payload = Payload.newBuilder()
                    .setPushId(((JSONObject) obj).getJSONObject(PAYLOAD_FIELD).getLong(PAYLOAD_PUSH_ID_FIELD))
                    .setSize(((JSONObject) obj).getJSONObject(PAYLOAD_FIELD).getInt(PAYLOAD_SIZE_FIELD))
                    .setDistinctSize(((JSONObject) obj).getJSONObject(PAYLOAD_FIELD).getInt(PAYLOAD_DISTINCT_SIZE_FIELD))
                    .setRef(((JSONObject) obj).getJSONObject(PAYLOAD_FIELD).getString(PAYLOAD_REF_FIELD))
                    .setHead(((JSONObject) obj).getJSONObject(PAYLOAD_FIELD).getString(PAYLOAD_HEAD_FIELD))
                    .setBefore(((JSONObject) obj).getJSONObject(PAYLOAD_FIELD).getString(PAYLOAD_BEFORE_FIELD))
                    .setCommits(commits)
                    .build();


            PushEvent event = PushEvent.newBuilder()
                    .setPublic$(((JSONObject) obj).getBoolean(EVENT_PUBLIC_FIELD))
                    .setRepo(repo)
                    .setActor(actor)
                    .setOrg(org)
                    .setCreatedAt(((JSONObject) obj).getString(EVENT_CREATED_AT_FIELD))
                    .setId(((JSONObject) obj).getString(EVENT_ID_FIELD))
                    .setPayload(payload)
                    .build();

            ProducerRecord<String, PushEvent> producerRecord = new ProducerRecord<String, PushEvent>(
                    topic, event
            );

            System.out.println(event);
            producer.send(producerRecord, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        System.out.println(metadata);
                    } else {
                        exception.printStackTrace();
                    }
                }
            });

            i += 1;
            lastCreatedAt = Instant.parse(event.getCreatedAt());
            lastEventId = event.getId();

            producer.flush();
            producer.close();

        }

        if (i > 0) log.info(String.format("Fetched %s record(s)", i));
        if (i == 100){
            // we have reached a full batch, we need to get the next one
            nextPageToVisit += 1;
        }
        else {
            nextQuerySince = lastCreatedAt.plusSeconds(1);
            nextPageToVisit = 1;
            gitHubHttpAPIClient.sleep();
        }

        return records;
    }

    @Override
    public void stop() {
        // Do whatever is required to stop your task.
    }

    private Map<String, String> sourcePartition(String sType, String eventId) {
        Map<String, String> map = new HashMap<>();
        map.put(EVENT_TYPE_FIELD, sType);
        map.put(GitHubSourceSchemas.EVENT_ID_FIELD, eventId);
        return map;
    }
}