package org.airpenthouse.GoTel.util.mappers;

import org.airpenthouse.GoTel.dtos.countries.CountriesDto;
import org.airpenthouse.GoTel.entities.country.CountriesEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface CountriesMapper {
    CountriesDto mapper(CountriesEntity entity);

}
