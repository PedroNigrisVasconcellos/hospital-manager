package br.codenation.hospital.manager.configuration;

import br.codenation.hospital.manager.model.Hospital;
import br.codenation.hospital.manager.model.Patient;
import br.codenation.hospital.manager.repository.HospitalRepository;
import br.codenation.hospital.manager.repository.PatientRepository;
import br.codenation.hospital.manager.service.HospitalService;
import br.codenation.hospital.manager.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;

@Configuration
@AllArgsConstructor
public class Instantiation implements CommandLineRunner {

  private final HospitalRepository hospitalRepository;
  private final PatientRepository patientRepository;

  private final HospitalService hospitalService;
  private final PatientService patientService;

  @Override
  public void run(String... args) throws Exception {

    hospitalRepository.deleteAll();
    patientRepository.deleteAll();

     Hospital samaritanHospital =
        new Hospital(
            "Hospital Samaritano",
            "R. Conselheiro Brotero, 1486",
            1000L,
            600L,
            -23.5392135,
            -46.66193084);

     Hospital morumbiHospital =
        new Hospital(
            "Hospital São Luiz Unidade Morumbi",
            "Rua Engenheiro Oscar Americano, 840 - Jardim Guedala, São Paulo - SP, 05605-050",
            10057L,
            785L,
            -23.59067495,
            -46.67325182);

     Patient pacienteComCheckin =
            new Patient("Pedro Silva",
                    "0123456789",
                    LocalDate.of(1992,2,14),
                    "male",
                    LocalDate.now(),
                    31.7654,
                    52.3376);

     Patient pacienteSemCheckin =
            new Patient("Lucas Santos",
                    "9876543210",
                    LocalDate.of(1994,5,23),
                    "male",
                    null,
                    23.5505,
                    46.6333);


    patientRepository.saveAll(Arrays.asList(pacienteComCheckin,pacienteSemCheckin));
    hospitalRepository.saveAll(Arrays.asList(samaritanHospital, morumbiHospital));

    hospitalService.checkinPatient(pacienteSemCheckin.getId(),samaritanHospital.getId());

    //System.out.println(hospitalService.findPatient(pacienteSemCheckin.getId(),samaritanHospital.getId()));

  }
}
