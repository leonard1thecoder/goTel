package org.airpenthouse.GoTel.util.executors;

import lombok.Getter;
import lombok.Setter;
import org.airpenthouse.GoTel.dtos.cities.CitiesRequest;
import org.airpenthouse.GoTel.entities.city.CitiesEntity;
import org.airpenthouse.GoTel.services.city.CitiesService;
import org.airpenthouse.GoTel.util.CountApiUsers;
import org.airpenthouse.GoTel.util.mappers.CitiesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.*;


public class CitiesExecutors extends CountApiUsers {
    private final ExecutorService executeCities;
    @Getter
    @Setter
    private static CitiesMapper mapper;
    @Getter
    private CitiesEntity entity;
    protected CitiesExecutors() {
        final var noProcesses = Runtime.getRuntime().availableProcessors();
        executeCities = Executors.newFixedThreadPool(noProcesses);
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
            var set = this.impCitiesEntityExecution();
            updateWorldCitiesCount();
            return set;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected Set<CitiesEntity> initializeCitiesEntity() {
        return executeCitiesEntity();
    }

    public Set<CitiesRequest> initializeCitiesService(boolean isResutlSet, CitiesEntity entity) {
        if (isResutlSet) {
            return executeCitiesService();
        } else {
            try {
                return impCitiesServiceExecution(entity);
            } catch (ExecutionException | TimeoutException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Set<CitiesEntity> impCitiesEntityExecution() throws ExecutionException, InterruptedException, TimeoutException {
        Future<Set<CitiesEntity>> futureCollection;
        futureCollection = executeCities.submit(new CitiesEntity());
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();

    }

    private Set<CitiesRequest> impCitiesServiceExecution() throws ExecutionException, InterruptedException, TimeoutException {
        Future<Set<CitiesRequest>> futureCollection;
        futureCollection = executeCities.submit(new CitiesService());
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();
    }

    private Set<CitiesRequest> impCitiesServiceExecution(CitiesEntity entity) throws ExecutionException, InterruptedException, TimeoutException {
        this.entity = entity;
        Future<Set<CitiesRequest>> futureCollection;
        futureCollection = executeCities.submit(new CitiesService());
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();
    }
}
