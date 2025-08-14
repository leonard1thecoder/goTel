package org.airpenthouse.GoTel.util.mappers;

import org.airpenthouse.GoTel.dtos.membership.MembershipRequest;
import org.airpenthouse.GoTel.dtos.membership.RegisterMemberRequest;
import org.airpenthouse.GoTel.dtos.membership.UpdateTokenRequest;
import org.airpenthouse.GoTel.entities.membership.MembershipEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
public interface MembershipMapper {
    MembershipRequest toDto(MembershipEntity request);

    @Mapping(target = "memberToken", expression = "java(org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(7432))")
    @Mapping(target = "registeredDate", expression = "java(java.time.LocalDateTime.now())")
    MembershipEntity toEntity(RegisterMemberRequest request);

    @Mapping(target = "memberToken", expression = "java(org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(7432))")
    @Mapping(target = "registeredDate", expression = "java(java.time.LocalDateTime.now())")
    MembershipEntity toEntity(UpdateTokenRequest request);

}
