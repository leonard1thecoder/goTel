package org.airpenthouse.GoTel.dtos.administrators;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
public class RegisterAdministratorsRequest {
    @Getter
    private LocalDateTime registrationDate;
    @Getter
    private String administratorName, administratorSurname, administratorCellphoneNo, administratorEmailAddress, administratorToken, administratorPassword;
}
