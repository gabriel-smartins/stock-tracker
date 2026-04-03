package com.gmartins.stocktracker.service;

import com.gmartins.stocktracker.client.response.BrapiStockDataResponse;
import com.gmartins.stocktracker.entity.Stock;
import com.gmartins.stocktracker.entity.StockPurchase;
import com.gmartins.stocktracker.repository.StockPurchaseRepository;
import com.gmartins.stocktracker.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final StockPurchaseRepository stockPurchaseRepository;
    private final FindStockDetailService findStockDetailService;

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

    public List<Stock> findAll() {
        List<Stock> stocks = stockRepository.findAll();

        stocks.forEach(stock -> {
            Optional<BrapiStockDataResponse> brapiStockDetail = findStockDetailService
                    .findBrapiStockDetail(stock.getStock());
            stock.setPrice(BigDecimal
                    .valueOf(brapiStockDetail
                            .map(BrapiStockDataResponse::getRegularMarketPrice).orElse(0.0)));
        });

        return stocks;
    }
}
