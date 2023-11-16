package com.gft.challenge.service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.dataformat.csv.CsvParser;

import com.gft.challenge.model.Price;
import com.gft.challenge.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class PriceService {

    private final PriceRepository repository;

    public void initialDataLoad() {
        File inputCsv = new File("prices.csv");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
        CsvMapper csvMapper = new CsvMapper();
        csvMapper.enable(CsvParser.Feature.TRIM_SPACES);
        csvMapper.registerModule(new JavaTimeModule());
        csvMapper.setDateFormat(dateFormat);
                
        try (MappingIterator<Price> pricesIterator = csvMapper
                .readerWithTypedSchemaFor(Price.class)
                .readValues(inputCsv)){

            List<Price> prices = pricesIterator.readAll();
            repository.saveAll(prices);
            log.info("Initial data load completed");

        } catch(InvalidFormatException e) {
            log.error("Error while trying to load value {} from initial load CSV file: {}", e.getValue(), e.getMessage());
        } catch(IOException e) {
            log.error("Error while trying to open initial data load file: {}", e.getMessage());
        } catch(Exception e){
            log.error("Hubo otro error: {}", e.getMessage());
        }
    }
}