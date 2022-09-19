package com.countries.database.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
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
    Integer newId = (int) size + 1;
    Country country = new Country(newId, addedCountry.getCountryName(), addedCountry.getCapital());
    countryRepository.save(country);
    return countryRepository.findById(newId).get();
  }

  public Country updateCountry(Country updatedCountry) {
    countryRepository.save(updatedCountry);
    return countryRepository.findById(updatedCountry.getId()).get();
  }

  public ResponseMessage deleteCountry(Integer countryId) {
    // how to print out some messages to console without having to return it and finish the process?

    countryRepository.deleteById(countryId);
    return new ResponseMessage("Country deleted");
  }
}
