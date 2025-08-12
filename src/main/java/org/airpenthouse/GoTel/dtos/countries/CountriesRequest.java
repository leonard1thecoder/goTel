package org.airpenthouse.GoTel.dtos.countries;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CountriesRequest {
    @JsonProperty("country_name")
    private String countryName;
    @JsonProperty("_country_region")
    private String countryRegion;
}
