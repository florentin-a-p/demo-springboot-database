package com.countries.database.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
@Slf4j
public class CountryService {
  @Autowired
  CountryRepository countryRepository;
  public List<Country> getCountries() {
    return (List<Country>) countryRepository.findAll();
  }

  public Country getCountriesWithId(Integer countryId) {
    return countryRepository.findById(countryId).get();
  }

  public Country getCountriesWithName(String countryName) {
    List<Country> countryList = (List<Country>) countryRepository.findAll();
    for (Country country:countryList) {
      if (countryName.equals(country.getCountryName())) {
        return country;
      }
    }
    return new Country(0,"NONE","NONE");
  }

  public Country addCountry(Country addedCountry) {
    long size = countryRepository.count();
    log.info("[FLO] REPOSITORY SIZE IS: " + size);
    Integer newId = (int) size + 1;
    log.info("[FLO] newId IS: " + newId);
    Country country = new Country(newId, addedCountry.getCountryName(), addedCountry.getCapital());
    log.info("[FLO] newly added country is: " + country.getId() + country.getCountryName() + country.getCapital());
    countryRepository.save(country);
    log.info("[FLO] newly added country in repository is: " + countryRepository.findById(newId).get());
    return countryRepository.findById(newId).get();
  }

  public Country updateCountry(Country updatedCountry) {
    countryRepository.save(updatedCountry);
    return countryRepository.findById(updatedCountry.getId()).get();
  }

  public ResponseMessage deleteCountry(Integer countryId) {
    countryRepository.deleteById(countryId);
    return new ResponseMessage("Country deleted");
  }
}
