package org.airpenthouse.GoTel.util.mappers;

import org.airpenthouse.GoTel.dtos.languages.InsertWorldLanguageRequest;
import org.airpenthouse.GoTel.dtos.languages.LanguageRequest;
import org.airpenthouse.GoTel.dtos.languages.UpdateLanguageRequest;
import org.airpenthouse.GoTel.dtos.languages.UpdateLanguageStatus;
import org.airpenthouse.GoTel.entities.languanges.WorldLanguagesEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component

public interface LanguageMapper {
    LanguageRequest mapper(WorldLanguagesEntity entity);

    WorldLanguagesEntity toWorldEntity(UpdateLanguageRequest request);

    WorldLanguagesEntity toWorldEntity(UpdateLanguageStatus request);

    WorldLanguagesEntity toWorldEntity(InsertWorldLanguageRequest request);
}
