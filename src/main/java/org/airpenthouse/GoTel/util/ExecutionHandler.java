package org.airpenthouse.GoTel.util;


import org.airpenthouse.GoTel.entities.city.CitiesEntity;
import org.airpenthouse.GoTel.entities.country.CountriesEntity;
import org.airpenthouse.GoTel.entities.languanges.WorldLanguagesEntity;
import org.airpenthouse.GoTel.services.city.CitiesService;
import org.airpenthouse.GoTel.services.country.Country;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;
import java.util.Optional;
import java.util.Set;

/*
 * The application is a multithreaded application
 * To avoid having multiple imports of the concurrent framework
 * Ths framework handle the threads
 * */
@Component
public abstract class ExecutionHandler {
    private Set<CitiesEntity> citiesEntityCollection = new CopyOnWriteArraySet<>();
    private Set<CountriesEntity> countriesCollection = new ConcurrentSkipListSet<>();
    private Set<WorldLanguagesEntity> worldLanguagesCollection = new CopyOnWriteArraySet<>();
    private Set<CitiesEntity> citiesServiceCollection = new CopyOnWriteArraySet<>();
    private Set<CountriesEntity> countriesServiceCollection = new CopyOnWriteArraySet<>();

    private CountriesEntity countriesEntity;
    private WorldLanguagesEntity worldLanguagesEntity;
    private final ExecutorService entityService = Executors.newCachedThreadPool();


    protected Set<CitiesEntity> executeCitiesEntity() {
        Future<Set<CitiesEntity>> futureCollection;
        CitiesEntity citiesEntity = new CitiesEntity();
        try {
            futureCollection = entityService.submit(citiesEntity);
            citiesEntityCollection = futureCollection.get(15, TimeUnit.SECONDS);
            LOG.info("initializing the cities entity was successful");
        } catch (ExecutionException | TimeoutException | InterruptedException | NullPointerException e) {
            throw new RuntimeException("Error occurred :" + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            entityService.shutdown();
            return Optional.of(citiesEntityCollection).get();
        }
    }


    protected Set<CountriesEntity> executeCountriesEntity(CountriesEntity call) {
        Future<Set<CountriesEntity>> futureCollection;

        try {
            futureCollection = entityService.submit(call);
            countriesCollection = futureCollection.get(15, TimeUnit.SECONDS);

        } catch (ExecutionException | TimeoutException | InterruptedException | NullPointerException e) {
            throw new RuntimeException("Error occurred :" + e.getMessage());
        } finally {
            entityService.shutdown();

            return Optional.of(countriesCollection).get();

        }
    }


    protected Optional<Set<WorldLanguagesEntity>> executeWorldLanguagesEntity(WorldLanguagesEntity call) {
        Future<Set<WorldLanguagesEntity>> futureCollection;
        ExecutorService service = Executors.newCachedThreadPool();
        try {
            futureCollection = service.submit(call);
            worldLanguagesCollection = futureCollection.get(15, TimeUnit.SECONDS);

        } catch (ExecutionException | TimeoutException | InterruptedException e) {
            throw new RuntimeException("Error occurred :" + e.getMessage());
        } finally {
            service.shutdown();

            return Optional.of(worldLanguagesCollection);
        }
    }

    protected Set<CitiesEntity> initializeCitiesEntity() {
        LOG.info("initializing the Cities entity");
        return executeCitiesEntity();
    }


    protected Set<CitiesEntity> initializeCitiesService() {
        LOG.info("initializing the Cities service");
        return executeCitiesService();
    }

    protected Set<CitiesEntity> executeCitiesService() {
        Future<Set<CitiesEntity>> futureCollection;
        CitiesService citiesService = new CitiesService();
        ExecutorService service = Executors.newCachedThreadPool();
        try {
            futureCollection = service.submit(citiesService);
            this.citiesServiceCollection = futureCollection.get(15, TimeUnit.SECONDS);

        } catch (ExecutionException | TimeoutException | InterruptedException e) {
            throw new RuntimeException("Error occurred :" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            service.shutdown();

            return Optional.of(citiesServiceCollection).get();
        }
    }

    protected Set<CountriesEntity> executeCountriesService(Country call) {
        Future<Set<CountriesEntity>> futureCollection;
        ExecutorService service = Executors.newCachedThreadPool();
        try {
            futureCollection = service.submit(call);
            this.countriesServiceCollection = futureCollection.get(15, TimeUnit.SECONDS);
            LOG.info("initializing the cities service was successful");
        } catch (ExecutionException | TimeoutException | InterruptedException | NullPointerException e) {
            throw new RuntimeException("Error occurred :" + e.getMessage());
        } finally {
            service.shutdown();

            return Optional.of(countriesServiceCollection).get();
        }
    }
}