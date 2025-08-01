package org.airpenthouse.GoTel.dtos.cities;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateCityPopulation {

    private String cityName;
    private int population;
}
