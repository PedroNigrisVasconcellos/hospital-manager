package br.codenation.hospital.manager.integration.service;

import br.codenation.hospital.manager.exception.ResourceNotFoundException;
import br.codenation.hospital.manager.model.Hospital;
import br.codenation.hospital.manager.model.Patient;
import br.codenation.hospital.manager.repository.HospitalRepository;
import br.codenation.hospital.manager.repository.PatientRepository;
import br.codenation.hospital.manager.service.HospitalService;
import helper.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataMongoTest
public class HospitalServiceIT {

  @Autowired private HospitalRepository hospitalRepository;
  @Autowired private PatientRepository patientRepository;

  private HospitalService hospitalService;

  @BeforeEach
  public void setUp() {
    hospitalService = new HospitalService(hospitalRepository, patientRepository);
  }

  @Test
  public void saveHospital() {
    final Hospital hospital = hospitalService.save(TestHelper.newHospital());

    assertEquals(hospital, hospitalService.loadHospital(hospital.getId()));
  }

  @Test
  public void hospitalNotFound() {
    assertThrows(
        ResourceNotFoundException.class, () -> hospitalService.loadHospital("some random value"));
  }

  @Test
  public void savePatient() {
    final Patient patient = hospitalService.savePatient(TestHelper.newPatient());

    assertEquals(patient, hospitalService.loadPatient(patient.getId()));
  }

  @Test
  public void patientNotFound() {
    assertThrows(
        ResourceNotFoundException.class, () -> hospitalService.loadPatient("some random value"));
  }
}
