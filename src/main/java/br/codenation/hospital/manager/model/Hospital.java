package br.codenation.hospital.manager.model;

import br.codenation.hospital.manager.exception.HospitalException;
import br.codenation.hospital.manager.model.product.SupplyItem;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

@Data
@NoArgsConstructor
@Document(collection = "hospitals")
@TypeAlias("hospital")
public class Hospital {

  @Id private String id;

  @NotEmpty @NotBlank private String name;
  @NotEmpty @NotBlank private String address;

  @NotNull
  @Min(0)
  private Long beds;

  @NotNull
  @Min(0)
  private Long availableBeds;

  private Map<String, SupplyItem> stock;
  private Map<String, Patient> patients;

  @NotNull private Double latitude;
  @NotNull private Double longitude;

  public Hospital(
      String name,
      String address,
      Long beds,
      Long availableBeds,
      Double latitude,
      Double longitude) {
    this.name = name;
    this.address = address;
    this.beds = beds;
    this.availableBeds = availableBeds;
    this.latitude = latitude;
    this.longitude = longitude;
    this.patients = new HashMap<>();
    this.stock = new HashMap<>();
  }

  public Patient addNewPatient(Patient patient) {

    if (patients.containsKey(patient.getId()))
      return null;

    patients.put(patient.getId(), patient);
    return patient;
  }

  public Optional<Patient> removePatient(String patientId){
    return Optional.of(patients.remove(patientId));
  }

  public SupplyItem addItemStock(SupplyItem supplyItem){

    if (stock.containsKey(supplyItem.getId())) {
      final long currentQuantity = supplyItem.getQuantity();
      supplyItem.setQuantity(currentQuantity + stock.get(supplyItem.getId()).getQuantity());

      return stock.replace(supplyItem.getId(), supplyItem);
    }

    return stock.putIfAbsent(supplyItem.getId(), supplyItem);

  }

  public boolean incrementAvailableBeds(){
    if(availableBeds < beds) {
      availableBeds++;
      return true;
    }else
      return false;
  }

  public boolean decrementAvailableBeds(){
    if(availableBeds > 0) {
      availableBeds--;
      return true;
    }else
      return false;
  }

}
