package com.github.tachesimazzoca.google.cli.core;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

public class Request {

    private static final Option HELP = Option.builder("h")
            .longOpt("help").desc("Print help").build();

    private static final String DEFAULT_USER_ID = "user";
    private static final Option USER_ID = Option.builder("u")
            .longOpt("user").hasArg().desc(
                    String.format("An user ID to be authorized (default: %s)", DEFAULT_USER_ID))
            .build();

    private static final Option[] SUPPORTED_OPTIONS = new Option[]{
            HELP,
            USER_ID
    };

    public static Option[] getSupportedOptions() {
        return SUPPORTED_OPTIONS;
    }

    private final CommandLine commandLine;
    private final String[] subCommands;
    private final String[] args;

    public Request(CommandLine commandLine) {
        this.commandLine = commandLine;
        String[] cmdArgs = commandLine.getArgs();
        if (cmdArgs.length >= 2) {
            args = new String[cmdArgs.length - 2];
            subCommands = new String[]{cmdArgs[0], cmdArgs[1]};
            if (args.length > 0)
                System.arraycopy(cmdArgs, 2, args, 0, args.length);
        } else if (cmdArgs.length == 1) {
            subCommands = new String[]{cmdArgs[0]};
            args = new String[0];
        } else {
            subCommands = new String[0];
            args = new String[0];
        }
    }

    public String[] getSubCommands() {
        return subCommands;
    }

    public String[] getArgs() {
        return args;
    }

    public boolean needsHelp() {
        return commandLine.hasOption(HELP.getOpt());
    }

    public String getUserId() {
        return getUserId(DEFAULT_USER_ID);
    }

    public String getUserId(String defaultValue) {
        return commandLine.getOptionValue(USER_ID.getOpt(), defaultValue);
    }
}
