package ru.akirakozov.sd.refactoring.db.update;

public final class CreateTableUpdate extends Update {
    @Override
    protected String query() {
        return "CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)";
    }
}
