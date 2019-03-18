package br.codenation.hospital.manager.controller;

import br.codenation.hospital.manager.exception.BadRequestException;
import br.codenation.hospital.manager.model.Hospital;
import br.codenation.hospital.manager.model.Patient;
import br.codenation.hospital.manager.model.Product;
import br.codenation.hospital.manager.repository.HospitalRepository;
import br.codenation.hospital.manager.repository.PatientRepository;
import br.codenation.hospital.manager.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/hospitais")
public class HospitalController {

  private final HospitalRepository hospitalRepository;
  private final ProductRepository productRepository;
  private final PatientRepository patientRepository;

  @PostMapping
  public Mono<Hospital> createHospital(@Valid @RequestBody Hospital hospital) {
    return hospitalRepository.save(hospital);
  }

  @GetMapping(value = "/{hospitalId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Mono<ResponseEntity<Hospital>> getHospitalById(
      @PathVariable("hospitalId") String hospitalId) {
    return hospitalRepository
        .findById(hospitalId)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping(value = "/{hospitalId}/estoque")
  public Mono<Product> createProduct(
      @PathVariable("hospitalId") String hospitalId, @Valid @RequestBody Product product) {

    return productRepository
        .save(product)
        .zipWith(hospitalRepository.findById(hospitalId))
        .flatMap(
            objects -> {
              final Product tupleProduct = objects.getT1();
              final Hospital tupleHospital = objects.getT2();
              tupleHospital.getProducts().add(tupleProduct.getId());
              return Mono.just(tupleProduct).zipWith(hospitalRepository.save(tupleHospital));
            })
        .map(Tuple2::getT1);
  }

  @GetMapping(value = "/{hospitalId}/estoque", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Mono<List<String>> getHospitalStock(@PathVariable("hospitalId") String hospitalId) {
    return hospitalRepository.findById(hospitalId).map(Hospital::getProducts);
  }

  @GetMapping(
      value = "/{hospitalId}/estoque/{productId}",
      produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Mono<ResponseEntity<Product>> getProductInfo(
      @PathVariable("hospitalId") String hospitalId, @PathVariable("productId") String productId) {

    final Mono<Hospital> error =
        Mono.error(
            new BadRequestException(
                String.format("Hospital %s does not has the product %s", hospitalId, productId)));

    return hospitalRepository
        .findById(hospitalId)
        .filter(hospital -> hospital.getProducts().contains(productId))
        .switchIfEmpty(error)
        .flatMap(ignored -> productRepository.findById(productId))
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping(value = "/{hospitalId}/pacientes")
  public Mono<Patient> createPatient(
      @PathVariable("hospitalId") String hospitalId, @Valid @RequestBody Patient patient) {

    return patientRepository
        .save(patient)
        .zipWith(hospitalRepository.findById(hospitalId))
        .flatMap(
            objects -> {
              final Patient tuplePatient = objects.getT1();
              final Hospital tupleHospital = objects.getT2();
              tupleHospital.getPatients().add(tuplePatient.getId());
              return Mono.just(tuplePatient).zipWith(hospitalRepository.save(tupleHospital));
            })
        .map(Tuple2::getT1);
  }

  @GetMapping(value = "/{hospitalId}/pacientes", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Mono<List<String>> getHospitalPatientNames(@PathVariable("hospitalId") String hospitalId) {
    return hospitalRepository.findById(hospitalId).map(Hospital::getPatients);
  }

  @GetMapping(
      value = "/{hospitalId}/pacientes/{patientId}",
      produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Mono<ResponseEntity<Product>> getPatientInfo(
      @PathVariable("hospitalId") String hospitalId, @PathVariable("patientId") String patientId) {

    final Mono<Hospital> error =
        Mono.error(
            new BadRequestException(
                String.format("Hospital %s does not has the patient %s", hospitalId, patientId)));

    return hospitalRepository
        .findById(hospitalId)
        .filter(hospital -> hospital.getProducts().contains(patientId))
        .switchIfEmpty(error)
        .flatMap(ignored -> productRepository.findById(patientId))
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }
}
