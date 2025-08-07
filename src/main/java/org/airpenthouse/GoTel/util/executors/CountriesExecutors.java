package org.airpenthouse.GoTel.util.executors;

import lombok.Getter;
import org.airpenthouse.GoTel.dtos.countries.CountriesRequest;
import org.airpenthouse.GoTel.entities.country.CountriesEntity;
import org.airpenthouse.GoTel.services.country.CountriesService;
import org.airpenthouse.GoTel.util.CountApiUsers;
import org.airpenthouse.GoTel.util.mappers.CountriesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.*;

@Component
public class CountriesExecutors extends CountApiUsers {
    private final ExecutorService executeCities;
    @Getter
    @Autowired
    private CountriesMapper mapper;

    protected CountriesExecutors() {
        final var noProcesses = Runtime.getRuntime().availableProcessors();
        executeCities = Executors.newFixedThreadPool(noProcesses);
    }


    private Set<CountriesEntity> executeCountriesEntity() {
        try {
            var set = this.implCountriesEntityExecution();
            super.updateWorldCountriesCount();
            return set;
        } catch (ExecutionException | TimeoutException | InterruptedException | NullPointerException e) {
            throw new RuntimeException("Error occurred :" + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Set<CountriesRequest> executeCountriesServices() {
        try {
            return this.implCountriesServiceExecution();
        } catch (ExecutionException | TimeoutException | InterruptedException | NullPointerException e) {
            throw new RuntimeException("Error occurred :" + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Set<CountriesEntity> initializeCountriesEntity() {
        return executeCountriesEntity();
    }

    public Set<CountriesRequest> initializeCountriesService() {
        return executeCountriesServices();
    }

    private Set<CountriesEntity> implCountriesEntityExecution() throws ExecutionException, InterruptedException, TimeoutException {
        Future<Set<CountriesEntity>> futureCollection;
        futureCollection = executeCities.submit(CountriesEntity.getInstance());
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();
    }

    private Set<CountriesRequest> implCountriesServiceExecution() throws ExecutionException, InterruptedException, TimeoutException {
        Future<Set<CountriesRequest>> futureCollection;
        futureCollection = executeCities.submit(CountriesService.getInstance());
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();
    }


}
