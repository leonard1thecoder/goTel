package org.airpenthouse.GoTel.dtos.languages;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InsertWorldLanguageRequest {
    @JsonProperty("_country_name")
    private String countryName;
    @JsonProperty("_language_name")
    private String languageName;
}
