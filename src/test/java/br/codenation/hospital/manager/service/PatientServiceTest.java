package br.codenation.hospital.manager.service;

import br.codenation.hospital.manager.exception.ResourceNotFoundException;
import br.codenation.hospital.manager.model.Patient;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;


    @Test
    public void savePatient() {

        final Patient patient = TestHelper.newPatient();

        when(patientRepository.save(eq(patient))).thenReturn(patient);
        when(patientRepository.findById(eq(patient.getId()))).thenReturn(Optional.of(patient));

        final Patient savedPatient = patientService.save(patient);

        assertEquals(savedPatient, patientService.loadPatient(savedPatient.getId()));
    }

    @Test
    public void patientNotFound() {

        when(patientRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class, () -> patientService.loadPatient("some random value"));
    }
}
