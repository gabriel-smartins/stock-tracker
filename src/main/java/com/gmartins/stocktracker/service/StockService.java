package com.gmartins.stocktracker.service;

import com.gmartins.stocktracker.entity.Stock;
import com.gmartins.stocktracker.entity.StockPurchase;
import com.gmartins.stocktracker.repository.StockPurchaseRepository;
import com.gmartins.stocktracker.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final StockPurchaseRepository stockPurchaseRepository;

    public Stock savePurchase(Stock stock, StockPurchase stockPurchase) {
        StockPurchase savedStockPurchase = stockPurchaseRepository.save(stockPurchase);
        stock.setPurchases(List.of(savedStockPurchase));
        return stockRepository.save(stock);
    }

    public void addPurchase(String stockId, StockPurchase stockPurchase) {
        Optional<Stock> optStock = stockRepository.findById(stockId);

        optStock.map(stock -> {
            StockPurchase savedStockPurchase = stockPurchaseRepository.save(stockPurchase);
            stock.getPurchases().add(savedStockPurchase);

            return stockRepository.save(stock);
        }).orElseThrow(() -> new IllegalArgumentException("Cannot find stock with id: " + stockId));
    }

}
