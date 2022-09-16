package com.countries.database.demo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class springboot_country_tbl {
  @Id
  @GeneratedValue
  private int id;

  @Column
  private String countryName;

  @Column
  private String countryCapital;
}
