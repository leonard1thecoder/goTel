package org.airpenthouse.GoTel.util.executors;

import lombok.Getter;
import lombok.Setter;
import org.airpenthouse.GoTel.dtos.administrators.AdministratorsRequest;
import org.airpenthouse.GoTel.entities.administrator.AdministratorEntity;
import org.airpenthouse.GoTel.services.administrator.AdministratorsService;
import org.airpenthouse.GoTel.util.CommonEntityMethod;
import org.airpenthouse.GoTel.util.mappers.AdministratorsMapper;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.*;

public class AdministratorsExecutors extends CommonEntityMethod {

    private final ExecutorService executeAministrators;

    @Getter
    private AdministratorEntity entity;
    @Getter
    @Setter
    private static AdministratorsMapper mapper;
    protected AdministratorsExecutors() {
        final var noProcesses = Runtime.getRuntime().availableProcessors();
        executeAministrators = Executors.newFixedThreadPool(noProcesses);
    }


    private Set<AdministratorEntity> executeAministratorsEntity() {
        try {
            return this.impAdministratorEntityExecution();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected Set<AdministratorEntity> initializeAdministrAtOrEntity() {
        return executeAministratorsEntity();
    }


    private Set<AdministratorEntity> impAdministratorEntityExecution() throws ExecutionException, InterruptedException, TimeoutException {
        Future<Set<AdministratorEntity>> futureCollection;
        futureCollection = executeAministrators.submit(new AdministratorEntity());
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();

    }

    public Set<AdministratorsRequest> initializeCountriesService(boolean isResultSet, AdministratorEntity entity) {
        if (isResultSet)
        return executeCountriesService();
        else {
            try {
                return implAdministratorsServviceExecution(entity);
            } catch (ExecutionException | InterruptedException | TimeoutException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Set<AdministratorsRequest> executeCountriesService() {
        try {
            return this.implAdministratorsServviceExecution();
        } catch (ExecutionException | TimeoutException | InterruptedException | NullPointerException e) {
            throw new RuntimeException("Error occurred :" + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Set<AdministratorsRequest> implAdministratorsServviceExecution() throws ExecutionException, InterruptedException, TimeoutException {
        Future<Set<AdministratorsRequest>> futureCollection;
        futureCollection = executeAministrators.submit(new AdministratorsService());
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();
    }

    private Set<AdministratorsRequest> implAdministratorsServviceExecution(AdministratorEntity entity) throws ExecutionException, InterruptedException, TimeoutException {
        Future<Set<AdministratorsRequest>> futureCollection;
        this.entity = entity;
        futureCollection = executeAministrators.submit(new AdministratorsService(
        ));
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();
    }


}
