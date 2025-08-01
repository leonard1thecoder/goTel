package org.airpenthouse.GoTel.dtos.cities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CitiesRequest {
    private String cityName, district, countryName;

}
