package br.codenation.hospital.manager.configuration;

import br.codenation.hospital.manager.model.Hospital;
import br.codenation.hospital.manager.model.Patient;
import br.codenation.hospital.manager.model.product.SupplyItem;
import br.codenation.hospital.manager.model.product.SupplyType;
import br.codenation.hospital.manager.repository.HospitalRepository;
import br.codenation.hospital.manager.repository.PatientRepository;
import br.codenation.hospital.manager.service.HospitalService;
import br.codenation.hospital.manager.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

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

     Hospital realHospital =
        new Hospital(
            "Real Hospital Português de Beneficência",
            " Av. Gov. Agamenon Magalhães, 4760 - Paissandu, Recife - PE, 52010-040",
            10L,
            5L,
            -8.0646,
            -34.8986);

     Hospital morumbiHospital =
        new Hospital(
            "Hospital São Luiz Unidade Morumbi",
            "Rua Engenheiro Oscar Americano, 840 - Jardim Guedala, São Paulo - SP, 05605-050",
            5L,
            1L,
            -23.59067495,
            -46.67325182);

      Hospital santaCasaHospital =
              new Hospital(
                      "Santa Casa de Misericordia",
                      "Av. Independência, 75 - Independência, Porto Alegre - RS, 90035-072",
                      5L,
                      3L,
                      -30.0309,
                      -51.2214);

     Patient paciente1 =
            new Patient("Pedro Silva",
                    "0123456789",
                    LocalDate.of(1992,2,14),
                    "male",
                    null,
                    -31.7654,
                    -52.3376);

     Patient paciente2 =
            new Patient("Lucas Santos",
                    "9876543210",
                    LocalDate.of(1994,5,23),
                    "male",
                    null,
                    -23.5505,
                    -46.6333);

     Patient paciente3 =
              new Patient("Regina Souza",
                      "555555555",
                      LocalDate.of(1978,5,21),
                      "female",
                      null,
                      -31.7654,
                      -52.3376);

      SupplyItem seringa =
              new SupplyItem("1","Seringa",Long.valueOf(5000), SupplyType.PRODUCT);
      SupplyItem gesso =
              new SupplyItem("2","Gesso",Long.valueOf(250), SupplyType.PRODUCT);
      SupplyItem gaze =
              new SupplyItem("3","Gaze",Long.valueOf(45000), SupplyType.PRODUCT);
      SupplyItem sangueAB =
              new SupplyItem("4","Sangue AB",Long.valueOf(6), SupplyType.BLOOD_BANK);
      SupplyItem sangueOPos =
              new SupplyItem("5","Sangue O Positivo",Long.valueOf(25), SupplyType.BLOOD_BANK);

    patientRepository.saveAll(Arrays.asList(paciente1,paciente2,paciente3));
    hospitalRepository.saveAll(Arrays.asList(realHospital, morumbiHospital, santaCasaHospital));

    hospitalService.insertProduct(morumbiHospital.getId(),seringa);
    hospitalService.insertProduct(morumbiHospital.getId(),gesso);
    hospitalService.insertProduct(morumbiHospital.getId(),gaze);
    hospitalService.insertProduct(morumbiHospital.getId(),sangueOPos);

    hospitalService.insertProduct(santaCasaHospital.getId(),sangueAB);

    //hospitalService.checkinPatient(patientService.loadPatient(paciente1.getId()),santaCasaHospital.getId());

     // Banco Inicializado com 2 Hospitais, 3 pacientes e 5 Itens, sendo 3 produtos e 2 bancos de sangue//
     // No samaritanHospital, são adicinados: seringa, gerro, gaze e sangueAB, além de fazer o checkin do paciente1.
     // No morumbiHospital é adicionado apenas sangue O positivo.

  }
}
