package com.github.tachesimazzoca.google.cli;

import com.github.tachesimazzoca.google.cli.core.Command;
import com.github.tachesimazzoca.google.cli.core.Environment;
import com.github.tachesimazzoca.google.cli.core.Request;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Main {
    private static final String MAIN_COMMAND_NAME = "google";

    public static void main(String[] args) {
        try {
            Options options = new Options();
            for (Option opt : Request.getSupportedOptions()) {
                options.addOption(opt);
            }
            CommandLineParser parser = new DefaultParser();
            CommandLine commandLine = parser.parse(options, args);

            Request request = new Request(commandLine);
            String[] subCommands = request.getSubCommands();
            Command command = dispatchCommand(subCommands);
            boolean isUsage = command.getClass().getSimpleName().equals("Usage");

            if (request.needsHelp() || isUsage) {
                HelpFormatter helpFormatter = new HelpFormatter();
                helpFormatter.printHelp(
                        makeCommandLineSyntax(subCommands, command.getArgumentSyntax()),
                        command.getDescription(), options, "");
            } else {
                Environment environment = Environment.create();
                command.execute(environment, request);
            }

        } catch (Throwable e) {
            throw new Error(e);
        }
    }

    private static Command dispatchCommand(String[] subCommands) {
        try {
            String pkg = Main.class.getPackage().getName();
            String className;
            if (subCommands.length >= 2) {
                className = String.format(
                        "%s.%s.command.%sCommand", pkg,
                        asCommandGroup(subCommands[0]), asCommandClassName(subCommands[1]));
            } else if (subCommands.length == 1) {
                className = String.format(
                        "%s.%s.command.Usage", pkg, asCommandGroup(subCommands[0]));
            } else {
                className = String.format("%s.command.Usage", pkg);
            }
            Class<?> clz = Class.forName(className);
            return (Command) clz.newInstance();

        } catch (Throwable e) {
            throw new Error(e);
        }
    }

    private static String asCommandGroup(String str) {
        if (!str.matches("^[a-z][0-9a-z]*$"))
            throw new IllegalArgumentException("Invalid sub command group");
        return str;
    }

    private static String asCommandClassName(String str) {
        if (!str.matches("^[a-z][0-9a-zA-Z]+$"))
            throw new IllegalArgumentException("Invalid sub command name");
        return ucFirst(str);
    }

    private static String ucFirst(String str) {
        String first = str.substring(0, 1).toUpperCase();
        String trailing = str.substring(1, str.length());
        return first + trailing;
    }

    private static String makeCommandLineSyntax(String[] subCommands, String argumentsSyntax) {
        StringBuilder sb = new StringBuilder();
        sb.append(MAIN_COMMAND_NAME);
        for (String s : subCommands) {
            sb.append(" ");
            sb.append(s);
        }
        sb.append(" ");
        sb.append(argumentsSyntax);
        return sb.toString();
    }
}
