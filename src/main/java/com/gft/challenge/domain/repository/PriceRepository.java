package com.gft.challenge.domain.repository;

import com.gft.challenge.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepository {
    List<Price> findByCriteria(String brandId, String productId, LocalDateTime searchDate);
}