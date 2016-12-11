package com.github.tachesimazzoca.google.cli.core;

public interface Command {
    void execute(Environment environment, Request request);

    String getArgumentSyntax();

    String getDescription();
}
