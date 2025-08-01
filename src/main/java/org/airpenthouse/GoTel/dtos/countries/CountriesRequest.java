package org.airpenthouse.GoTel.dtos.countries;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CountriesRequest {
    private String countryName;
    private String countryRegion;
}
