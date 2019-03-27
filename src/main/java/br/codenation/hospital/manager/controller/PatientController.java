package br.codenation.hospital.manager.controller;

import br.codenation.hospital.manager.model.Patient;
import br.codenation.hospital.manager.service.HospitalService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/v1/hospitais")
@AllArgsConstructor
public class PatientController {

  private final HospitalService hospitalService;

  @GetMapping(value = "/{hospitalId}/pacientes", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Callable<ResponseEntity<List<String>>> getHospitalPatientsName(
      @PathVariable("hospitalId") String hospitalId) {
    return () ->
        ResponseEntity.ok(
            hospitalService.loadHospital(hospitalId).getPatients().values().stream()
                .map(Patient::getFullName)
                .collect(Collectors.toList()));
  }

  @GetMapping(
      value = "/{hospitalId}/pacientes/{patientId}",
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Callable<ResponseEntity<Patient>> getPatient(
      @PathVariable("hospitalId") String hospitalId, @PathVariable("patientId") String patientId) {
    return () ->
        ResponseEntity.of(
            Optional.ofNullable(
                hospitalService.loadHospital(hospitalId).getPatients().get(patientId)));
  }

  @PostMapping(value = "/{hospitalId}/pacientes")
  public Callable<ResponseEntity<Patient>> createPatient(
      @PathVariable("hospitalId") String hospitalId, @Valid @RequestBody Patient patient) {
    return () -> ResponseEntity.status(HttpStatus.CREATED).body(hospitalService.savePatient(patient, hospitalId));
  }

  @PostMapping(value = "/pacientes")
  public Callable<ResponseEntity<Patient>> createPatient(@Valid @RequestBody Patient patient) {
    return () -> ResponseEntity.status(HttpStatus.CREATED).body(hospitalService.savePatient(patient));
  }

  @PostMapping(value = "/{hospitalId}/{patientId}")
  public Callable<ResponseEntity<Patient>> patientCheckIn(
          @PathVariable("hospitalId") String hospitalId, @PathVariable("patientId") String patientId) {
    return () -> ResponseEntity.status(HttpStatus.CREATED).body(hospitalService.savePatient(patientId, hospitalId));
  }

  //  // TODO - We have to figure out when exactly to persist patients. When a patient is in the
  //  // TODO - hospital it means it has checked-in
  //  // TODO - Should we only save when the patient checks in?
  //  @PostMapping(value = "/v1/pacientes")
  //  public Callable<ResponseEntity<Patient>> createPatient(@Valid @RequestBody Patient patient) {
  //    return () -> ResponseEntity.of(hospitalService.save(patient));
  //  }
}
