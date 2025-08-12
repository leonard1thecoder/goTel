package org.airpenthouse.GoTel.dtos.cities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateCityNameRequest {
    @JsonProperty("_city_name")
    private String cityName;
    @JsonProperty("_new_city_name")
    private String newCityName;
}
