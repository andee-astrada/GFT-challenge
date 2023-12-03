package com.gft.challenge.adapters.rest;

import com.gft.challenge.MockFactory;
import com.gft.challenge.adapters.rest.dto.PriceResponse;
import com.gft.challenge.domain.model.Price;
import com.gft.challenge.adapters.rest.PriceController;
import com.gft.challenge.adapters.rest.dto.SearchCriteria;
import com.gft.challenge.application.PriceService;
import com.gft.challenge.exception.DataInconsistencyException;
import com.gft.challenge.exception.PriceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceControllerTest {
    @InjectMocks
    private PriceController controller;

    @Mock
    private PriceService service;

    @Test
    void searchPrices_ok() throws PriceNotFoundException, DataInconsistencyException {
        when(service.searchPrice(any())).thenReturn(MockFactory.getPriceResult());

        ResponseEntity<PriceResponse> response =
                (ResponseEntity<PriceResponse>) controller.searchPrices(MockFactory.getCriteria());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void searchPrices_notFound() throws PriceNotFoundException, DataInconsistencyException {
        when(service.searchPrice(any())).thenThrow(new PriceNotFoundException());

        ResponseEntity<PriceResponse> response =
                (ResponseEntity<PriceResponse>) controller.searchPrices(MockFactory.getCriteria());

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void searchPrices_multipleResults() throws PriceNotFoundException, DataInconsistencyException {
        when(service.searchPrice(any())).thenThrow(new DataInconsistencyException());

        ResponseEntity<PriceResponse> response =
                (ResponseEntity<PriceResponse>) controller.searchPrices(MockFactory.getCriteria());

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void searchPrices_invalidCriteria(){
        SearchCriteria criteria = MockFactory.getCriteria();
        criteria.setRequestDate(null);

        ResponseEntity<PriceResponse> response =
                (ResponseEntity<PriceResponse>) controller.searchPrices(criteria);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void searchPrices_invalidCriteria2(){
        SearchCriteria criteria = MockFactory.getCriteria();
        criteria.setProductId(null);

        ResponseEntity<PriceResponse> response =
                (ResponseEntity<PriceResponse>) controller.searchPrices(criteria);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void searchPrices_invalidCriteria3(){
        SearchCriteria criteria = MockFactory.getCriteria();
        criteria.setBrandId(null);

        ResponseEntity<PriceResponse> response =
                (ResponseEntity<PriceResponse>) controller.searchPrices(criteria);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
