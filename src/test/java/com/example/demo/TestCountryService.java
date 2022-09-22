package com.example.demo;

import com.countries.database.demo.Country;
import com.countries.database.demo.CountryRepository;
import com.countries.database.demo.CountryService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@SpringBootTest(classes={CountryService.class})
public class TestCountryService {
	@Mock
	private CountryRepository countryRepository;
	@InjectMocks
	private CountryService countryService;

	// start region scenario for getCountries //
	@Test
	public void getCountriesWithDataExistTblExistDbExist_WillCallRepositoryFindAllAndReturnAllCountries () {
		// Given
		List<Country> countryList = new ArrayList<Country>();
		countryList.add(new Country(1,"US","Washington"));
		countryList.add(new Country(2,"China","Beijing"));
		given(countryRepository.findAll()).willReturn(countryList);

		// When
		List<Country> actualCountryList = countryService.getCountries();

		// Then
		assertEquals(countryList,actualCountryList);
		assertEquals(countryList.size(),actualCountryList.size());
		then(countryRepository).should(times(1)).findAll();
	}

	// end region scenario for getCountries //


	// start region scenario for getCountriesWithId //
	// end region scenario for getCountriesWithId //

	// start region scenario for getCountriesWithName //
	// end region scenario for getCountriesWithName //

	// start region scenario for addCountry //
	// end region scenario for addCountry //

	// start region scenario for updateCountry //
	// end region scenario for updateCountry //

	// start region scenario for deleteCountry //
	// end region scenario for deleteCountry //
}
