package com.github.tachesimazzoca.google.cli.sheets.command;

import com.github.tachesimazzoca.google.cli.core.Command;
import com.github.tachesimazzoca.google.cli.core.Environment;
import com.github.tachesimazzoca.google.cli.core.Request;
import com.github.tachesimazzoca.google.cli.oauth.Authorizer;
import com.github.tachesimazzoca.google.cli.oauth.LocalServerReceiverProvider;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.util.Arrays;
import java.util.List;

public class GetCommand implements Command {

    private static final String APPLICATION_NAME = GetCommand.class.getName();
    private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY);

    @Override
    public String getArgumentSyntax() {
        return "<sheetId> <query>";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public void execute(Environment environment, Request request) {

        String[] args = request.getArgs();
        if (2 > args.length) {
            throw new IllegalArgumentException(
                    "The following arguments are required: " + getArgumentSyntax());
        }
        String sheetId = args[0];
        String query = args[1];

        try {
            String userId = request.getUserId();
            Authorizer authorizer = new Authorizer(environment, new LocalServerReceiverProvider());
            Credential credential = authorizer.authorize(userId, SCOPES);

            Sheets sheets = new Sheets.Builder(
                    environment.getHttpTransport(), environment.getJsonFactory(), credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            ValueRange valueRange = sheets.spreadsheets().values()
                    .get(sheetId, query)
                    .execute();
            List<List<Object>> rows = valueRange.getValues();
            if (null != rows) {
                for (List<Object> columns : rows) {
                    System.out.println(formatLine(columns, "\t"));
                }
            }

        } catch (Throwable e) {
            throw new Error(e);
        }
    }

    private static String formatLine(List<Object> items, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            Object v = items.get(i);
            if (i > 0) {
                sb.append(separator);
            }
            if (null == v) {
                sb.append("");
            } else {
                String s = v.toString()
                        .replace("\t", "")
                        .replace("\r", "")
                        .replace("\n", "");
                sb.append(s);
            }
        }
        return sb.toString();
    }
}
