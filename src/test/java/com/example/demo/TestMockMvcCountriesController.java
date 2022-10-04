package com.example.demo;

import com.countries.database.demo.CountriesController;
import com.countries.database.demo.Country;
import com.countries.database.demo.CountryService;
import com.countries.database.demo.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.CannotCreateTransactionException;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes={CountriesController.class})
public class TestMockMvcCountriesController {
  @Autowired
  private MockMvc mockMvc;
  @Mock
  private CountryService countryService;
  @Mock
  private Log log;
  @InjectMocks
  private CountriesController countriesController;

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(countriesController).build();
  }

  // start region scenario for getCountries //
  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void getCountriesNoException_WillReturnCorrectResponse(CapturedOutput output) throws Exception {
    // Given
    List<Country> countryList = new ArrayList<Country>();
    countryList.add(new Country(1,"India","New Delhi"));
    countryList.add(new Country(2,"Myanmar","Naypyidaw"));
    ObjectMapper mapper = new ObjectMapper();
    String countryListString = mapper.writeValueAsString(countryList);
    given(countryService.getCountries()).willReturn(countryList);

    // When
    MvcResult actualResponse = this.mockMvc.perform(get("/getCountries"))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();

    // Then
    assertEquals(HttpStatus.OK.value(),actualResponse.getResponse().getStatus());
    assertEquals(countryListString,actualResponse.getResponse().getContentAsString());
    then(countryService).should(times(1)).getCountries();
    assertThat(output).contains("[FLO] /getCountries is called");
  }

  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void getCountriesDbExistTblNotExist_WillReturnCorrectResponse(CapturedOutput output) throws Exception {
    // Given
    given(countryService.getCountries()).willAnswer( invocation -> { throw new InvalidDataAccessResourceUsageException(""); });

    // When
    MvcResult actualResponse = this.mockMvc.perform(get("/getCountries"))
        .andExpect(status().isGone())
        .andExpect(status().is4xxClientError())
        .andDo(print())
        .andReturn();

    // Then
    assertThat(output).contains("[FLO] /getCountries is called");
    assertEquals("",actualResponse.getResponse().getContentAsString());
  }

  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void getCountriesDbDeletedWhileSpringRunning_WillReturnCorrectResponse(CapturedOutput output) throws Exception {
    // Given
    given(countryService.getCountries()).willAnswer( invocation -> { throw new CannotCreateTransactionException(""); });

    // When
    MvcResult actualResponse = this.mockMvc.perform(get("/getCountries"))
        .andExpect(status().isExpectationFailed())
        .andExpect(status().is4xxClientError())
        .andDo(print())
        .andReturn();

    // Then
    assertThat(output).contains("[FLO] /getCountries is called");
    assertEquals("",actualResponse.getResponse().getContentAsString());
  }

  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void getCountriesTableEmpty_WillReturnCorrectResponse(CapturedOutput output) throws Exception {
    // Given
    List<Country> countryList = new ArrayList<Country>();
    given(countryService.getCountries()).willReturn(countryList);

    // When
    MvcResult actualResponse = this.mockMvc.perform(get("/getCountries"))
        .andExpect(status().isNoContent())
        .andExpect(status().is2xxSuccessful())
        .andDo(print())
        .andReturn();

    // Then
    assertThat(output).contains("[FLO] /getCountries is called");
    assertEquals("",actualResponse.getResponse().getContentAsString());
  }

  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void getCountriesOtherException_WillReturnCorrectResponse(CapturedOutput output) throws Exception {
    // Given
    given(countryService.getCountries()).willAnswer( invocation -> { throw new Exception(); });

    // When
    //ResponseEntity<List<Country>> actualResponse = countriesController.getCountries();
    MvcResult actualResponse = this.mockMvc.perform(get("/getCountries"))
        .andExpect(status().isIAmATeapot())
        .andExpect(status().is4xxClientError())
        .andDo(print())
        .andReturn();

    // Then
    assertThat(output).contains("[FLO] /getCountries is called");
    assertEquals("",actualResponse.getResponse().getContentAsString());
  }
  // end region scenario for getCountries //

  // start region scenario for getCountriesWithId //
  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void getCountriesWithIdNoException_WillReturnCorrectResponse(CapturedOutput output) throws Exception {
    // Given
    Integer countryId = 1;
    Country country = new Country(1,"Laos","Vientiane");
    given(countryService.getCountriesWithId(countryId)).willReturn(country);
    ObjectMapper mapper = new ObjectMapper();
    String countryString = mapper.writeValueAsString(country);

    // When
    MvcResult actualResponse = this.mockMvc.perform(get("/getCountries/{countryId}",countryId))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();

    // Then
    assertEquals(HttpStatus.OK.value(),actualResponse.getResponse().getStatus());
    assertEquals(countryString,actualResponse.getResponse().getContentAsString());
    then(countryService).should(times(1)).getCountriesWithId(countryId);
    assertThat(output).contains("[FLO] /getCountries/{countryId} is called");
  }

  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void getCountriesWithIdException_WillReturnCorrectResponse(CapturedOutput output) throws Exception {
    // Given
    EasyRandom easyRandom = new EasyRandom();
    Integer countryId = easyRandom.nextInt();
    given(countryService.getCountriesWithId(countryId)).willAnswer( invocation -> { throw new Exception(); });

    // When
    //ResponseEntity<Country> actualResponse = countriesController.getCountriesWithId(countryId);
    MvcResult actualResponse = this.mockMvc.perform(get("/getCountries/{countryId}",countryId))
        .andExpect(status().isConflict())
        .andExpect(status().is4xxClientError())
        .andDo(print())
        .andReturn();

    // Then
    assertThat(output).contains("[FLO] /getCountries/{countryId} is called");
    assertEquals("",actualResponse.getResponse().getContentAsString());
  }
  // end region scenario for getCountriesWithId //

  // start region scenario for getCountriesWithName //
  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void getCountriesWithNameNoException_WillReturnCorrectResponse(CapturedOutput output) throws Exception {
    // Given
    String countryName = "Cambodia";
    Country country = new Country(1,"Cambodia","Phnom Penh");
    given(countryService.getCountriesWithName(countryName)).willReturn(country);
    ObjectMapper mapper = new ObjectMapper();
    String countryString = mapper.writeValueAsString(country);

    // When
    MvcResult actualResponse = this.mockMvc.perform(get("/getCountries/countryName").param("name",countryName))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();

    // Then
    assertEquals(HttpStatus.OK.value(),actualResponse.getResponse().getStatus());
    assertEquals(countryString,actualResponse.getResponse().getContentAsString());
    then(countryService).should(times(1)).getCountriesWithName(countryName);
    assertThat(output).contains("[FLO] /getCountries/countryName is called");
  }

  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void getCountriesWithNameException_WillReturnCorrectResponse(CapturedOutput output) throws Exception {
    // Given
    EasyRandom easyRandom = new EasyRandom();
    String countryName = easyRandom.nextObject(String.class);
    given(countryService.getCountriesWithName(countryName)).willAnswer( invocation -> { throw new Exception(); });

    // When
    MvcResult actualResponse = this.mockMvc.perform(get("/getCountries/countryName").param("name",countryName))
        .andExpect(status().isConflict())
        .andExpect(status().is4xxClientError())
        .andDo(print())
        .andReturn();

    // Then
    assertThat(output).contains("[FLO] /getCountries/countryName is called");
    assertEquals("",actualResponse.getResponse().getContentAsString());
  }
  // end region scenario for getCountriesWithName //

  // start region scenario for addCountry //
  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void addCountryNoException_WillReturnCorrectResponse(CapturedOutput output) throws Exception {
    // Given
    Country country = new Country();
    country.setCountryName("Cambodia");
    country.setCapital("Phnom Penh");
    Country expectedCountry = new Country(1, country.getCountryName(), country.getCapital());
    ObjectMapper mapper = new ObjectMapper();
    String expectedCountryString = mapper.writeValueAsString(expectedCountry);
    String countryString = mapper.writeValueAsString(country);
    given(countryService.addCountry(country)).willReturn(expectedCountry);

    // When
    MvcResult actualResponse = this.mockMvc.perform(post("/addCountry")
            .content(countryString)
            .contentType("application/json;charset=UTF-8"))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();

    // Then
    assertEquals(HttpStatus.OK.value(),actualResponse.getResponse().getStatus());
    assertEquals(expectedCountryString,actualResponse.getResponse().getContentAsString());
    then(countryService).should(times(1)).addCountry(country);
    assertThat(output).contains("[FLO] /addCountry is called");
  }

  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void addCountryException_WillReturnCorrectResponse(CapturedOutput output) throws Exception {
    // Given
    EasyRandom easyRandom = new EasyRandom();
    Country country = easyRandom.nextObject(Country.class);
    given(countryService.addCountry(country)).willAnswer( invocation -> { throw new Exception(); });
    ObjectMapper mapper = new ObjectMapper();
    String countryString = mapper.writeValueAsString(country);

    // When
    MvcResult actualResponse = this.mockMvc.perform(post("/addCountry")
            .content(countryString)
            .contentType("application/json;charset=UTF-8"))
        .andExpect(status().isConflict())
        .andExpect(status().is4xxClientError())
        .andDo(print())
        .andReturn();

    // Then
    assertThat(output).contains("[FLO] /addCountry is called");
    assertEquals("",actualResponse.getResponse().getContentAsString());
  }
  // end region scenario for addCountry //

  // start region scenario for updateCountry //
  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void updateCountryNoException_WillReturnCorrectResponse(CapturedOutput output) throws Exception {
    // Given
    Country country = new Country(1,"Cambodia","Phnom Penh");
    given(countryService.updateCountry(country)).willReturn(country);
    ObjectMapper mapper = new ObjectMapper();
    String countryString = mapper.writeValueAsString(country);

    // When
    MvcResult actualResponse = this.mockMvc.perform(put("/updateCountry")
            .content(countryString)
            .contentType("application/json;charset=UTF-8"))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();

    // Then
    assertEquals(HttpStatus.OK.value(),actualResponse.getResponse().getStatus());
    assertEquals(countryString,actualResponse.getResponse().getContentAsString());
    then(countryService).should(times(1)).updateCountry(country);
    assertThat(output).contains("[FLO] /updateCountry is called");
  }

  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void updateCountryException_WillReturnCorrectResponse(CapturedOutput output) throws Exception {
    // Given
    EasyRandom easyRandom = new EasyRandom();
    Country country = easyRandom.nextObject(Country.class);
    given(countryService.updateCountry(country)).willAnswer( invocation -> { throw new Exception(); });
    ObjectMapper mapper = new ObjectMapper();
    String countryString = mapper.writeValueAsString(country);

    // When
    MvcResult actualResponse = this.mockMvc.perform(put("/updateCountry")
            .content(countryString)
            .contentType("application/json;charset=UTF-8"))
        .andExpect(status().isConflict())
        .andExpect(status().is4xxClientError())
        .andDo(print())
        .andReturn();

    // Then
    assertThat(output).contains("[FLO] /updateCountry is called");
    assertEquals("",actualResponse.getResponse().getContentAsString());
  }
  // end region scenario for updateCountry //

  // start region scenario for deleteCountry //
  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void deleteCountryNoException_WillReturnCorrectResponse(CapturedOutput output) throws Exception {
    // Given
    EasyRandom easyRandom = new EasyRandom();
    Integer id = easyRandom.nextInt();
    Country country = new Country(id,"US","Washington");
    ResponseMessage responseMessage = new ResponseMessage("Country deleted");
    ObjectMapper mapper = new ObjectMapper();
    String responseMessageString = mapper.writeValueAsString(responseMessage);
    given(countryService.deleteCountry(id)).willReturn(responseMessage);

    // When
    MvcResult actualResponse = this.mockMvc.perform(delete("/deleteCountry/{countryId}",id))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();

    // Then
    assertEquals(HttpStatus.OK.value(),actualResponse.getResponse().getStatus());
    assertEquals(responseMessageString,actualResponse.getResponse().getContentAsString());
    then(countryService).should(times(1)).deleteCountry(id);
    assertThat(output).contains("[FLO] deleteCountry/{countryId} is called");
  }

  @Test
  @ExtendWith(OutputCaptureExtension.class)
  public void deleteCountryException_WillReturnCorrectResponse(CapturedOutput output) throws Exception {
    // Given
    EasyRandom easyRandom = new EasyRandom();
    Integer id = easyRandom.nextInt();
    given(countryService.deleteCountry(id)).willAnswer( invocation -> { throw new Exception(); });

    // When
    MvcResult actualResponse = this.mockMvc.perform(delete("/deleteCountry/{countryId}",id))
        .andExpect(status().isConflict())
        .andExpect(status().is4xxClientError())
        .andDo(print())
        .andReturn();

    // Then
    assertThat(output).contains("[FLO] deleteCountry/{countryId} is called");
    assertEquals("",actualResponse.getResponse().getContentAsString());
  }
  // end region scenario for deleteCountry //
}
