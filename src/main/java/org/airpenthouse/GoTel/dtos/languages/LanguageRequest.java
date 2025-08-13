package org.airpenthouse.GoTel.dtos.languages;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LanguageRequest {
    @JsonProperty("_country_name")
    private String countryName;
    @JsonProperty("_language_name")
    private String languageName;

}
