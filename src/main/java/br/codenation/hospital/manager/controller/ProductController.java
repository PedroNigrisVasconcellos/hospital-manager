package br.codenation.hospital.manager.controller;

import br.codenation.hospital.manager.model.Hospital;
import br.codenation.hospital.manager.model.product.SupplyItem;
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
public class ProductController {

  private final HospitalService hospitalService;

  //  @PostMapping(value = "/{hospitalId}/estoque")
  //  public Callable<ResponseEntity<SupplyItem>> createProduct(@PathVariable("hospitalId") String
  // hospitalId, @Valid @RequestBody SupplyItem stockItem) {
  //    return () -> ResponseEntity.of(hospitalService.loadHospital(hospitalId).map(hospital -> {
  //      stockItem.set
  //      hospital.getStock().put(UUID.randomUUID().toString(), )
  //    }))
  //  }

  @GetMapping(value = "/{hospitalId}/estoque", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Callable<ResponseEntity<List<String>>> getStock(
      @PathVariable("hospitalId") String hospitalId) {
    return () ->
        ResponseEntity.ok(
            hospitalService.loadHospital(hospitalId).getStock().values().stream()
                .map(SupplyItem::getName)
                .collect(Collectors.toList()));
  }

  @GetMapping(
      value = "/{hospitalId}/estoque/{productId}",
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Callable<ResponseEntity<SupplyItem>> getProduct(
      @PathVariable("hospitalId") String hospitalId, @PathVariable("productId") String productId) {
    return () ->
        ResponseEntity.of(
            Optional.ofNullable(
                hospitalService.loadHospital(hospitalId).getStock().get(productId)));
  }

  @PostMapping(value = "/{hospitalId}/estoque")
  public Callable<ResponseEntity<SupplyItem>> saveProduct(
      @PathVariable("hospitalId") String hospitalId, @Valid @RequestBody SupplyItem supplyItem) {
    return () ->
        ResponseEntity.status(HttpStatus.CREATED)
            .body(hospitalService.saveSupplyItem(supplyItem, hospitalId));
  }
}
