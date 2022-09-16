package com.countries.database.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
//@ComponentScan(basePackages = "com.countries.demo")
public class CountriesApplication {
  public static void main(String[] args) {
    SpringApplication.run(CountriesApplication.class, args);
  }
}
