package br.codenation.hospital.manager.controller;

import br.codenation.hospital.manager.dto.HospitalDTO;
import br.codenation.hospital.manager.model.Hospital;
import br.codenation.hospital.manager.service.HospitalService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequestMapping(value = "/v1/hospitais")
@AllArgsConstructor
public class HospitalController {

  private final HospitalService hospitalService;

  @PostMapping
  public Callable<ResponseEntity<Hospital>> createHospital(@Valid @RequestBody Hospital hospital) {
    return () -> ResponseEntity.status(HttpStatus.CREATED).body(hospitalService.save(hospital));
  }

  @GetMapping(value = "/{hospitalId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Callable<ResponseEntity<HospitalDTO>> getHospital(
      @PathVariable("hospitalId") String hospitalId) {
    return () -> ResponseEntity.ok(new HospitalDTO(hospitalService.loadHospital(hospitalId)));
  }

  @GetMapping
  public Callable<ResponseEntity<List<Hospital>>> getAllHospitals() {
    return () -> ResponseEntity.ok(hospitalService.loadAllHospitals());
  }

  @GetMapping(value = "/{hospitalId}/leitos", produces =  MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Callable<ResponseEntity<Long>> getAvailableBedsCount(@PathVariable("hospitalId") String hospitalId) {
    return () -> ResponseEntity.ok(hospitalService.loadHospital(hospitalId).getAvailableBeds());
  }

}
