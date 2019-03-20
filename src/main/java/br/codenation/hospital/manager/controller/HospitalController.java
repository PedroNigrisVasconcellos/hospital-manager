package br.codenation.hospital.manager.controller;

import br.codenation.hospital.manager.model.Hospital;
import br.codenation.hospital.manager.service.HospitalService;
import br.codenation.hospital.manager.view.HospitalView;
import br.codenation.hospital.manager.view.PatientView;
import br.codenation.hospital.manager.view.ProductView;
import br.codenation.hospital.manager.view.StockView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

@RestController
@RequestMapping(value = "/v1/hospitais")
public class HospitalController {

  @Autowired private HospitalService hospitalService;

  @GetMapping(value = "/{hospitalId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Callable<HospitalView> getHospitalInformation(
      @PathVariable("hospitalId") String hospitalId) {
    Hospital hospital = hospitalService.findById(hospitalId);
    return () ->
        new HospitalView(
            hospital.getName(),
            hospital.getAddress(),
            hospital.getBeds(),
            hospital.getAvailableBeds());
  }

  @GetMapping(value = "/{hospitalId}/estoque", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Callable<StockView> getStockInformation(@PathVariable("hospitalId") String hospitalId) {
    return StockView::new;
  }

  @GetMapping(
      value = "/{hospitalId}/estoque/{productId}",
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Callable<ProductView> getProductInformation(
      @PathVariable("hospitalId") String hospitalId, @PathVariable("productId") String productId) {
    return ProductView::new;
  }

  @GetMapping(value = "/{hospitalId}/pacientes", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Callable<List<String>> getHospitalPatients(@PathVariable("hospitalId") String hospitalId) {
    return ArrayList::new;
  }

  @GetMapping(
      value = "/{hospitalId}/pacientes/{patientId}",
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Callable<PatientView> getPatientInformation(
      @PathVariable("hospitalId") String hospitalId, @PathVariable("patientId") String patientId) {
    return () ->
        new PatientView(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            LocalDate.now(),
            UUID.randomUUID().toString(),
            LocalDate.now());
  }
}
