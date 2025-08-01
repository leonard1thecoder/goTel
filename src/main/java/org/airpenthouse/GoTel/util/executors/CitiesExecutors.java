package org.airpenthouse.GoTel.util.executors;

import lombok.Getter;
import lombok.Setter;
import org.airpenthouse.GoTel.dtos.cities.CitiesRequest;
import org.airpenthouse.GoTel.entities.city.CitiesEntity;
import org.airpenthouse.GoTel.services.city.CitiesService;
import org.airpenthouse.GoTel.util.mappers.CitiesMapper;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.*;

public class CitiesExecutors {
    private static CitiesExecutors instance;
    private final ExecutorService executeCities;
    @Getter
    @Setter
    private CitiesMapper mapper;

    private CitiesExecutors() {

        final var noProcesses = Runtime.getRuntime().availableProcessors();
        executeCities = Executors.newFixedThreadPool(noProcesses);
    }

    public static CitiesExecutors getInstances() {
        if (instance == null) {
            instance = new CitiesExecutors();
        }
        return instance;
    }

    private Set<CitiesRequest> executeCitiesService() {
        try {
            return this.impCitiesServiceExecution();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Set<CitiesEntity> executeCitiesEntity() {
        try {
            return this.impCitiesEntityExecution();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Set<CitiesEntity> initializeCitiesEntity() {
        return executeCitiesEntity();
    }

    public Set<CitiesRequest> initializeCitiesService() {
        return executeCitiesService();
    }

    private Set<CitiesEntity> impCitiesEntityExecution() throws ExecutionException, InterruptedException, TimeoutException {
        Future<Set<CitiesEntity>> futureCollection;
        futureCollection = executeCities.submit(CitiesEntity.getInstance());
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();

    }

    private Set<CitiesRequest> impCitiesServiceExecution() throws ExecutionException, InterruptedException, TimeoutException {
        Future<Set<CitiesRequest>> futureCollection;
        futureCollection = executeCities.submit(CitiesService.getInstance());
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();
    }
}
