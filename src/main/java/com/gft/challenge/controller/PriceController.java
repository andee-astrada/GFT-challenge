package com.gft.challenge.controller;

import com.gft.challenge.exception.FieldValidationException;
import com.gft.challenge.model.Price;
import com.gft.challenge.model.SearchCriteria;
import com.gft.challenge.service.PriceService;
import com.gft.challenge.util.EntityValidator;
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
            EntityValidator.validate(criteria);
            List<Price> priceResponse = service.searchPrice(criteria);

            return switch (priceResponse.size()) {
                case 1 -> new ResponseEntity<>(priceResponse.get(0), HttpStatus.OK);
                case 0 -> new ResponseEntity<>(HttpStatus.NOT_FOUND);
                default -> new ResponseEntity<>("Data error, contact support", HttpStatus.INTERNAL_SERVER_ERROR);
            };

        } catch (FieldValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
