package org.airpenthouse.GoTel.dtos.cities;

import lombok.Data;

@Data
public class CreateNewCityDto {
    private String cityName, district, countryCode;
    private long population;


}
