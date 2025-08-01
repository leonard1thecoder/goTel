package org.airpenthouse.GoTel.dtos.languages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Getter
public class UpdateLanguageRequest {


    private String languageName;

    private String newLanguageName;
}
