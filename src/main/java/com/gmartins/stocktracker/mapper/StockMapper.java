package com.gmartins.stocktracker.mapper;

import com.gmartins.stocktracker.controller.request.StockRequest;
import com.gmartins.stocktracker.entity.Stock;
import com.gmartins.stocktracker.entity.StockPurchase;
import lombok.experimental.UtilityClass;
import org.springframework.data.util.Pair;

@UtilityClass
public class StockMapper {

    public static Pair<Stock, StockPurchase> toStock(StockRequest request) {

        final Stock stock = Stock
                .builder()
                .stock(request.getStock())
                .build();

        final StockPurchase stockPurchase = StockPurchase
                .builder()
                .date(request.getDate())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .build();

        return Pair.of(stock, stockPurchase);
    }

}
