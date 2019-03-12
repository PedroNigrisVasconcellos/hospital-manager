package br.codenation.hospital.manager.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Pacient {

  private final String id;
  private final String fullName;
  private final String cpf;
  private final LocalDate birthDate;
  private final String gender;
  private final LocalDate hospitalCheckIn;
}
