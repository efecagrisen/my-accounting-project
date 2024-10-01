package com.ecs.client;

import com.ecs.dto.CountriesDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

//https://www.universal-tutorial.com/rest-apis/free-rest-api-for-country-state-city

@FeignClient(url = "https://www.universal-tutorial.com/api", name = "COUNTRY-CLIENT")
public interface CountryClient {

    @GetMapping("/countries")
    List<CountriesDto> getAllCountries(@RequestHeader ("Authorization") String bearerToken);

}
