package org.airpenthouse.GoTel.util;


import org.airpenthouse.GoTel.entities.city.CitiesEntity;
import org.airpenthouse.GoTel.entities.country.CountriesEntity;
import org.airpenthouse.GoTel.entities.languanges.WorldLanguagesEntity;
import org.airpenthouse.GoTel.services.city.CitiesService;
import org.airpenthouse.GoTel.services.country.CountriesService;
import org.airpenthouse.GoTel.services.language.WorldLanguagesService;
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


    protected Set<CountriesEntity> executeCountriesEntity() {
        Future<Set<CountriesEntity>> futureCollection;

        CountriesEntity countryEntity = new CountriesEntity();
        ExecutorService service = Executors.newCachedThreadPool();
        try {
            futureCollection = service.submit(countryEntity);
            countriesCollection = futureCollection.get(15, TimeUnit.SECONDS);
            LOG.info("initializing the countries entity entity was successful " + countriesCollection);

        } catch (ExecutionException | TimeoutException | InterruptedException | NullPointerException e) {
            throw new RuntimeException("Error occurred :" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityService.shutdown();
            return Optional.of(countriesCollection).get();

        }
    }


    protected Set<WorldLanguagesEntity> executeWorldLanguagesEntity() {
        Future<Set<WorldLanguagesEntity>> futureCollection;
        ExecutorService service = Executors.newCachedThreadPool();
        WorldLanguagesEntity worldLanguages = new WorldLanguagesEntity();

        try {
            futureCollection = service.submit(worldLanguages);
            worldLanguagesCollection = futureCollection.get(15, TimeUnit.SECONDS);

        } catch (ExecutionException | TimeoutException | InterruptedException e) {
            throw new RuntimeException("Error occurred :" + e.getMessage());
        } finally {
            service.shutdown();

            return Optional.of(worldLanguagesCollection).get();
        }
    }

    protected Set<WorldLanguagesEntity> executeWorldLanguagesService() {
        Future<Set<WorldLanguagesEntity>> futureCollection;
        ExecutorService service = Executors.newCachedThreadPool();
        WorldLanguagesService worldLanguages = new WorldLanguagesService();

        try {
            futureCollection = service.submit(worldLanguages);
            worldLanguagesCollection = futureCollection.get(15, TimeUnit.SECONDS);

        } catch (ExecutionException | TimeoutException | InterruptedException e) {
            throw new RuntimeException("Error occurred :" + e.getMessage());
        } finally {
            service.shutdown();

            return Optional.of(worldLanguagesCollection).get();
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

    protected Set<CountriesEntity> executeCountriesService() {
        Future<Set<CountriesEntity>> futureCollection;
        ExecutorService service = Executors.newCachedThreadPool();
        CountriesService countryService = new CountriesService();
        try {
            futureCollection = service.submit(countryService);
            this.countriesServiceCollection = futureCollection.get(15, TimeUnit.SECONDS);
            LOG.info("initializing the countries service was successful" + this.countriesServiceCollection);
        } catch (ExecutionException | TimeoutException | InterruptedException | NullPointerException e) {
            throw new RuntimeException("Error occurred :" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            service.shutdown();

            return Optional.of(countriesServiceCollection).get();
        }
    }
}