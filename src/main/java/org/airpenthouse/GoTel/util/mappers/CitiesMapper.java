package org.airpenthouse.GoTel.util.mappers;

import org.airpenthouse.GoTel.dtos.cities.CitiesRequest;
import org.airpenthouse.GoTel.dtos.cities.CreateNewCityRequest;
import org.airpenthouse.GoTel.dtos.cities.UpdateCityNameRequest;
import org.airpenthouse.GoTel.dtos.cities.UpdateCityPopulation;
import org.airpenthouse.GoTel.entities.city.CitiesEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CitiesMapper {
    CitiesRequest toCitiesDto(CitiesEntity entity);

    CitiesEntity toCitiesEntity(CreateNewCityRequest request);

    CitiesEntity toCitiesEntity(UpdateCityNameRequest request);

    CitiesEntity toCitiesEntity(UpdateCityPopulation request);
}
