package org.airpenthouse.GoTel.util.mappers;

import org.airpenthouse.GoTel.dtos.languages.LanguageDto;
import org.airpenthouse.GoTel.entities.languanges.WorldLanguagesEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LanguageMapper {
    LanguageDto mapper(WorldLanguagesEntity entity);
}
