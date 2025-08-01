package org.airpenthouse.GoTel.dtos.languages;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateLanguageStatus {

    private String languageName;
    private boolean languageStatus;

}
