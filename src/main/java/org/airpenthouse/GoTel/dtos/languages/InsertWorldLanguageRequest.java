package org.airpenthouse.GoTel.dtos.languages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InsertWorldLanguageRequest {
    private String countryName;
    private String languageName;
}
