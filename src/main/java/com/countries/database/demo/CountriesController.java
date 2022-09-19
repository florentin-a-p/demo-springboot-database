package com.countries.database.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CountriesController {
  @Autowired
  CountryService countryService;

  @GetMapping(path="/getCountries")
  @ResponseBody
  public List getCountries() {
    return countryService.getCountries();
  }

  @GetMapping(path="/getCountries/{countryId}")
  public Country getCountriesWithId(@PathVariable Integer countryId) {
    return countryService.getCountriesWithId(countryId);
  }

  @GetMapping(path="/getCountries/countryName")
  public Country getCountriesWithName(@RequestParam(value="name") String countryName) {
    return countryService.getCountriesWithName(countryName);
  }

  @PostMapping(path="addCountry")
  public Country addCountry(@RequestBody Country addedCountry) {
    return countryService.addCountry(addedCountry);
  }

  @PutMapping(path="updateCountry")
  public Country updateCountry(@RequestBody Country updatedCountry) {
    return countryService.updateCountry(updatedCountry);
  }

  @DeleteMapping(path="deleteCountry/{countryId}")
  public ResponseMessage deleteCountry(@PathVariable Integer countryId) {
    return countryService.deleteCountry(countryId);
  }
}
