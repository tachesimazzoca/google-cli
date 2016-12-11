package com.github.tachesimazzoca.google.cli.core;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Environment {

    private final HttpTransport httpTransport;
    private final JsonFactory jsonFactory;
    private final DataStoreFactory dataStoreFactory;
    private final GoogleClientSecrets clientSecrets;

    private Environment(
            HttpTransport httpTransport,
            JsonFactory jsonFactory,
            DataStoreFactory dataStoreFactory,
            GoogleClientSecrets clientSecrets) {
        this.httpTransport = httpTransport;
        this.jsonFactory = jsonFactory;
        this.dataStoreFactory = dataStoreFactory;
        this.clientSecrets = clientSecrets;
    }

    public HttpTransport getHttpTransport() {
        return httpTransport;
    }

    public JsonFactory getJsonFactory() {
        return jsonFactory;
    }

    public DataStoreFactory getDataStoreFactory() {
        return dataStoreFactory;
    }

    public GoogleClientSecrets getClientSecrets() {
        return clientSecrets;
    }

    public static Environment create(Config config) throws IOException {
        File secret = new File(config.getString("GoogleAPI.clientSecretPath"));
        File dataStoreDir = new File(config.getString("GoogleAPI.dataStoreDirectoryPath"));

        return new Environment.Builder()
                .setDataStoreFactory(new FileDataStoreFactory(dataStoreDir))
                .setClientSecrets(secret)
                .build();
    }

    public static Environment create() throws IOException {
        return create(ConfigFactory.load());
    }

    public static class Builder {
        private HttpTransport httpTransport;
        private JsonFactory jsonFactory;
        private DataStoreFactory dataStoreFactory;
        private GoogleClientSecrets clientSecrets;

        public Builder() {
            httpTransport = null;
            jsonFactory = null;
            dataStoreFactory = null;
            clientSecrets = null;
        }

        public Environment build() {
            try {
                Preconditions.checkState(null != dataStoreFactory);
                Preconditions.checkState(null != clientSecrets);

                if (null == httpTransport) {
                    httpTransport = GoogleNetHttpTransport.newTrustedTransport();
                }
                if (null == jsonFactory) {
                    jsonFactory = JacksonFactory.getDefaultInstance();
                }

                return new Environment(httpTransport, jsonFactory, dataStoreFactory, clientSecrets);

            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public Builder setHttpTransport(HttpTransport httpTransport) {
            this.httpTransport = httpTransport;
            return this;
        }

        public Builder setJsonFactory(JsonFactory jsonFactory) {
            this.jsonFactory = jsonFactory;
            return this;
        }

        public Builder setDataStoreFactory(DataStoreFactory dataStoreFactory) {
            this.dataStoreFactory = dataStoreFactory;
            return this;
        }

        public Builder setClientSecrets(GoogleClientSecrets clientSecrets) {
            this.clientSecrets = clientSecrets;
            return this;
        }

        public Builder setClientSecrets(InputStream in) throws IOException {
            JsonFactory jf;
            if (null != jsonFactory)
                jf = jsonFactory;
            else
                jf = JacksonFactory.getDefaultInstance();
            this.clientSecrets = GoogleClientSecrets.load(jf, new InputStreamReader(in));
            return this;
        }

        public Builder setClientSecrets(File path) throws IOException {
            return setClientSecrets(new FileInputStream(path));
        }
    }
}
