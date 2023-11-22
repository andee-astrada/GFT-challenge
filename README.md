# GFT-challenge

The application allows to search for product prices, according to a specific brand and timeframe. 
During the application start, the content of the file prices.csv is loaded on memory.

File prices.csv follows the format below:

| BRAND_ID | START_DATE          | END_DATE            | PRICE_LIST | PRODUCT_ID | PRIORITY | PRICE   | CURR |
|----------|---------------------|---------------------|------------|------------|----------|---------|------|
| 1        | 2020-06-14-00.00.00 | 2020-12-31-23.59.59 | 1          | 35455      | 0        | 35.50   | EUR  |


## Run the app

    ./gradlew bootRun

## Run the tests

    ./gradlew test

# REST API

## Search for a product

The search parameters to be included in the body are:
- BrandID: foreign key of the brand
- ProductId: id of the product
- request_date: date and time of when we need to know the price

The search will return a result if there is a price within the date sent. In case more than one result is present, only the one with the highest priority will be returned.

### Request

`POST /search/`

    curl --location 'localhost:8080/prices/search' \
    --header 'Accept: application/json' \
    --header 'Content-Type: application/json' \
    --data '{
    "brand_id": "1",
    "product_id": "35455",
    "request_date": "2023-06-16-21.00.00"
    }'


### Response

    {
    "brand_id": "1",
    "start_date": "2020-06-15-00.00.00",
    "end_date": "2020-06-15-11.00.00",
    "price_list": "3",
    "product_id": "35455",
    "priority": 1,
    "unit_price": 30.5,
    "curr": "EUR"
    }


