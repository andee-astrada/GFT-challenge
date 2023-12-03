package com.gft.challenge.util;

import com.gft.challenge.adapters.rest.dto.PriceResponse;
import com.gft.challenge.domain.model.Price;

public class PriceMapper {
    private PriceMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static PriceResponse toPriceResponse(Price price){
        return PriceResponse.builder()
                .productId(price.getProductId())
                .brandId(price.getBrandId())
                .priceList(price.getPriceList())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .finalPrice(price.getCurr() + " " + price.getUnitPrice().toPlainString())
                .build();
    }
}
