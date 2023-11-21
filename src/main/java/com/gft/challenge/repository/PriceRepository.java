package com.gft.challenge.repository;

import com.gft.challenge.model.Price;
import com.gft.challenge.model.SearchCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    @Query(value = "SELECT p FROM price AS p " +
            "WHERE brandId = :brandId " +
            "AND productId = :productId " +
            "AND startDate <= :searchDate " +
            "AND endDate >= :searchDate")
    public List<Price> findByCriteria(@Param("brandId") String brandId,
                                      @Param("productId") String productId,
                                      @Param("searchDate") LocalDateTime searchDate);

    @Query(value = "SELECT p FROM price AS p " +
            "WHERE brandId = :brandId " +
            "AND productId = :productId ")
    public List<Price> findByCriteria2(@Param("brandId") String brandId,
                                      @Param("productId") String productId);
}
