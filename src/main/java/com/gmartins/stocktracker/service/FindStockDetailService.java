package com.gmartins.stocktracker.service;

import com.gmartins.stocktracker.client.BrapiClient;
import com.gmartins.stocktracker.client.response.BrapiStockDataResponse;
import com.gmartins.stocktracker.client.response.BrapiStockResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindStockDetailService {

    @Value("${stock.client.brapi.token}")
    private String token;

    private final BrapiClient brapiClient;

    @Cacheable(value = "stock", key = "#stock")
    public Optional<BrapiStockDataResponse> findBrapiStockDetail(String stock) {
        log.info("Consulting stock information: {} on Brapi", stock);
        BrapiStockResponse brapiStockResponse = brapiClient.getStock(stock, token);
        log.info("Brapi return of stock {}: {}", stock, brapiStockResponse.getResults().get(0));

        return Optional.of(brapiStockResponse.getResults().get(0));
    }
}
