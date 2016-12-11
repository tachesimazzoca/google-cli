package com.github.tachesimazzoca.google.cli.oauth;

import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;

public interface VerificationCodeReceiverProvider {
    VerificationCodeReceiver provide();
}
