package com.gmartins.stocktracker.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "stock")
public class Stock {

    @Id
    private String id;

    private String stock;

    @Transient
    private BigDecimal price;

    @DBRef
    private List<StockPurchase> purchases;

    @DBRef
    private User user;
}
