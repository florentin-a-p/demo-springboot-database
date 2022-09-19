package com.countries.database.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.countries.database.demo")
public class CountriesApplication {
  public static void main(String[] args) {
    SpringApplication.run(CountriesApplication.class, args);
  }
}
