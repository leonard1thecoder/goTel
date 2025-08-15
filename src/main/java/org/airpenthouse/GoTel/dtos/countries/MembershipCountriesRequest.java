package org.airpenthouse.GoTel.dtos.countries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.airpenthouse.GoTel.util.dto.binder.CountriesRequestCombiner;

@AllArgsConstructor
public class MembershipCountriesRequest implements CountriesRequestCombiner {

    @Getter
    private String countryName;
    @Getter
    private String countryRegion;
    @Getter
    private int population;
    @Getter
    private String continent;
    @Getter
    private double surfaceArea;
    @Getter
    private int indepYear;
    @Getter
    private double lifeExpectancy;
    @Getter
    private double gnp;
    @Getter
    private double oldGnp;
    @Getter
    private String localName;
    @Getter
    private String governmentForm;
    @Getter
    private String headOfState;
    @Getter
    private String capital;
    @Getter
    private String code;

}
