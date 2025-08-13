package org.airpenthouse.GoTel.dtos.countApiUsers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class CountApiUsersRequest {
    @JsonProperty("_world_language_api_users_count")
    private Integer countWorldLanguagesUsers;
    @JsonProperty("_world_countries_api_users_count")
    private Integer countWorldCountriesUsers;
    @JsonProperty("_world_cities_api_users_count")
    private Integer countWorldCitiesUsersUsers;
    @JsonIgnore
    @JsonProperty("_last_modified_date")
    private LocalDateTime modifiedDate;
}
