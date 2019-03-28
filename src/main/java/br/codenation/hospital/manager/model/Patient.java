package br.codenation.hospital.manager.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Document(collection = "patients")
@TypeAlias("patient")
public class Patient {
  @Id private String id;

  @NotEmpty @NotBlank private String fullName;
  @NotEmpty @NotBlank @CPF private String cpf;
  @NotNull private LocalDate birthDate;
  private Gender gender;
  private LocalDate hospitalCheckIn;
  @NotNull private Double latitude;
  @NotNull private Double longitude;

  public Patient(
      String fullName,
      String cpf,
      LocalDate birthDate,
      @NotEmpty @NotBlank String gender,
      LocalDate hospitalCheckIn,
      Double latitude,
      Double longitude) {
    this.fullName = fullName;
    this.cpf = cpf;
    this.birthDate = birthDate;
    this.gender = Gender.fromValue(gender);
    this.hospitalCheckIn = hospitalCheckIn;
    this.latitude = latitude;
    this.longitude = longitude;
  }
}
