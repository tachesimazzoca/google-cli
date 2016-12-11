package com.github.tachesimazzoca.google.cli.oauth.command;

import com.github.tachesimazzoca.google.cli.core.Command;
import com.github.tachesimazzoca.google.cli.core.Environment;
import com.github.tachesimazzoca.google.cli.core.Request;
import com.github.tachesimazzoca.google.cli.oauth.Authorizer;
import com.github.tachesimazzoca.google.cli.oauth.LocalServerReceiverProvider;
import com.google.api.client.auth.oauth2.Credential;

import java.util.Arrays;

/**
 * This command will launch a LocalServerReceiver (Jetty) to receive
 * an OAuth2.0 token from Google Sign-in.
 * <ul>
 * <oi>Open a redirect URL, printed out to STDOUT, with your web browser. Or
 * your default browser will launch with the URL automatically)</oi>
 * <oi>Allow this application to access the Google contents. Make sure
 * the contents includes all the private data that you can read.</oi>
 * <oi>Your browser will redirect to the LocalServerReceiver with an OAuth2.0 token.</oi>
 * <oi>A credential data "StoredCredential" will be stored under the specified directory</oi>
 * </ul>
 */
public class AuthorizeCommand implements Command {

    @Override
    public String getArgumentSyntax() {
        return "<scope> ...";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public void execute(Environment environment, Request request) {

        String[] scopes = request.getArgs();
        if (scopes.length == 0) {
            throw new IllegalArgumentException("One or more Google API scopes are required.");
        }

        try {
            String userId = request.getUserId();
            Authorizer authorizer = new Authorizer(environment, new LocalServerReceiverProvider());
            Credential credential = authorizer.authorize(userId, Arrays.asList(scopes));

            System.out.println("AccessToken: " + credential.getAccessToken());
            System.out.println("RefreshToken: " + credential.getRefreshToken());

        } catch (Throwable e) {
            throw new Error(e);
        }
    }
}
