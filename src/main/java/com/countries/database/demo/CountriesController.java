package com.countries.database.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class CountriesController {
  @Autowired(required = false)
  CountryService countryService;

  @GetMapping(path="/getCountries")
  public ResponseEntity<List<Country>> getCountries() {
    log.info("[FLO] /getCountries is called");
    try {
      return new ResponseEntity<>(countryService.getCountries(), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @GetMapping(path="/getCountries/{countryId}")
  public ResponseEntity<Country> getCountriesWithId(@PathVariable Integer countryId) {
    log.info("[FLO] /getCountries/{countryId} is called");
    try {
      return new ResponseEntity<>(countryService.getCountriesWithId(countryId), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @GetMapping(path="/getCountries/countryName")
  public ResponseEntity<Country> getCountriesWithName(@RequestParam(value="name") String countryName) {
    log.info("[FLO] /getCountries/countryName is called");
    try {
      return new ResponseEntity<>(countryService.getCountriesWithName(countryName), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @PostMapping(path="addCountry")
  public ResponseEntity<Country> addCountry(@RequestBody Country addedCountry) {
    log.info("[FLO] /addCountry is called");
    try {
      return new ResponseEntity<>(countryService.addCountry(addedCountry), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @PutMapping(path="updateCountry")
  public ResponseEntity<Country> updateCountry(@RequestBody Country updatedCountry) {
    log.info("[FLO] /updateCountry is called");
    try {
      return new ResponseEntity<>(countryService.updateCountry(updatedCountry), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @DeleteMapping(path="deleteCountry/{countryId}")
  public ResponseEntity<ResponseMessage> deleteCountry(@PathVariable Integer countryId) {
    log.info("[FLO] deleteCountry/{countryId} is called");
    try {
      return new ResponseEntity<>(countryService.deleteCountry(countryId), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }
}
