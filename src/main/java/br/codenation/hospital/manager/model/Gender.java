package br.codenation.hospital.manager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum Gender {
  MALE("male"),
  FEMALE("female");

  private String genderName;
}
