package br.codenation.hospital.manager.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Patient {
    @Id
    private String id;
    private String fullName;
    private String cpf;
    private LocalDate birthdayDate;
    private Gender gender;
    private LocalDate hospitalAdmissionDate;
}
