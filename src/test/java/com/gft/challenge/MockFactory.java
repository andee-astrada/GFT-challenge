package com.gft.challenge;

import com.gft.challenge.model.Currency;
import com.gft.challenge.model.Price;
import com.gft.challenge.model.SearchCriteria;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockFactory {
    public static Price getPrice(){
        Price price = new Price();
        price.setId(UUID.randomUUID());
        price.setBrandId("1");
        price.setStartDate(LocalDateTime.now().minusDays(1));
        price.setEndDate(LocalDateTime.now().plusDays(1));
        price.setPriceList("3");
        price.setProductId("35455");
        price.setPriority(1);
        price.setUnitPrice(BigDecimal.valueOf(35.82));
        price.setCurr(Currency.EUR);

        return price;
    }

    public static Price getPrice2(){
        Price price = new Price();
        price.setId(UUID.randomUUID());
        price.setBrandId("1");
        price.setStartDate(LocalDateTime.now().minusDays(1));
        price.setEndDate(LocalDateTime.now().plusDays(1));
        price.setPriceList("2");
        price.setProductId("35455");
        price.setPriority(1);
        price.setUnitPrice(BigDecimal.valueOf(39.82));
        price.setCurr(Currency.EUR);

        return price;
    }

    public static List<Price> getOneResultPrice(){
        List<Price> priceList = new ArrayList<>();
        priceList.add(getPrice());
        return priceList;
    }

    public static List<Price> getMultipleResultPrice(){
        List<Price> priceList = new ArrayList<>();
        priceList.add(getPrice());
        priceList.add(getPrice2());
        return priceList;
    }

    public static List<Price> getNoResultPrice(){
        return new ArrayList<>();
    }

    public static SearchCriteria getCriteria(){
        SearchCriteria criteria = new SearchCriteria();
        criteria.setBrandId("1");
        criteria.setProductId("35455");
        criteria.setRequestDate(LocalDateTime.now());
        return criteria;
    }
}
