package org.airpenthouse.GoTel.dtos.languages;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateLanguageStatus {
    @JsonProperty("_language_name")
    private String languageName;
    @JsonProperty("_language_status")
    private boolean languageStatus;

}
