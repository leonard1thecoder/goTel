package org.airpenthouse.GoTel.util.executors;

import lombok.Getter;
import org.airpenthouse.GoTel.dtos.languages.LanguageRequest;
import org.airpenthouse.GoTel.entities.languanges.WorldLanguagesEntity;
import org.airpenthouse.GoTel.services.language.WorldLanguagesService;
import org.airpenthouse.GoTel.util.CountApiUsers;
import org.airpenthouse.GoTel.util.mappers.LanguageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.*;

@Component
public class WorldLanguagesExecutors extends CountApiUsers {
    private final ExecutorService executeLanguages;
    @Autowired
    @Getter
    private LanguageMapper languageMapper;

    protected WorldLanguagesExecutors() {
        final var noProcesses = Runtime.getRuntime().availableProcessors();
        executeLanguages = Executors.newFixedThreadPool(noProcesses);
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

    protected Set<WorldLanguagesEntity> initializeWorldLanguagesEntity() {
        return executeWorldLanguageEntity();
    }

    protected Set<LanguageRequest> initializeWorldLanguageService() {
        return executeWorldLanguageService();
    }

    private Set<WorldLanguagesEntity> implWorldLanguageEntityExecution() throws ExecutionException, InterruptedException, TimeoutException {
        Future<Set<WorldLanguagesEntity>> futureCollection;
        futureCollection = executeLanguages.submit(WorldLanguagesEntity.getInstance());
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();
    }

    private Set<LanguageRequest> implWorldLanguageServiceExecution() throws ExecutionException, InterruptedException, TimeoutException {
        Future<Set<LanguageRequest>> futureCollection;
        futureCollection = executeLanguages.submit(WorldLanguagesService.getInstance());
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();
    }


}
