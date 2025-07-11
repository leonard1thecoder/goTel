package org.airpenthouse.GoTel.util.mappers;

import org.airpenthouse.GoTel.dtos.cities.CitiesDto;
import org.airpenthouse.GoTel.dtos.cities.CreateNewCityDto;
import org.airpenthouse.GoTel.entities.city.CitiesEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CitiesMapper {
    CitiesDto toCitiesDto(CitiesEntity entity);

    CitiesEntity toCitiesEntity(CreateNewCityDto dto);
}
