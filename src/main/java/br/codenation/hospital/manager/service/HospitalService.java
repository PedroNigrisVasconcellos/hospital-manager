package br.codenation.hospital.manager.service;

import br.codenation.hospital.manager.exception.HospitalException;
import br.codenation.hospital.manager.exception.ResourceNotFoundException;
import br.codenation.hospital.manager.model.Hospital;
import br.codenation.hospital.manager.model.Patient;
import br.codenation.hospital.manager.repository.HospitalRepository;
import br.codenation.hospital.manager.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HospitalService {

  private static final String HOSPITAL_NOT_FOUND = "Hospital %s not found";
  private static final String PATIENT_NOT_FOUND = "Patient %s not found";
  private static final String PATIENT_ALREADY_CHECKED_IN = "Patient %s already checked in";

  @Autowired
  private final HospitalRepository hospitalRepository;
  @Autowired
  private final PatientService patientService;

  public Hospital save(Hospital hospital) {
    return hospitalRepository.save(hospital);
  }

  public Hospital loadHospital(String hospitalId) {
    return hospitalRepository
        .findById(hospitalId)
        .orElseThrow(
            () -> new ResourceNotFoundException(String.format(HOSPITAL_NOT_FOUND, hospitalId)));
  }

  public Patient checkinPatient(Patient patient, String hospitalId) {
    Hospital hospital = loadHospital(hospitalId);

    patient.setHospitalCheckIn(LocalDate.now());

    patientService.update(patient);

    if(hospital.addNewPatient(patient) == null) throw new HospitalException(PATIENT_ALREADY_CHECKED_IN);

    updatePatientsList(hospital);

    return patient;
  }

  public Patient checkinPatient(String patientId, String hospitalId) {
    Hospital hospital = loadHospital(hospitalId);
    Patient patient = patientService.loadPatient(patientId);

    patient.setHospitalCheckIn(LocalDate.now());

    if(hospital.addNewPatient(patient) == null) throw new HospitalException(PATIENT_ALREADY_CHECKED_IN);

    updatePatientsList(hospital);

    return patient;
  }

  public Patient findPatient(String patientId,String hospitalId){
    
    Hospital hospital = loadHospital(hospitalId);

    if(hospital.patientsMapIsNullOrEmpty()) throw new ResourceNotFoundException(String.format(PATIENT_NOT_FOUND, patientId));

    Patient patient = hospital.getPatients().get(patientId);

    if(patient != null) return patient;
    else throw new ResourceNotFoundException(String.format(PATIENT_NOT_FOUND, patientId));
  }

  public Hospital updatePatientsList(Hospital hospitalUpdated){

    Optional<Hospital> hospital = hospitalRepository.findById(hospitalUpdated.getId());

    if(hospital.isPresent()){
      Hospital updated = hospital.get();
      updated.setPatients(hospitalUpdated.getPatients());
      save(updated);
      return updated;
    }else
      throw new ResourceNotFoundException(String.format(HOSPITAL_NOT_FOUND,hospitalUpdated.getId()));

  }

}
