
package com.ecs.dto.exchange;

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
    "eur",
    "gbp",
    "cad",
    "jpy",
    "inr"
})
@Generated("jsonschema2pojo")
public class Usd {

    @JsonProperty("eur")
    private Double eur;
    @JsonProperty("gbp")
    private Double gbp;
    @JsonProperty("cad")
    private Double cad;
    @JsonProperty("jpy")
    private Double jpy;
    @JsonProperty("inr")
    private Double inr;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("eur")
    public Double getEur() {
        return eur;
    }

    @JsonProperty("eur")
    public void setEur(Double eur) {
        this.eur = eur;
    }

    @JsonProperty("gbp")
    public Double getGbp() {
        return gbp;
    }

    @JsonProperty("gbp")
    public void setGbp(Double gbp) {
        this.gbp = gbp;
    }

    @JsonProperty("cad")
    public Double getCad() {
        return cad;
    }

    @JsonProperty("cad")
    public void setCad(Double cad) {
        this.cad = cad;
    }

    @JsonProperty("jpy")
    public Double getJpy() {
        return jpy;
    }

    @JsonProperty("jpy")
    public void setJpy(Double jpy) {
        this.jpy = jpy;
    }

    @JsonProperty("inr")
    public Double getInr() {
        return inr;
    }

    @JsonProperty("inr")
    public void setInr(Double inr) {
        this.inr = inr;
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
