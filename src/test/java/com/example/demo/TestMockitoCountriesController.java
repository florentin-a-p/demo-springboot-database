package com.example.demo;

import com.countries.database.demo.CountriesController;
import com.countries.database.demo.Country;
import com.countries.database.demo.CountryService;
import com.countries.database.demo.ResponseMessage;
import lombok.extern.java.Log;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@SpringBootTest(classes={CountriesController.class})
public class TestMockitoCountriesController {
  @Mock
  private CountryService countryService;
  @Mock
  private Log log;
  @InjectMocks
  private CountriesController countriesController;

  // start region scenario for getCountries //
  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void getCountriesNoException_WillReturnCorrectResponse(CapturedOutput output) {
    // Given
    List<Country> countryList = new ArrayList<Country>();
    countryList.add(new Country(1,"India","New Delhi"));
    countryList.add(new Country(2,"Myanmar","Naypyidaw"));
    given(countryService.getCountries()).willReturn(countryList);

    // When
    ResponseEntity<List<Country>> actualResponse = countriesController.getCountries();

    // Then
    assertEquals(HttpStatus.OK,actualResponse.getStatusCode());
    assertEquals(countryList,actualResponse.getBody());
    then(countryService).should(times(1)).getCountries();
    assertThat(output).contains("[FLO] /getCountries is called");
  }

  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void getCountriesDbExistTblNotExist_WillReturnCorrectResponse(CapturedOutput output) {
    // Given
    given(countryService.getCountries()).willAnswer( invocation -> { throw new InvalidDataAccessResourceUsageException(""); });

    // When
    ResponseEntity<List<Country>> actualResponse = countriesController.getCountries();

    // Then
    assertThat(output).contains("[FLO] /getCountries is called");
    assertEquals(HttpStatus.GONE,actualResponse.getStatusCode());
    assertNull(actualResponse.getBody());
  }

  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void getCountriesDbDeletedWhileSpringRunning_WillReturnCorrectResponse(CapturedOutput output) {
    // Given
    given(countryService.getCountries()).willAnswer( invocation -> { throw new CannotCreateTransactionException(""); });

    // When
    ResponseEntity<List<Country>> actualResponse = countriesController.getCountries();

    // Then
    assertThat(output).contains("[FLO] /getCountries is called");
    assertEquals(HttpStatus.EXPECTATION_FAILED,actualResponse.getStatusCode());
    assertNull(actualResponse.getBody());
  }

  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void getCountriesTableEmpty_WillReturnCorrectResponse(CapturedOutput output) {
    // Given
    List<Country> countryList = new ArrayList<Country>();
    given(countryService.getCountries()).willReturn(countryList);

    // When
    ResponseEntity<List<Country>> actualResponse = countriesController.getCountries();

    // Then
    assertThat(output).contains("[FLO] /getCountries is called");
    assertEquals(HttpStatus.NO_CONTENT,actualResponse.getStatusCode());
    assertNull(actualResponse.getBody());
  }

  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void getCountriesOtherException_WillReturnCorrectResponse(CapturedOutput output) {
    // Given
    given(countryService.getCountries()).willAnswer( invocation -> { throw new Exception(); });

    // When
    ResponseEntity<List<Country>> actualResponse = countriesController.getCountries();

    // Then
    assertThat(output).contains("[FLO] /getCountries is called");
    assertEquals(HttpStatus.I_AM_A_TEAPOT,actualResponse.getStatusCode());
    assertNull(actualResponse.getBody());
  }
  // end region scenario for getCountries //

  // start region scenario for getCountriesWithId //
  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void getCountriesWithIdNoException_WillReturnCorrectResponse(CapturedOutput output) {
    // Given
    Integer countryId = 1;
    Country country = new Country(1,"Laos","Vientiane");
    given(countryService.getCountriesWithId(countryId)).willReturn(country);

    // When
    ResponseEntity<Country> actualResponse = countriesController.getCountriesWithId(countryId);

    // Then
    assertEquals(HttpStatus.OK,actualResponse.getStatusCode());
    assertEquals(country,actualResponse.getBody());
    then(countryService).should(times(1)).getCountriesWithId(countryId);
    assertThat(output).contains("[FLO] /getCountries/{countryId} is called");
  }

  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void getCountriesWithIdException_WillReturnCorrectResponse(CapturedOutput output) {
    // Given
    EasyRandom easyRandom = new EasyRandom();
    Integer countryId = easyRandom.nextInt();
    given(countryService.getCountriesWithId(countryId)).willAnswer( invocation -> { throw new Exception(); });

    // When
    ResponseEntity<Country> actualResponse = countriesController.getCountriesWithId(countryId);

    // Then
    assertThat(output).contains("[FLO] /getCountries/{countryId} is called");
    assertEquals(HttpStatus.CONFLICT,actualResponse.getStatusCode());
    assertNull(actualResponse.getBody());
  }
  // end region scenario for getCountriesWithId //

  // start region scenario for getCountriesWithName //
  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void getCountriesWithNameNoException_WillReturnCorrectResponse(CapturedOutput output) {
    // Given
    String countryName = "Cambodia";
    Country country = new Country(1,"Cambodia","Phnom Penh");
    given(countryService.getCountriesWithName(countryName)).willReturn(country);

    // When
    ResponseEntity<Country> actualResponse = countriesController.getCountriesWithName(countryName);

    // Then
    assertEquals(HttpStatus.OK,actualResponse.getStatusCode());
    assertEquals(country,actualResponse.getBody());
    then(countryService).should(times(1)).getCountriesWithName(countryName);
    assertThat(output).contains("[FLO] /getCountries/countryName is called");
  }

  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void getCountriesWithNameException_WillReturnCorrectResponse(CapturedOutput output) {
    // Given
    EasyRandom easyRandom = new EasyRandom();
    String countryName = easyRandom.nextObject(String.class);
    given(countryService.getCountriesWithName(countryName)).willAnswer( invocation -> { throw new Exception(); });

    // When
    ResponseEntity<Country> actualResponse = countriesController.getCountriesWithName(countryName);

    // Then
    assertThat(output).contains("[FLO] /getCountries/countryName is called");
    assertEquals(HttpStatus.CONFLICT,actualResponse.getStatusCode());
    assertNull(actualResponse.getBody());
  }
  // end region scenario for getCountriesWithName //

  // start region scenario for addCountry //
  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void addCountryNoException_WillReturnCorrectResponse(CapturedOutput output) {
    // Given
    Country country = new Country();
    country.setCountryName("Cambodia");
    country.setCapital("Phnom Penh");
    Country expectedCountry = new Country(1, country.getCountryName(), country.getCapital());
    given(countryService.addCountry(country)).willReturn(expectedCountry);

    // When
    ResponseEntity<Country> actualResponse = countriesController.addCountry(country);

    // Then
    assertEquals(HttpStatus.OK,actualResponse.getStatusCode());
    assertEquals(expectedCountry,actualResponse.getBody());
    then(countryService).should(times(1)).addCountry(country);
    assertThat(output).contains("[FLO] /addCountry is called");
  }

  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void addCountryException_WillReturnCorrectResponse(CapturedOutput output) {
    // Given
    EasyRandom easyRandom = new EasyRandom();
    Country country = easyRandom.nextObject(Country.class);
    given(countryService.addCountry(country)).willAnswer( invocation -> { throw new Exception(); });

    // When
    ResponseEntity<Country> actualResponse = countriesController.addCountry(country);

    // Then
    assertThat(output).contains("[FLO] /addCountry is called");
    assertEquals(HttpStatus.CONFLICT,actualResponse.getStatusCode());
    assertNull(actualResponse.getBody());
  }
  // end region scenario for addCountry //

  // start region scenario for updateCountry //
  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void updateCountryNoException_WillReturnCorrectResponse(CapturedOutput output) {
    // Given
    Country country = new Country(1,"Cambodia","Phnom Penh");
    given(countryService.updateCountry(country)).willReturn(country);

    // When
    ResponseEntity<Country> actualResponse = countriesController.updateCountry(country);

    // Then
    assertEquals(HttpStatus.OK,actualResponse.getStatusCode());
    assertEquals(country,actualResponse.getBody());
    then(countryService).should(times(1)).updateCountry(country);
    assertThat(output).contains("[FLO] /updateCountry is called");
  }

  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void updateCountryException_WillReturnCorrectResponse(CapturedOutput output) {
    // Given
    EasyRandom easyRandom = new EasyRandom();
    Country country = easyRandom.nextObject(Country.class);
    given(countryService.updateCountry(country)).willAnswer( invocation -> { throw new Exception(); });

    // When
    ResponseEntity<Country> actualResponse = countriesController.updateCountry(country);

    // Then
    assertThat(output).contains("[FLO] /updateCountry is called");
    assertEquals(HttpStatus.CONFLICT,actualResponse.getStatusCode());
    assertNull(actualResponse.getBody());
  }
  // end region scenario for updateCountry //

  // start region scenario for deleteCountry //
  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void deleteCountryNoException_WillReturnCorrectResponse(CapturedOutput output) {
    // Given
    EasyRandom easyRandom = new EasyRandom();
    Integer id = easyRandom.nextInt();
    Country country = new Country(id,"US","Washington");
    ResponseMessage responseMessage = new ResponseMessage("Country deleted");
    given(countryService.deleteCountry(id)).willReturn(responseMessage);

    // When
    ResponseEntity<ResponseMessage> actualResponse = countriesController.deleteCountry(id);

    // Then
    assertEquals(HttpStatus.OK,actualResponse.getStatusCode());
    assertEquals(responseMessage,actualResponse.getBody());
    then(countryService).should(times(1)).deleteCountry(id);
    assertThat(output).contains("[FLO] deleteCountry/{countryId} is called");
  }

  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void deleteCountryException_WillReturnCorrectResponse(CapturedOutput output) {
    // Given
    EasyRandom easyRandom = new EasyRandom();
    Integer id = easyRandom.nextInt();
    given(countryService.deleteCountry(id)).willAnswer( invocation -> { throw new Exception(); });

    // When
    ResponseEntity<ResponseMessage> actualResponse = countriesController.deleteCountry(id);

    // Then
    assertThat(output).contains("[FLO] deleteCountry/{countryId} is called");
    assertEquals(HttpStatus.CONFLICT,actualResponse.getStatusCode());
    assertNull(actualResponse.getBody());
  }
  // end region scenario for deleteCountry //
}
