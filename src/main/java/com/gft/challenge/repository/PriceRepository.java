package com.gft.challenge.repository;

import com.gft.challenge.model.Price;
import com.gft.challenge.model.SearchCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    @Query(value = "SELECT p FROM price AS p " +
            "WHERE brandId = :brandId " +
            "AND productId = :productId " +
            "AND startDate <= :searchDate " +
            "AND endDate >= :searchDate " +
            "AND priority = (" +
            "   SELECT MAX(p2.priority) FROM price as p2" +
            "   WHERE p2.brandId = :brandId " +
            "   AND p2.productId = :productId" +
            "   AND p2.startDate <= :searchDate " +
            "   AND p2.endDate >= :searchDate ) ")
    public List<Price> findByCriteria(@Param("brandId") String brandId,
                                      @Param("productId") String productId,
                                      @Param("searchDate") LocalDateTime searchDate);


}
