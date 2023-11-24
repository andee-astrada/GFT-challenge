package com.gft.challenge.controller;

import com.gft.challenge.MockFactory;
import com.gft.challenge.model.Price;
import com.gft.challenge.model.SearchCriteria;
import com.gft.challenge.service.PriceService;
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
    void searchPrices_ok(){
        when(service.searchPrice(any())).thenReturn(MockFactory.getOneResultPrice());

        ResponseEntity<Price> response =
                (ResponseEntity<Price>) controller.searchPrices(MockFactory.getCriteria());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void searchPrices_notFound(){
        when(service.searchPrice(any())).thenReturn(MockFactory.getNoResultPrice());

        ResponseEntity<Price> response =
                (ResponseEntity<Price>) controller.searchPrices(MockFactory.getCriteria());

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void searchPrices_multipleResults(){
        when(service.searchPrice(any())).thenReturn(MockFactory.getMultipleResultPrice());

        ResponseEntity<Price> response =
                (ResponseEntity<Price>) controller.searchPrices(MockFactory.getCriteria());

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void searchPrices_invalidCriteria(){
        SearchCriteria criteria = MockFactory.getCriteria();
        criteria.setRequestDate(null);

        ResponseEntity<Price> response =
                (ResponseEntity<Price>) controller.searchPrices(criteria);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void searchPrices_invalidCriteria2(){
        SearchCriteria criteria = MockFactory.getCriteria();
        criteria.setProductId(null);

        ResponseEntity<Price> response =
                (ResponseEntity<Price>) controller.searchPrices(criteria);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void searchPrices_invalidCriteria3(){
        SearchCriteria criteria = MockFactory.getCriteria();
        criteria.setBrandId(null);

        ResponseEntity<Price> response =
                (ResponseEntity<Price>) controller.searchPrices(criteria);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
