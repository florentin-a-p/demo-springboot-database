package com.countries.database.demo;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class CountryService {
  // create the dummy existing data first, stored in a hashmap
  public CountryService() {
    countryMap.put(1, country1);
    countryMap.put(2, country2);
    countryMap.put(3, country3);
  }

  HashMap<Integer, Country> countryMap = new HashMap<Integer, Country>();
  Country country1 = new Country(1,"Indonesia","Jakarta");
  Country country2 = new Country(2,"Malaysia","Kuala Lumpur");
  Country country3 = new Country(3,"Thailand","Bangkok");

  public List getCountries() {
    List<Country> list = new ArrayList<Country>(countryMap.values());
    return list;
  }

  public Country getCountriesWithId(int countryId) {
    return countryMap.get(countryId);
  }

  public Country getCountriesWithName(String countryName) {
    for(int i=0;i<countryMap.size();i++){
      if (Objects.equals(countryName, countryMap.get(i + 1).getCountryName())) {
        return countryMap.get(i+1);
      }
    }
    return new Country(0,"NONE","NONE");
  }

  public Country addCountry(Country addedCountry) {
    int maxId = countryMap.size()+1;
    addedCountry.setId(maxId);
    countryMap.put(maxId,addedCountry);
    return countryMap.get(maxId);
  }

  public Country updateCountry(Country updatedCountry) {
    countryMap.put(updatedCountry.getId(),updatedCountry);
    return countryMap.get(updatedCountry.getId());
  }

  public ResponseMessage deleteCountry(int countryId) {
    countryMap.remove(countryId);
    return new ResponseMessage("country deleted");
  }
}
