package org.airpenthouse.GoTel.dtos.administrators;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class AdministratoLoginRequest {
    @Getter
    private String administratorPassword, administratorEmailAddress;
}

