package org.airpenthouse.GoTel.util.mappers;

import org.airpenthouse.GoTel.dtos.countApiUsers.CountApiUsersRequest;
import org.airpenthouse.GoTel.util.CountApiUsers;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CountApiUsersMapper {
    CountApiUsersRequest toDto(CountApiUsers request);
}
