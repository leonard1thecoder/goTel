package org.airpenthouse.GoTel.dtos.administrators;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class AdministratorsRequest {
    @Getter
    private String administratorName, administratorSurname, administratorCellphoneNo, administratorEmailAddress;
}
