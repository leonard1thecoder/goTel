package org.airpenthouse.GoTel.dtos.cities;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateCityNameRequest {

    private String cityName;
    private String newCityName;
}
