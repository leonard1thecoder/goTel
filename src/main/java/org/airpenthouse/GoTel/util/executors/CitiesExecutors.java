package org.airpenthouse.GoTel.util.executors;

import lombok.Getter;
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

@Component
public class CitiesExecutors extends CountApiUsers {
    private final ExecutorService executeCities;
    @Autowired
    @Getter
    private CitiesMapper mapper;

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

    protected Set<CitiesRequest> initializeCitiesService() {
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
