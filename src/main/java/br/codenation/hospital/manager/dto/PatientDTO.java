package br.codenation.hospital.manager.dto;

import br.codenation.hospital.manager.model.Gender;
import br.codenation.hospital.manager.model.Patient;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PatientDTO {

    private String fullName;
    private String cpf;
    private LocalDate birthDate;
    private Gender gender;
    private LocalDate hospitalCheckIn;

    public PatientDTO(Patient patient){
        this.fullName = patient.getFullName();
        this.cpf = patient.getCpf();
        this.birthDate = patient.getBirthDate();
        this.gender = patient.getGender();
        this.hospitalCheckIn = patient.getHospitalCheckIn();
    }

}
