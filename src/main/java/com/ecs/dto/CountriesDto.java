
package com.ecs.dto;

import java.util.LinkedHashMap;
import java.util.Map;
import jakarta.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "country_name",
    "country_short_name",
    "country_phone_code"
})
@Generated("jsonschema2pojo")
public class CountriesDto {

    @JsonProperty("country_name")
    private String countryName;
    @JsonProperty("country_short_name")
    private String countryShortName;
    @JsonProperty("country_phone_code")
    private Integer countryPhoneCode;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("country_name")
    public String getCountryName() {
        return countryName;
    }

    @JsonProperty("country_name")
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @JsonProperty("country_short_name")
    public String getCountryShortName() {
        return countryShortName;
    }

    @JsonProperty("country_short_name")
    public void setCountryShortName(String countryShortName) {
        this.countryShortName = countryShortName;
    }

    @JsonProperty("country_phone_code")
    public Integer getCountryPhoneCode() {
        return countryPhoneCode;
    }

    @JsonProperty("country_phone_code")
    public void setCountryPhoneCode(Integer countryPhoneCode) {
        this.countryPhoneCode = countryPhoneCode;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
