package com.github.tachesimazzoca.google.cli.oauth;

import com.github.tachesimazzoca.google.cli.core.Environment;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;

import java.io.IOException;
import java.util.List;

public class Authorizer {

    private static final String ACCESS_TYPE = "offline";

    private final Environment environment;
    private final VerificationCodeReceiverProvider verificationCodeReceiverProvider;

    public Authorizer(
            Environment environment,
            VerificationCodeReceiverProvider verificationCodeReceiverProvider) {
        this.environment = environment;
        this.verificationCodeReceiverProvider = verificationCodeReceiverProvider;
    }

    public Credential authorize(String userId, List<String> scopes) throws IOException {
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                environment.getHttpTransport(),
                environment.getJsonFactory(),
                environment.getClientSecrets(), scopes)
                .setDataStoreFactory(environment.getDataStoreFactory())
                .setAccessType(ACCESS_TYPE)
                .build();
        return new AuthorizationCodeInstalledApp(
                flow, verificationCodeReceiverProvider.provide()).authorize(userId);
    }
}
