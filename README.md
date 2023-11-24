# GFT-challenge

The application allows to search for product prices, according to a specific brand and timeframe. 
During the application start, the content of the file prices.csv is loaded on memory using SQLite.

File *prices.csv* follows the format below:

| BRAND_ID | START_DATE          | END_DATE            | PRICE_LIST | PRODUCT_ID | PRIORITY | PRICE   | CURR |
|----------|---------------------|---------------------|------------|------------|----------|---------|------|
| 1        | 2020-06-14-00.00.00 | 2020-12-31-23.59.59 | 1          | 35455      | 0        | 35.50   | EUR  |


## Run application

    ./gradlew bootRun

## Run unit tests

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


# Postman test cases
## Test data (loaded as part of the initial data load)
| BRAND_ID | START_DATE           | END_DATE             | PRICE_LIST | PRODUCT_ID | PRIORITY | PRICE  | CURR |
|----------|----------------------|----------------------|------------|------------|----------|--------|------|
| 1        | 2020-06-14-00.00.00  | 2020-12-31-23.59.59  | 1          | 35455      | 0        | 35.50  | EUR  |
| 1        | 2020-06-14-15.00.00  | 2020-06-14-18.30.00  | 2          | 35455      | 1        | 25.45  | EUR  |
| 1        | 2020-06-15-00.00.00  | 2020-06-15-11.00.00  | 3          | 35455      | 1        | 30.50  | EUR  |
| 1        | 2020-06-15-16.00.00  | 2020-12-31-23.59.59  | 4          | 35455      | 1        | 38.95  | EUR  |

### Test case 1 
Expected result: 35.5 EUR

    curl --location 'localhost:8080/prices/search' \
    --header 'Accept: application/json' \
    --header 'Content-Type: application/json' \
    --data '{
    "brand_id": "1",
    "product_id": "35455",
    "request_date": "2020-06-14-10.00.00"
    }'

### Test case 2
Expected result: 25.45 EUR

    curl --location 'localhost:8080/prices/search' \
    --header 'Accept: application/json' \
    --header 'Content-Type: application/json' \
    --data '{
    "brand_id": "1",
    "product_id": "35455",
    "request_date": "2020-06-14-16.00.00"
    }'

### Test case 3
Expected result: 35.5 EUR

    curl --location 'localhost:8080/prices/search' \
    --header 'Accept: application/json' \
    --header 'Content-Type: application/json' \
    --data '{
    "brand_id": "1",
    "product_id": "35455",
    "request_date": "2020-06-14-21.00.00"
    }'

### Test case 4
Expected result: 30.5 EUR

    curl --location 'localhost:8080/prices/search' \
    --header 'Accept: application/json' \
    --header 'Content-Type: application/json' \
    --data '{
    "brand_id": "1",
    "product_id": "35455",
    "request_date": "2020-06-15-10.00.00"
    }'

### Test case 5
Expected result: 38.95 EUR

    curl --location 'localhost:8080/prices/search' \
    --header 'Accept: application/json' \
    --header 'Content-Type: application/json' \
    --data '{
    "brand_id": "1",
    "product_id": "35455",
    "request_date": "2020-06-16-21.00.00"
    }'