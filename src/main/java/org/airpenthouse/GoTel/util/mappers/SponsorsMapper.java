package org.airpenthouse.GoTel.util.mappers;

import org.airpenthouse.GoTel.dtos.sponsors.SponsorsDonateRequest;
import org.airpenthouse.GoTel.dtos.sponsors.SponsorsRequest;
import org.airpenthouse.GoTel.entities.sponsors.SponsorEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface SponsorsMapper {
    SponsorsRequest toDto(SponsorEntity request);

    SponsorEntity toEntity(SponsorsDonateRequest request);
}
