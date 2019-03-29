package br.codenation.hospital.manager.controller;

import br.codenation.hospital.manager.dto.PatientDTO;
import br.codenation.hospital.manager.model.Patient;
import br.codenation.hospital.manager.service.HospitalService;
import br.codenation.hospital.manager.service.PatientService;
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
  private final PatientService patientService;

  @GetMapping(value = "/{hospitalId}/pacientes", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Callable<ResponseEntity<List<String>>> getHospitalPatientsName(
      @PathVariable("hospitalId") String hospitalId) {
    return () ->
        ResponseEntity.ok(
                hospitalService.findPatients(hospitalId).stream().
                        map(Patient::getFullName).
                        collect(Collectors.toList()));
  }

  @GetMapping(value = "/{hospitalId}/pacientes/{patientId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Callable<ResponseEntity<PatientDTO>> getPatient(
      @PathVariable("hospitalId") String hospitalId, @PathVariable("patientId") String patientId) {
    return () ->
        ResponseEntity.of(
                Optional.ofNullable(
                        new PatientDTO(hospitalService.findPatient(patientId,hospitalId))));
  }

  @PostMapping(value = "/{hospitalId}/pacientes")
  public Callable<ResponseEntity<Patient>> createPatient(
      @PathVariable("hospitalId") String hospitalId, @Valid @RequestBody Patient patient) {
    return () -> ResponseEntity.status(HttpStatus.CREATED).body(hospitalService.checkinPatient(patient, hospitalId));
  }

  @PostMapping(value = "/pacientes")
  public Callable<ResponseEntity<Patient>> createPatient(@Valid @RequestBody Patient patient) {
    return () -> ResponseEntity.status(HttpStatus.CREATED).body(patientService.save(patient));
  }

  @GetMapping(value = "/pacientes", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Callable<ResponseEntity<List<Patient>>> findAllPatientsDB() {
    return () -> ResponseEntity.of(
            Optional.ofNullable(
                    patientService.loadAllPatients()));
  }

  @PostMapping(value = "/{hospitalId}/checkin/{patientId}")
  public Callable<ResponseEntity<Patient>> patientCheckIn(
          @PathVariable("hospitalId") String hospitalId, @PathVariable("patientId") String patientId) {
    return () -> ResponseEntity.status(HttpStatus.OK).body(hospitalService.checkinPatient(patientService.loadPatient(patientId), hospitalId));
  }

  @PostMapping(value = "/{hospitalId}/checkout/{patientId}")
  public Callable<ResponseEntity<Patient>> patientCheckOut(
          @PathVariable("hospitalId") String hospitalId, @PathVariable("patientId") String patientId) {
    return () -> ResponseEntity.status(HttpStatus.OK).body(hospitalService.checkoutPatient(patientId, hospitalId));
  }

}
