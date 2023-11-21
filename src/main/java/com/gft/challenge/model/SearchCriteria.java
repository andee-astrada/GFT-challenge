package com.gft.challenge.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchCriteria {
    @JsonFormat(pattern="yyyy-MM-dd-HH.mm.ss")
    private LocalDateTime requestDate;
    private String brandId;
    private String productId;
}
