package org.airpenthouse.GoTel.dtos.cities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Data
public class CreateNewCityRequest {
    @JsonProperty("_city_name")
    private String cityName;
    @JsonProperty("_district")
    private String district;
    @JsonProperty("_country_name")
    private String countryName;
}
