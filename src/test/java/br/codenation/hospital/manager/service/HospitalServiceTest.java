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

import java.time.LocalDate;
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
  public void shouldCheckinPatientWithPatientObject(){

    Patient patient = TestHelper.newPatient();
    Hospital hospital = TestHelper.newHospital();


    when(hospitalService.save(hospital)).thenReturn(hospital);

    System.out.println(hospital.getId());

    when(hospitalService.checkinPatient(patient, hospital.getId())).thenReturn(patient);

    assertEquals(LocalDate.now(), patient.getHospitalCheckIn());

  }

}
