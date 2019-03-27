package br.codenation.hospital.manager.service;

import br.codenation.hospital.manager.exception.ResourceNotFoundException;
import br.codenation.hospital.manager.model.Hospital;
import br.codenation.hospital.manager.model.Patient;
import br.codenation.hospital.manager.repository.HospitalRepository;
import br.codenation.hospital.manager.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class HospitalService {

  private static final String HOSPITAL_NOT_FOUND = "Hospital %s not found";
  private static final String PATIENT_NOT_FOUND = "Patient %s not found";

  private final HospitalRepository hospitalRepository;
  private final PatientRepository patientRepository;

  public Hospital save(Hospital hospital) {
    return hospitalRepository.save(hospital);
  }

  public Hospital loadHospital(String hospitalId) {
    return hospitalRepository
        .findById(hospitalId)
        .orElseThrow(
            () -> new ResourceNotFoundException(String.format(HOSPITAL_NOT_FOUND, hospitalId)));
  }

  public Patient save(Patient patient) {
    return patientRepository.save(patient);
  }

  public Patient save(Patient patient, String hospitalId) {
    Hospital hospital = loadHospital(hospitalId);

    patient.setHospitalCheckIn(LocalDate.now());
    patientRepository.save(patient);

    hospital.addNewPatient(patient);
    hospitalRepository.save(hospital);

    return patient;
  }

  public Patient save(String patientId, String hospitalId) {
    Hospital hospital = loadHospital(hospitalId);
    Patient patient = loadPatient(patientId);

    hospital.addNewPatient(patient);
    patient.setHospitalCheckIn(LocalDate.now());

    hospitalRepository.save(hospital);
    return patientRepository.save(patient);
  }

  public Patient loadPatient(String patientId) {
    return patientRepository
        .findById(patientId)
        .orElseThrow(
            () -> new ResourceNotFoundException(String.format(PATIENT_NOT_FOUND, patientId)));
  }
}
