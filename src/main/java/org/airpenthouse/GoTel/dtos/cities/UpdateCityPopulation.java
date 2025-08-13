package org.airpenthouse.GoTel.dtos.cities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateCityPopulation {
    @JsonProperty("_city_name")
    private String cityName;
    @JsonProperty("_population_no")
    private int population;
}
