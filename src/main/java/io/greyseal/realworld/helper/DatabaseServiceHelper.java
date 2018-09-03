package io.greyseal.realworld.helper;


import io.greyseal.realworld.service.DatabaseService;

public enum DatabaseServiceHelper {
    INSTANCE;

    private DatabaseService dbService;

    public DatabaseService getDbService() {
        return dbService;
    }

    public void setDbService(final DatabaseService dbService) {
        this.dbService = dbService;
    }

    public io.greyseal.realworld.service.reactivex.DatabaseService getRxDBService() {
        return new io.greyseal.realworld.service.reactivex.DatabaseService(dbService);
    }
}