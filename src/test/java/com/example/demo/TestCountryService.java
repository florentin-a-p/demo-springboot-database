package com.example.demo;

import com.countries.database.demo.Country;
import com.countries.database.demo.CountryRepository;
import com.countries.database.demo.CountryService;
import com.countries.database.demo.ResponseMessage;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;

@SpringBootTest(classes={CountryService.class})
public class TestCountryService {
	@Mock
	private CountryRepository countryRepository;
	@Mock
	private ResponseMessage responseMessage;
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
	@Test
	public void getCountriesWithIdWithDataExistTblExistDbExist_WillCallRepositoryFindAllAndReturnAllCountries () {
		// Given
		int countryId = 1;
		Country country = new Country(countryId,"US","Washington");
		given(countryRepository.findById(countryId)).willReturn(Optional.of(country));

		// When
		Country actualCountry = countryService.getCountriesWithId(1);

		// Then
		assertEquals(country,actualCountry);
		then(countryRepository).should(times(1)).findById(countryId);
		then(countryRepository).should(times(0)).findById(2);
	}
	// end region scenario for getCountriesWithId //

	// start region scenario for getCountriesWithName //
	@Test
	public void getCountriesWithNameWithCountryExistDataExistTblExistDbExist_WillCallRepositoryFindAllAndReturnAllCountries () {
		// Given
		String countryName = "Vietnam";
		List<Country> countryList = new ArrayList<Country>();
		countryList.add(new Country(1,"US","Washington"));
		countryList.add(new Country(2,"China","Beijing"));
		countryList.add(new Country(3,countryName,"Hanoi"));
		given(countryRepository.findAll()).willReturn(countryList);

		// When
		Country actualCountry = countryService.getCountriesWithName(countryName);

		// Then
		assertEquals(countryName,actualCountry.getCountryName());
		then(countryRepository).should(times(1)).findAll();
	}

	@Test
	public void getCountriesWithNameWithCountryNotExistDataExistTblExistDbExist_WillCallRepositoryFindAllAndReturnAllCountries () {
		String countryName = "Vietnam";
		List<Country> countryList = new ArrayList<Country>();
		countryList.add(new Country(1,"US","Washington"));
		countryList.add(new Country(2,"China","Beijing"));
		given(countryRepository.findAll()).willReturn(countryList);

		// When
		Country actualCountry = countryService.getCountriesWithName(countryName);

		// Then
		assertEquals("NONE",actualCountry.getCountryName());
		then(countryRepository).should(times(1)).findAll();
	}
	// end region scenario for getCountriesWithName //

	// start region scenario for addCountry //
	@Test
	public void addCountryWithCountryNotExistDataExistTblExistDbExist_WillCallRepositoryFindAllAndReturnAllCountries () {
		// Given
		Country country = new Country();
		country.setCountryName("US");
		country.setCapital("Washington");

		int id = 1;
		Country expectedCountry = new Country(id, country.getCountryName(), country.getCapital());
		given(countryRepository.count()).willReturn((long) id);
		given(countryRepository.findById(anyInt())).willReturn(Optional.of(expectedCountry));

		// When
		Country actualCountry = countryService.addCountry(country);

		// Then
		then(countryRepository).should(times(1)).count();
		then(countryRepository).should(times(1)).save(any(Country.class));
		then(countryRepository).should(atLeast(1)).findById(anyInt());
		assertEquals(expectedCountry,actualCountry);
	}
	// end region scenario for addCountry //

	// start region scenario for updateCountry //
	@Test
	public void updateCountryWithCountryExistDataExistTblExistDbExist_WillCallRepositoryFindAllAndReturnAllCountries () {
		// Given
		int id = 1;
		Country country = new Country(id,"US","Washington");
		given(countryRepository.findById(id)).willReturn(Optional.of(country));

		// When
		Country actualCountry = countryService.updateCountry(country);

		// Then
		then(countryRepository).should(times(1)).save(country);
		then(countryRepository).should(times(1)).findById(country.getId());
		assertEquals(country,actualCountry);
	}
	// end region scenario for updateCountry //

	// start region scenario for deleteCountry //
	@Test
	public void deleteCountryWithCountryExistDataExistTblExistDbExist_WillCallRepositoryFindAllAndReturnAllCountries () {
		// Given
		int id = 1;
		Country country = new Country(id,"US","Washington");
		given(countryRepository.findById(id)).willReturn(Optional.of(country));

		// When
		ResponseMessage actualResponseMessage = countryService.deleteCountry(id);

		// Then
		then(countryRepository).should(times(1)).deleteById(id);
		assertEquals(new ResponseMessage("Country deleted").getMsg(),actualResponseMessage.getMsg());
	}
	// end region scenario for deleteCountry //
}
