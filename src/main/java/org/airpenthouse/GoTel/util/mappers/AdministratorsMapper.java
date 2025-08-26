package org.airpenthouse.GoTel.util.mappers;


import org.airpenthouse.GoTel.dtos.administrators.*;
import org.airpenthouse.GoTel.entities.administrator.AdministratorEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface AdministratorsMapper {
    AdministratorsRequest toDto(AdministratorEntity request);

    AdministratorEntity ToEntity(RegisterAdministratorsRequest request);

    AdministratorEntity ToEntity(AdministratoLoginRequest request);

    AdministratorEntity ToEntity(UpdateTokenRequest request);

    AdministratorEntity ToEntity(UpdatePasswordRequest request);
}
