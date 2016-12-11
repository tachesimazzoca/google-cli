package com.github.tachesimazzoca.google.cli.oauth.command;

import com.github.tachesimazzoca.google.cli.core.Command;
import com.github.tachesimazzoca.google.cli.core.Environment;
import com.github.tachesimazzoca.google.cli.core.Request;

public class Usage implements Command {
    @Override
    public String getArgumentSyntax() {
        return "(authorize) ...";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public void execute(Environment environment, Request request) {
        throw new UnsupportedOperationException();
    }
}
