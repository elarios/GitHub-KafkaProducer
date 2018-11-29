package com.codefeedr.kafka;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;
import java.util.*;

public class GitHubSourceSchemas {

    public static String NEXT_PAGE_FIELD = "next_page";


    // Event fields
    public static String EVENT_TYPE_FIELD = "type";
    public static String EVENT_PUBLIC_FIELD = "public";
    public static String EVENT_CREATED_AT_FIELD = "created_at";
    public static String EVENT_ID_FIELD = "id";

    // Repo fields
    public static String REPO_FIELD = "repo";
    public static String REPO_ID_FIELD = "id";
    public static String REPO_NAME_FIELD = "name";

    // Actor fields
    public static String ACTOR_FIELD = "actor";
    public static String ACTOR_ID_FIELD = "id";
    public static String ACTOR_LOGIN_FIELD = "login";
    public static String ACTOR_AVATAR_URL_FIELD = "avatar_url";
    public static String DISPLAY_LOGIN_FIELD = "display_login";

    // Org fields
    public static String ORG_FIELD = "org";
    public static String ORG_ID_FIELD = "id";
    public static String ORG_LOGIN_FIELD = "login";


    // Author fields
    public static String AUTHOR_FIELD = "author";
    public static String AUTHOR_EMAIL_FIELD = "email";
    public static String AUTHOR_NAME_FIELD = "name";

    // Commits fields
    public static String COMMIT_SHA_FIELD = "sha";
    public static String COMMIT_MESSAGE_FIELD = "message";
    public static String COMMIT_DISTINCT_FIELD = "distinct";

    // Payload fields
    public static String PAYLOAD_FIELD = "payload";
    public static String PAYLOAD_PUSH_ID_FIELD = "push_id";
    public static String PAYLOAD_SIZE_FIELD = "size";
    public static String PAYLOAD_DISTINCT_SIZE_FIELD = "distinct_size";
    public static String PAYLOAD_REF_FIELD = "ref";
    public static String PAYLOAD_HEAD_FIELD = "head";
    public static String PAYLOAD_BEFORE_FIELD = "before";
    public static String PAYLOAD_COMMITS_FIELD = "commits";

    // Schema name
    public static String SCHEMA_KEY = "event_key";

    // Key Schema
    public static Schema KEY_SCHEMA = SchemaBuilder.struct().name(SCHEMA_KEY)
            .version(1)
            .field(EVENT_ID_FIELD, Schema.STRING_SCHEMA)
            .field(EVENT_TYPE_FIELD, Schema.STRING_SCHEMA)
            .build();


}
