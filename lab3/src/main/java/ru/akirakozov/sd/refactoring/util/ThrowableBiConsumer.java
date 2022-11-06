package ru.akirakozov.sd.refactoring.util;

public interface ThrowableBiConsumer<T, U> {
    void accept(T t, U u) throws Exception;
}
