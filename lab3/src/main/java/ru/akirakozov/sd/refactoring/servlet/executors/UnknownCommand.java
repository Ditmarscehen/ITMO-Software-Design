package ru.akirakozov.sd.refactoring.servlet.executors;

import java.util.List;

public final class UnknownCommand extends ServletExecutor {
    private final String command;

    public UnknownCommand(String command) {
        this.command = command;
    }

    @Override
    List<String> getBody() {
        return List.of("Unknown command: " + command);
    }
}
