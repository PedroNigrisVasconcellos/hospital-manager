package br.codenation.hospital.manager.service;

import br.codenation.hospital.manager.exception.ResourceNotFoundException;
import br.codenation.hospital.manager.model.Hospital;
import br.codenation.hospital.manager.model.Patient;
import br.codenation.hospital.manager.repository.HospitalRepository;
import br.codenation.hospital.manager.repository.PatientRepository;
import helper.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HospitalServiceTest {

  @Mock private HospitalRepository hospitalRepository;
  @Mock private PatientRepository patientRepository;

  @InjectMocks private HospitalService hospitalService;

  @Test
  public void saveHospital() {

    final Hospital hospital = TestHelper.newHospital();

    when(hospitalRepository.save(eq(hospital))).thenReturn(hospital);
    when(hospitalRepository.findById(eq(hospital.getId()))).thenReturn(Optional.of(hospital));

    final Hospital savedHospital = hospitalService.save(hospital);

    assertEquals(savedHospital, hospitalService.loadHospital(savedHospital.getId()));
  }

  @Test
  public void hospitalNotFound() {

    when(hospitalRepository.findById(anyString())).thenReturn(Optional.empty());

    assertThrows(
        ResourceNotFoundException.class, () -> hospitalService.loadHospital("some random value"));
  }

  @Test
  public void savePatient() {

    final Patient patient = TestHelper.newPatient();

    when(patientRepository.save(eq(patient))).thenReturn(patient);
    when(patientRepository.findById(eq(patient.getId()))).thenReturn(Optional.of(patient));

    final Patient savedPatient = hospitalService.savePatient(patient);

    assertEquals(savedPatient, hospitalService.loadPatient(savedPatient.getId()));
  }

  @Test
  public void patientNotFound() {

    when(patientRepository.findById(anyString())).thenReturn(Optional.empty());

    assertThrows(
        ResourceNotFoundException.class, () -> hospitalService.loadPatient("some random value"));
  }
}
