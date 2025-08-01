package org.airpenthouse.GoTel.util.executors;

import lombok.Getter;
import org.airpenthouse.GoTel.dtos.languages.LanguageRequest;
import org.airpenthouse.GoTel.entities.languanges.WorldLanguagesEntity;
import org.airpenthouse.GoTel.services.language.WorldLanguagesService;
import org.airpenthouse.GoTel.util.mappers.LanguageMapper;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.*;

import static org.airpenthouse.GoTel.entities.languanges.WorldLanguagesEntity.getInstance;

public class WorldLanguagesExecutors {

    private static final WorldLanguagesExecutors instance = new WorldLanguagesExecutors();
    private final ExecutorService executeCities;
    @Getter
    private LanguageMapper languageMapper;

    private WorldLanguagesExecutors() {
        final var noProcesses = Runtime.getRuntime().availableProcessors();
        executeCities = Executors.newFixedThreadPool(noProcesses);
    }

    public static WorldLanguagesExecutors getInstances() {
        return instance;
    }

    private Set<WorldLanguagesEntity> executeWorldLanguageEntity() {
        try {
            return this.implWorldLanguageEntityExecution();
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

    public Set<LanguageRequest> initializeWorldLanguageService() {
        return executeWorldLanguageService();
    }

    private Set<WorldLanguagesEntity> implWorldLanguageEntityExecution() throws ExecutionException, InterruptedException, TimeoutException {
        Future<Set<WorldLanguagesEntity>> futureCollection;

        futureCollection = executeCities.submit(getInstance());
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();

    }

    private Set<LanguageRequest> implWorldLanguageServiceExecution() throws ExecutionException, InterruptedException, TimeoutException {
        Future<Set<LanguageRequest>> futureCollection;
        futureCollection = executeCities.submit(WorldLanguagesService.getInstance());
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();
    }

    public void build(LanguageMapper mapper) {
        this.languageMapper = mapper;
    }
}
