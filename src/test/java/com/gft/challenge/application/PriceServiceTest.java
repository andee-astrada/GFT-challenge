package com.gft.challenge.application;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.gft.challenge.MockFactory;
import com.gft.challenge.adapters.rest.dto.PriceResponse;
import com.gft.challenge.adapters.rest.dto.SearchCriteria;
import com.gft.challenge.adapters.database.JPAPriceRepository;
import com.gft.challenge.exception.DataInconsistencyException;
import com.gft.challenge.exception.PriceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @InjectMocks
    private PriceService service;

    @Mock
    JPAPriceRepository repository;

    @Test
    void initialDataLoad_ok() {
        ReflectionTestUtils.setField(service, "idlFilePath", "build/resources/test/pricesTest.csv");
        service.initialDataLoad();
        verify(repository).saveAll(any());
    }

    @Test
    void initialDataLoad_invalidFormatException() {
        Logger fooLogger = (Logger) LoggerFactory.getLogger(PriceService.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        fooLogger.addAppender(listAppender);

        ReflectionTestUtils.setField(service, "idlFilePath", "build/resources/test/invalidPricesTest.csv");

        service.initialDataLoad();

        List<ILoggingEvent> logsList = listAppender.list;
        assertEquals(1, logsList.size());
        assertEquals("Error while trying to load value", logsList.get(0).getFormattedMessage().substring(0,32));
    }

    @Test
    void initialDataLoad_IOException() {
        Logger fooLogger = (Logger) LoggerFactory.getLogger(PriceService.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        fooLogger.addAppender(listAppender);

        ReflectionTestUtils.setField(service, "idlFilePath", "build/resources/test/nonExistentFile.csv");

        service.initialDataLoad();

        List<ILoggingEvent> logsList = listAppender.list;
        assertEquals(1, logsList.size());
        assertEquals("Error while trying to open initial data load file", logsList.get(0).getFormattedMessage().substring(0,49));
    }

    @Test
    void searchPrice_ok() throws PriceNotFoundException, DataInconsistencyException {
        SearchCriteria criteria = MockFactory.getCriteria();
        PriceResponse expectedResult = MockFactory.getPriceResult();

        when(repository.findByCriteria(
                criteria.getBrandId(),
                criteria.getProductId(),
                criteria.getRequestDate()))
                .thenReturn(MockFactory.getOneResultPrice());

        PriceResponse actualResult = service.searchPrice(criteria);
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void searchPrice_multipleResults() {
        SearchCriteria criteria = MockFactory.getCriteria();

        when(repository.findByCriteria(
                criteria.getBrandId(),
                criteria.getProductId(),
                criteria.getRequestDate()))
                .thenReturn(MockFactory.getMultipleResultPrice());

        assertThrows(DataInconsistencyException.class, () -> {
            service.searchPrice(criteria);
        });
    }

    @Test
    void searchPrice_notFound() {
        SearchCriteria criteria = MockFactory.getCriteria();

        when(repository.findByCriteria(
                criteria.getBrandId(),
                criteria.getProductId(),
                criteria.getRequestDate()))
                .thenReturn(MockFactory.getNoResultPrice());

        assertThrows(PriceNotFoundException.class, () -> {
            service.searchPrice(criteria);
        });
    }

}
