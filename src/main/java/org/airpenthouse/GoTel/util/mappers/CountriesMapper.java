package org.airpenthouse.GoTel.util.mappers;

import org.airpenthouse.GoTel.dtos.countries.CountriesRequest;
import org.airpenthouse.GoTel.entities.country.CountriesEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface CountriesMapper {
    CountriesRequest mapper(CountriesEntity entity);
}
