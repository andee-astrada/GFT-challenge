package com.gft.challenge.controller;

import com.gft.challenge.model.Price;
import com.gft.challenge.model.SearchCriteria;
import com.gft.challenge.service.PriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/prices")
@RequiredArgsConstructor
@Slf4j
public class PriceController {
    private final PriceService service;

    @PostMapping("/search")
    public ResponseEntity<?> searchPrices(
            @RequestBody SearchCriteria criteria
    ) {
        try {
            //TODO hace falta un priceResponse?
            List<Price> priceResponse = service.searchPrice(criteria);
            if(!priceResponse.isEmpty())
                return new ResponseEntity<>(priceResponse, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
