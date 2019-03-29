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

     Patient paciente1 =
            new Patient("Pedro Silva",
                    "0123456789",
                    LocalDate.of(1992,2,14),
                    "male",
                    null,
                    31.7654,
                    52.3376);

     Patient paciente2 =
            new Patient("Lucas Santos",
                    "9876543210",
                    LocalDate.of(1994,5,23),
                    "male",
                    null,
                    23.5505,
                    46.6333);

     Patient paciente3 =
              new Patient("Regina Souza",
                      "555555555",
                      LocalDate.of(1978,5,21),
                      "female",
                      null,
                      32.0395,
                      52.1014);

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

    samaritanHospital.addItensStock(Arrays.asList(seringa,gesso,gaze,sangueAB));
    morumbiHospital.addItemStock(sangueOPos);

    patientRepository.saveAll(Arrays.asList(paciente1,paciente2,paciente3));
    hospitalRepository.saveAll(Arrays.asList(samaritanHospital, morumbiHospital));

    hospitalService.checkinPatient(patientService.loadPatient(paciente1.getId()),samaritanHospital.getId());

     // Banco Inicializado com 2 Hospitais, 3 pacientes e 5 Itens, sendo 3 produtos e 2 bancos de sangue//
     // No samaritanHospital, são adicinados: seringa, gerro, gaze e sangueAB, além de fazer o checkin do paciente1.
     // No morumbiHospital é adicionado apenas sangue O positivo.

  }
}
