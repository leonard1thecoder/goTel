package org.airpenthouse.GoTel.util.mappers;


import org.airpenthouse.GoTel.dtos.administrators.AdministratorsRequest;
import org.airpenthouse.GoTel.dtos.administrators.RegisterAdministratorsRequest;
import org.airpenthouse.GoTel.dtos.administrators.UpdatePasswordRequest;
import org.airpenthouse.GoTel.dtos.administrators.UpdateTokenRequest;
import org.airpenthouse.GoTel.entities.administrator.AdministratorEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface AdministratorsMapper {
    AdministratorsRequest toDto(AdministratorEntity request);

    AdministratorEntity ToEntity(RegisterAdministratorsRequest request);

    AdministratorEntity ToEntity(UpdateTokenRequest request);

    AdministratorEntity ToEntity(UpdatePasswordRequest request);
}
