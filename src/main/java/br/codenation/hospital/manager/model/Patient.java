package br.codenation.hospital.manager.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Document(collection = "pacients")
@TypeAlias("pacient")
public class Patient {

  @Id private String id;

  @NotEmpty @NotBlank private String fullName;
  @NotEmpty @NotBlank private String cpf;
  @NotNull private LocalDate birthDate;
  @NotEmpty @NotBlank private String gender;
  @NotNull private LocalDate hospitalCheckIn;

  public Patient(
      String fullName, String cpf, LocalDate birthDate, String gender, LocalDate hospitalCheckIn) {
    this.fullName = fullName;
    this.cpf = cpf;
    this.birthDate = birthDate;
    this.gender = gender;
    this.hospitalCheckIn = hospitalCheckIn;
  }
}
