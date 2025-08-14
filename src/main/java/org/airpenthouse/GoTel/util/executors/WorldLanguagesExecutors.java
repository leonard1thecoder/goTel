package org.airpenthouse.GoTel.util.executors;


import lombok.Getter;
import lombok.Setter;
import org.airpenthouse.GoTel.dtos.languages.LanguageRequest;
import org.airpenthouse.GoTel.entities.languanges.WorldLanguagesEntity;
import org.airpenthouse.GoTel.services.language.WorldLanguagesService;
import org.airpenthouse.GoTel.util.CountApiUsers;
import org.airpenthouse.GoTel.util.mappers.LanguageMapper;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.*;


public class WorldLanguagesExecutors extends CountApiUsers {

    @Setter
    @Getter
    private static LanguageMapper languageMapper;

    @Getter
    private WorldLanguagesEntity entity;
    @Getter
    @Setter
    private WorldLanguagesService service;

    protected WorldLanguagesExecutors() {

    }

    public ExecutorService worldLanguagesExecutors() {
        final var noProcesses = Runtime.getRuntime().availableProcessors();
        return Executors.newFixedThreadPool(noProcesses);
    }


    private Set<WorldLanguagesEntity> executeWorldLanguageEntity() {
        try {
            var set = this.implWorldLanguageEntityExecution();
            super.updateWorldLanguageCount();
            return set;
        } catch (ExecutionException | TimeoutException | InterruptedException | NullPointerException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Set<LanguageRequest> executeWorldLanguageService() {
        try {
            return this.implWorldLanguageServiceExecution();
        } catch (ExecutionException | TimeoutException | InterruptedException | NullPointerException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Set<WorldLanguagesEntity> initializeWorldLanguagesEntity() {
        return executeWorldLanguageEntity();
    }

    public Set<LanguageRequest> initializeWorldLanguageService(boolean isResultSet, WorldLanguagesEntity entity) {
        if (isResultSet)
        return executeWorldLanguageService();
        else {
            try {
                return implWorldLanguageServiceExecution(entity);
            } catch (ExecutionException | TimeoutException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Set<WorldLanguagesEntity> implWorldLanguageEntityExecution() throws ExecutionException, InterruptedException, TimeoutException {
        Future<Set<WorldLanguagesEntity>> futureCollection;
        entity = new WorldLanguagesEntity();
        futureCollection = worldLanguagesExecutors().submit(getEntity());
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();
    }

    private Set<LanguageRequest> implWorldLanguageServiceExecution() throws ExecutionException, InterruptedException, TimeoutException {
        Future<Set<LanguageRequest>> futureCollection;
        service = new WorldLanguagesService();
        futureCollection = worldLanguagesExecutors().submit(this.getService());
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();
    }

    private Set<LanguageRequest> implWorldLanguageServiceExecution(WorldLanguagesEntity entity) throws ExecutionException, InterruptedException, TimeoutException {
        this.entity = entity;
        Future<Set<LanguageRequest>> futureCollection;
        service = new WorldLanguagesService();
        futureCollection = worldLanguagesExecutors().submit(this.getService());
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();
    }


}
