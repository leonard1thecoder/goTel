package org.airpenthouse.GoTel.services.country;

import org.airpenthouse.GoTel.util.ExecutionHandler;
import org.airpenthouse.GoTel.entities.country.CountriesEntity;
import org.airpenthouse.GoTel.util.LOG;
import org.airpenthouse.GoTel.util.mappers.CountriesMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Callable;

import static org.airpenthouse.GoTel.entities.country.CountriesEntity.ENTITY_TRIGGER;

@Service
public final class CountriesService extends ExecutionHandler implements Callable<Set<CountriesEntity>> {
    private CountriesMapper mapper;
    public static String SERVICE_HANDLER;


    private Set<CountriesEntity> getAllCountries() {
        ENTITY_TRIGGER = "FIND_ALL_COUNTRIES";
        LOG.info("EXECURINg EnTITY" + super.executeCountriesEntity());
        return Collections.unmodifiableSet(super.executeCountriesEntity());
    }

    @Override
    public Set<CountriesEntity> call() throws Exception {

        switch (SERVICE_HANDLER) {
            case "FIND_ALL_COUNTRIES" -> {
                return getAllCountries();
            }

        }
        return null;
    }
}

