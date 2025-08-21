package org.airpenthouse.GoTel.dtos.administrators;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
public class UpdatePasswordRequest {
    @Getter
    private LocalDateTime registrationDate;
    @Getter
    private String administratorEmailAddress, administratorPassword;
}
