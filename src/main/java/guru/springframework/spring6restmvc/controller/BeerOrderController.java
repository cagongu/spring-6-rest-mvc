package guru.springframework.spring6restmvc.controller;

import org.springframework.stereotype.Controller;

public class BeerOrderController {

    public static final String BEER_ORDER_PATH = "/api/v1/beerorder";
    public static final String BEER_ORDER_PATH_ID = BEER_ORDER_PATH + "/{beerOrderId}";
}