package com.gmartins.stocktracker.client.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrapiStockDataResponse {

    private String symbol;
    private String currency;
    private String shortName;
    private String longName;
    private Double regularMarketPrice;
    private Double regularMarketDayHigh;
    private Double regularMarketDayLow;
    private Double regularMarketPreviousClose;
    private String logourl;
}
