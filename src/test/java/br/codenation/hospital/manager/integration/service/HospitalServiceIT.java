package br.codenation.hospital.manager.integration.service;

import br.codenation.hospital.manager.exception.ResourceNotFoundException;
import br.codenation.hospital.manager.model.Hospital;
import br.codenation.hospital.manager.model.Patient;
import br.codenation.hospital.manager.repository.HospitalRepository;
import br.codenation.hospital.manager.repository.PatientRepository;
import br.codenation.hospital.manager.service.HospitalService;
import br.codenation.hospital.manager.service.PatientService;
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
  private PatientService patientService;

  @BeforeEach
  public void setUp() {
    hospitalService = new HospitalService(hospitalRepository,patientService);
    patientService = new PatientService(patientRepository);
  }

  @Test
  public void saveHospital() {
    final Hospital hospital = hospitalService.save(TestHelper.newHospital());

    Hospital hp = hospitalService.loadHospital(hospital.getId());

    assertEquals(hospital, hp);
  }

  @Test
  public void hospitalNotFound() {
    assertThrows(
        ResourceNotFoundException.class, () -> hospitalService.loadHospital("some random value"));
  }

  @Test
  public void savePatient() {
    final Patient patient = patientService.save(TestHelper.newPatient());

    assertEquals(patient, patientService.loadPatient(patient.getId()));
  }

  @Test
  public void patientNotFound() {
    assertThrows(
        ResourceNotFoundException.class, () -> patientService.loadPatient("some random value"));
  }
}
