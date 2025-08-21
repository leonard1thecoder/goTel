package org.airpenthouse.GoTel.util.executors;

import lombok.Getter;
import org.airpenthouse.GoTel.entities.administrator.AdministratorEntity;
import org.airpenthouse.GoTel.entities.city.CitiesEntity;
import org.airpenthouse.GoTel.util.CommonEntityMethod;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.*;

public class AdministratorsExecutors extends CommonEntityMethod {

    private final ExecutorService executeAministrators;

    @Getter
    private CitiesEntity entity;

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


}
