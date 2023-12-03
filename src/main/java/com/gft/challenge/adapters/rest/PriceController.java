package com.gft.challenge.adapters.rest;

import com.gft.challenge.adapters.rest.dto.SearchCriteria;
import com.gft.challenge.application.PriceService;
import com.gft.challenge.exception.DataInconsistencyException;
import com.gft.challenge.exception.FieldValidationException;
import com.gft.challenge.exception.PriceNotFoundException;
import com.gft.challenge.util.EntityValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
            return ResponseEntity.ok(service.searchPrice(criteria));
            //List<PriceResponse> priceResponse = service.searchPrice(criteria);

        } catch (FieldValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (PriceNotFoundException e){
            return ResponseEntity.notFound().build();
        //} catch (DataInconsistencyException e){
        //    return ResponseEntity.internalServerError().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
