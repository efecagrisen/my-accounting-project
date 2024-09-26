package com.ecs.client;

import com.ecs.dto.exchange.ExchangeRates;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/usd.json", name = "exchange-client")
public interface ExchangeClient {

    @GetMapping
    ExchangeRates getExchangeRates();


}
