package com.github.tachesimazzoca.google.cli.oauth;

import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;

public class LocalServerReceiverProvider implements VerificationCodeReceiverProvider {
    private final String host;
    private final int port;

    public LocalServerReceiverProvider() {
        host = "localhost";
        port = -1;
    }

    public LocalServerReceiverProvider(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public VerificationCodeReceiver provide() {
        return new LocalServerReceiver.Builder()
                .setHost(host)
                .setPort(port)
                .build();
    }
}
