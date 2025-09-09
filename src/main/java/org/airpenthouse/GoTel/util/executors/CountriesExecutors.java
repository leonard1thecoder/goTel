package org.airpenthouse.GoTel.util.executors;

import lombok.Getter;
import lombok.Setter;
import org.airpenthouse.GoTel.dtos.countries.CountriesRequest;
import org.airpenthouse.GoTel.entities.country.CountriesEntity;
import org.airpenthouse.GoTel.services.country.CountriesService;
import org.airpenthouse.GoTel.util.CountApiUsers;
import org.airpenthouse.GoTel.util.dto.binder.CountriesRequestCombiner;
import org.airpenthouse.GoTel.util.mappers.CountriesMapper;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.*;


public class CountriesExecutors extends CountApiUsers {
    private ExecutorService executeCountries;
    @Getter
    @Setter
    private static CountriesMapper mapper;

    protected CountriesExecutors() {
        super();
    }


    private ExecutorService determineNoThreadToUse() {
        /*
            Members with registered successful uses more threads than non membership
         */
        if (checkMemberShipStatusAndTokenMatch()) {
            final var noProcesses = Runtime.getRuntime().availableProcessors() * 2;
            executeCountries = Executors.newFixedThreadPool(noProcesses);
        } else {
            final var noProcesses = Runtime.getRuntime().availableProcessors() ;
            executeCountries = Executors.newFixedThreadPool(noProcesses);
        }
        return executeCountries;
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

    private Set<? extends CountriesRequestCombiner> executeCountriesServices() {
        try {
            return this.implCountriesServiceExecution();
        } catch (ExecutionException | TimeoutException | InterruptedException | NullPointerException e) {
            throw new RuntimeException("Error occurred :" + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Set<CountriesEntity> initializeCountriesEntity() {
        return executeCountriesEntity();
    }

    public Set<? extends CountriesRequestCombiner> initializeCountriesService() {
        return executeCountriesServices();
    }

    private Set<CountriesEntity> implCountriesEntityExecution() throws ExecutionException, InterruptedException, TimeoutException {
        Future<Set<CountriesEntity>> futureCollection;
        futureCollection = determineNoThreadToUse().submit(new CountriesEntity());
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();
    }

    private Set<? extends CountriesRequestCombiner> implCountriesServiceExecution() throws ExecutionException, InterruptedException, TimeoutException {
        Future<Set<? extends CountriesRequestCombiner>> futureCollection;
        futureCollection = determineNoThreadToUse().submit(new CountriesService());
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();
    }


}
