package com.stockms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Application entry point. */
@SpringBootApplication
public class StockMsApplication {

    protected StockMsApplication() {
    }

    public static void main(String[] args) {
        SpringApplication.run(StockMsApplication.class, args);
    }
}
