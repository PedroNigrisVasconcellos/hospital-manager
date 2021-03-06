package br.codenation.hospital.manager.service;

import br.codenation.hospital.manager.exception.HospitalException;
import br.codenation.hospital.manager.exception.ResourceNotFoundException;
import br.codenation.hospital.manager.model.Hospital;
import br.codenation.hospital.manager.model.Patient;
import br.codenation.hospital.manager.model.product.SupplyItem;
import br.codenation.hospital.manager.repository.HospitalRepository;
import br.codenation.hospital.manager.util.Coordinates;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.nio.charset.CoderResult;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@AllArgsConstructor
public class HospitalService {

  private static final String HOSPITAL_NOT_FOUND = "Hospital %s not found";
  private static final String NO_HOSPITALS_AVAILABLE = "No hospitals available";
  private static final String PATIENT_NOT_FOUND = "Patient %s not found";
  private static final String PRODUCT_NOT_FOUND = "Product %s not found";
  private static final String NO_PATIENTS_HAS_BEEN_FOUND = "No patients has been found";
  private static final String PATIENT_ALREADY_CHECKED_IN = "Patient %s already checked in";
  private static final String PATIENT_IS_NOT_CHECKED_IN = "Patient %s is not checked in";
  private static final String NO_AVAILABLE_BEDS = "No available beds in the hospital %s";
  private static final String NO_AVAILABLE_ITEMS = "No available items";
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

  public Optional<Hospital> findNearestAvailableHospital(Patient patient){

    List<Hospital> hospitals = loadAllHospitals();
    Double patientLat = patient.getLatitude();
    Double patientLong = patient.getLongitude();

    Optional<Hospital> selected = Optional.empty();

    for(Hospital hospital: hospitals){

      if(selected.isEmpty()){
        if(hospital.getAvailableBeds() > 0) selected = Optional.ofNullable(hospital);
      }else{
        if(hospital.getAvailableBeds() > 0)
          if(Coordinates.calculateDistance(patientLat,patientLong, hospital.getLatitude(),hospital.getLongitude()) <
             Coordinates.calculateDistance(patientLat,patientLong,selected.get().getLatitude(), selected.get().getLongitude()))
            selected = Optional.ofNullable(hospital);
      }
    }

    if(selected.isEmpty())throw new HospitalException(NO_HOSPITALS_AVAILABLE);

    return selected;
  }

  public Optional<Hospital> findNearestHospitalWithItem(Hospital hospital, String productId, Long quantity){

    List<Hospital> hospitals = loadAllHospitals();
    Double hospitalLat = hospital.getLatitude();
    Double hospitalLong = hospital.getLongitude();

    Optional<Hospital> selected = Optional.empty();

    for(Hospital hosp: hospitals){
      if(selected.isEmpty()){
        if(hosp.haveEnoughItems(productId, quantity)) selected = Optional.ofNullable(hosp);
      }else{
        if(hospital.haveEnoughItems(productId, quantity))
          if (Coordinates.calculateDistance(hospitalLat, hospitalLong, hospital.getLatitude(), hospital.getLongitude()) <
                  Coordinates.calculateDistance(hospitalLat, hospitalLong, selected.get().getLatitude(), selected.get().getLongitude()))
            selected = Optional.ofNullable(hosp);
        }

    }

    if(selected.isEmpty()) throw new HospitalException(NO_AVAILABLE_ITEMS);

    return selected;
    }

  public Hospital findPatientInHospitals(Patient patient){

    Hospital hospital = null;

     List<Hospital> hospitals = loadAllHospitals();

     for(Hospital h : hospitals)
       if(h.getPatients().containsKey(patient.getId()))
         hospital = h;

     if(hospital == null)
       throw new HospitalException(String.format(PATIENT_IS_NOT_CHECKED_IN, patient.getId()));

    return hospital;
  }

  public Optional<SupplyItem> useItem(String hospitalId, String productId, Long quantity) {

    Hospital hospital = loadHospital(hospitalId);

    if(hospital.getStock().containsKey(productId)){
      hospital.removeItemStock(findProduct(hospitalId,productId), quantity, true);
      save(hospital);

      return Optional.ofNullable(hospital.getStock().get(productId));
    }else{
      Optional<Hospital> supportHospital = findNearestHospitalWithItem(hospital, productId, quantity);

      if(supportHospital.isEmpty()) throw new HospitalException(String.format(PRODUCT_NOT_FOUND, productId));

      hospital = loadHospital(supportHospital.get().getId());

      hospital.removeItemStock(findProduct(hospital.getId(),productId), quantity, false);
      save(hospital);

      return Optional.ofNullable(hospital.getStock().get(productId));
    }
  }
}
