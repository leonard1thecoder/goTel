package org.airpenthouse.GoTel.dtos.cities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Data
public class CreateNewCityRequest {
    private String cityName, district, countryName;
}
