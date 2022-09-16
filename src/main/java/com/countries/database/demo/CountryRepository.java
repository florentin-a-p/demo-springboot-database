package com.countries.database.demo;

import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Long>  {
  Country findByCountryName(String countryName);

  Country findById(long id);
}
