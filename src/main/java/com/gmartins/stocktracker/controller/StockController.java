package com.gmartins.stocktracker.controller;

import com.gmartins.stocktracker.controller.request.StockAddPurchaseRequest;
import com.gmartins.stocktracker.controller.request.StockRequest;
import com.gmartins.stocktracker.entity.Stock;
import com.gmartins.stocktracker.entity.StockPurchase;
import com.gmartins.stocktracker.mapper.StockMapper;
import com.gmartins.stocktracker.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.gmartins.stocktracker.mapper.StockMapper.toStockPurchase;

@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping
    public ResponseEntity<Stock> savePurchase(@RequestBody StockRequest request) {
        Pair<Stock, StockPurchase> stock = StockMapper.toStock(request);
        Stock savedStock = stockService.savePurchase(stock.getFirst(), stock.getSecond());

        return ResponseEntity.status(HttpStatus.CREATED).body(savedStock);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addPurchase(@RequestBody StockAddPurchaseRequest request) {
        try {
            stockService.addPurchase(request.getStockId(), toStockPurchase(request));

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.unprocessableEntity().build();
        }

    }
}