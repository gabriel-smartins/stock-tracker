package com.gmartins.stocktracker.controller;

import com.gmartins.stocktracker.controller.request.StockRequest;
import com.gmartins.stocktracker.entity.Stock;
import com.gmartins.stocktracker.entity.StockPurchase;
import com.gmartins.stocktracker.repository.StockPurchaseRepository;
import com.gmartins.stocktracker.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockRepository stockRepository;
    private final StockPurchaseRepository stockPurchaseRepository;

    @PostMapping
    public void savePurchase(@RequestBody StockRequest request) {

        Stock stock = new Stock();
        stock.setStock(request.getStock());

        StockPurchase stockPurchase = new StockPurchase();
        stockPurchase.setPrice(request.getPrice());
        stockPurchase.setQuantity(request.getQuantity());
        stockPurchase.setDate(request.getDate());

        StockPurchase saved = stockPurchaseRepository.save(stockPurchase);

        stock.setPurchases(List.of(saved));

        stockRepository.save(stock);
    }


}