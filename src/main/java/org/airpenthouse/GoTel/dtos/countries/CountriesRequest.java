package org.airpenthouse.GoTel.dtos.countries;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.airpenthouse.GoTel.util.dto.binder.CountriesRequestCombiner;

@AllArgsConstructor
@Getter
public class CountriesRequest implements CountriesRequestCombiner {
    @JsonProperty("country_name")
    private String countryName;
    @JsonProperty("_country_region")
    private String countryRegion;
}
