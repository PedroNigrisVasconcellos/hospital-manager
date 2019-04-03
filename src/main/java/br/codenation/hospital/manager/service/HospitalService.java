package br.codenation.hospital.manager.service;

import br.codenation.hospital.manager.exception.HospitalException;
import br.codenation.hospital.manager.exception.ResourceNotFoundException;
import br.codenation.hospital.manager.model.Hospital;
import br.codenation.hospital.manager.model.Patient;
import br.codenation.hospital.manager.model.product.SupplyItem;
import br.codenation.hospital.manager.repository.HospitalRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@AllArgsConstructor
public class HospitalService {

  private static final String HOSPITAL_NOT_FOUND = "Hospital %s not found";
  private static final String PATIENT_NOT_FOUND = "Patient %s not found";
  private static final String PRODUCT_NOT_FOUND = "Product %s not found";
  private static final String NO_PATIENTS_HAS_BEEN_FOUND = "No patients has been found";
  private static final String PATIENT_ALREADY_CHECKED_IN = "Patient %s already checked in";
  private static final String PATIENT_IS_NOT_CHECKED_IN = "Patient %s is not checked in";
  private static final String NO_AVAILABLE_BEDS = "No available beds in the hospital %s";
  private static final String INVALID_NUMBER_OF_BEDS = "Invalid number of beds";

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

  public List<Hospital> loadAllHospitals() {
    return hospitalRepository.findAll();
  }

  public Patient checkinPatient(Patient patient, String hospitalId) {
    Hospital hospital = loadHospital(hospitalId);

    if(!hospital.decrementAvailableBeds()) throw new HospitalException(String.format(NO_AVAILABLE_BEDS,hospitalId));

    patient.setHospitalCheckIn(LocalDate.now());

    patientService.update(patient);

    if(hospital.addNewPatient(patient) == null) throw new HospitalException(String.format(PATIENT_ALREADY_CHECKED_IN, patient.getId()));

    save(hospital);

    return patient;
  }

  public Patient checkoutPatient(String patientId, String hospitalId) {
    Hospital hospital = loadHospital(hospitalId);

    if(!hospital.incrementAvailableBeds()) throw new HospitalException(String.format(INVALID_NUMBER_OF_BEDS));

    Optional<Patient> patientOptional = hospital.removePatient(patientId);

    if(patientOptional.isEmpty()) throw new HospitalException(String.format(PATIENT_IS_NOT_CHECKED_IN,patientId));

    Patient patient = patientOptional.get();

    patient.setHospitalCheckIn(null);
    patientService.update(patient);
    save(hospital);

    return patient;
  }

  public Patient findPatient(String patientId,String hospitalId){
    Hospital hospital = loadHospital(hospitalId);

    if(hospital.getPatients().isEmpty()) throw new ResourceNotFoundException(String.format(PATIENT_NOT_FOUND, patientId));

    Patient patient = hospital.getPatients().get(patientId);

    if(patient != null) return patient;
    else throw new ResourceNotFoundException(String.format(PATIENT_NOT_FOUND, patientId));
  }

  public Map<String, Patient> findPatients(String hospitalId){
    Hospital hospital = loadHospital(hospitalId);

    if(hospital.getPatients().isEmpty()) throw new ResourceNotFoundException(String.format(NO_PATIENTS_HAS_BEEN_FOUND));

    return hospital.getPatients();
  }

  public SupplyItem insertProduct(String hospitalId, SupplyItem product){
    Hospital hospital = loadHospital(hospitalId);

    hospital.addItemStock(product);

    save(hospital);

    return product;
  }

  public SupplyItem findProduct(String hospitalId ,String productId){
      Hospital hospital = loadHospital(hospitalId);

      SupplyItem supplyItem = hospital.getStock().get(productId);

      if(supplyItem != null)
        return supplyItem;
      else
        throw new ResourceNotFoundException(String.format(PRODUCT_NOT_FOUND,productId));

  }

}
