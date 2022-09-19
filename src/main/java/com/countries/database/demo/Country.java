package com.countries.database.demo;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="springboot_country_tbl")
@NoArgsConstructor
public class Country {
  @Id
  @Column(name="id")
  @GeneratedValue
  private int id;

  @Column(name="countryname")
  private String countryName;

  @Column(name="countrycapital")
  private String capital;

  public Country(int id, String countryName, String capital) {
    this.id = id;
    this.countryName = countryName;
    this.capital = capital;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCountryName() {
    return countryName;
  }

  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  public String getCapital() {
    return capital;
  }

  public void setCapital(String capital) {
    this.capital = capital;
  }
}
