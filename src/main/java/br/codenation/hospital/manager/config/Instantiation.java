package br.codenation.hospital.manager.config;

import br.codenation.hospital.manager.model.Hospital;
import br.codenation.hospital.manager.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class Instantiation implements CommandLineRunner {

  @Autowired HospitalRepository hospitalRepository;

  @Override
  public void run(String... args) throws Exception {

    hospitalRepository.deleteAll();

    Hospital h1 =
        new Hospital(
            "Hospital Samaritano",
            "R. Conselheiro Brotero, 1486",
            1000L,
            600L,
            -23.5392135,
            -46.66193084);
    Hospital h2 =
        new Hospital(
            "Hospital São Luiz Unidade Morumbi",
            "Rua Engenheiro Oscar Americano, 840 - Jardim Guedala, São Paulo - SP, 05605-050",
            10057L,
            785L,
            -23.59067495,
            -46.67325182);

    hospitalRepository.insert(Arrays.asList(h1, h2));
  }
}
