package com.gft.challenge.adapters.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.gft.challenge.domain.model.Currency;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonPropertyOrder({ "brandId", "startDate", "endDate", "priceList", "productId", "priority", "unitPrice", "curr" })
public class PriceResponse {
    private String brandId;
    @JsonFormat(pattern="yyyy-MM-dd-HH.mm.ss")
    private LocalDateTime startDate;
    @JsonFormat(pattern="yyyy-MM-dd-HH.mm.ss")
    private LocalDateTime endDate;
    private String priceList;
    private String productId;
    private String finalPrice;
}
