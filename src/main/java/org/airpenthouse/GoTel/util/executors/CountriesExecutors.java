package org.airpenthouse.GoTel.util.executors;

import lombok.Getter;
import lombok.Setter;
import org.airpenthouse.GoTel.dtos.countries.CountriesRequest;
import org.airpenthouse.GoTel.entities.country.CountriesEntity;
import org.airpenthouse.GoTel.services.country.CountriesService;
import org.airpenthouse.GoTel.util.mappers.CountriesMapper;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.*;

import static org.airpenthouse.GoTel.entities.country.CountriesEntity.getInstance;

public class CountriesExecutors {

    private static final CountriesExecutors instance = new CountriesExecutors();
    private final ExecutorService executeCities;
    @Getter
    private CountriesMapper mapper;

    private CountriesExecutors() {
        final var noProcesses = Runtime.getRuntime().availableProcessors();
        executeCities = Executors.newFixedThreadPool(noProcesses);
    }

    public static CountriesExecutors getInstances() {
        return instance;
    }

    private Set<CountriesEntity> executeCountriesEntity() {
        try {
            return this.implCountriesEntityExecution();
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
        futureCollection = executeCities.submit(getInstance());
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();
    }

    private Set<CountriesRequest> implCountriesServiceExecution() throws ExecutionException, InterruptedException, TimeoutException {
        Future<Set<CountriesRequest>> futureCollection;
        futureCollection = executeCities.submit(CountriesService.getInstance());
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();
    }

    public void build(CountriesMapper mapper) {
        this.mapper = mapper;
    }
}
