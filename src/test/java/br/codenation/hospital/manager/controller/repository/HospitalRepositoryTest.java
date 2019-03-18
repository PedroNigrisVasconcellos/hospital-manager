package br.codenation.hospital.manager.controller.repository;

import br.codenation.hospital.manager.model.Hospital;
import br.codenation.hospital.manager.repository.HospitalRepository;
import helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
public class HospitalRepositoryTest {

  @Autowired private HospitalRepository hospitalRepository;

  @Test
  public void saveHospital() {
    final Hospital hospital = hospitalRepository.save(TestHelper.newHospital()).block();

    assertEquals(hospital, hospitalRepository.findById(hospital.getId()).block());
  }
}
