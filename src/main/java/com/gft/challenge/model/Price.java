package com.gft.challenge.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "price")
@JsonPropertyOrder({ "brandId", "startDate", "endDate", "priceList", "productId", "priority", "unitPrice", "curr" })
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String brandId;
    @JsonFormat(pattern="yyyy-MM-dd-HH.mm.ss")
    private LocalDateTime startDate;
    @JsonFormat(pattern="yyyy-MM-dd-HH.mm.ss")
    private LocalDateTime endDate;
    private String priceList;
    private String productId;
    private Integer priority;
    private BigDecimal unitPrice;
    //TODO pasar a enum
    private Currency curr;

    /*public Price(String brandId, LocalDateTime startDate, LocalDateTime endDate, String priceList,
                 String productId, Integer priority, BigDecimal unitPrice, String curr){
        this.brandId = brandId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceList = priceList;
        this.productId = productId;
        this.priority = priority;
        this.unitPrice = unitPrice;
        this.curr = curr;
    }*/
}
