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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    if (!this.patients.containsKey(patient.getId())) {
      this.patients.put(patient.getId(), patient);
      return patient;
    }else
      return null;
  }

  public Patient removePatient(String patientId){
    return this.patients.remove(patientId);
  }

  public SupplyItem addItemStock(SupplyItem supplyItem){

    if(!this.stock.containsKey(supplyItem.getId())) {
      this.stock.put(supplyItem.getId(), supplyItem);
      return supplyItem;
    }
    else{
      supplyItem.addItens(this.stock.get(supplyItem.getId()).getQuantity());
      this.stock.replace(supplyItem.getId(),supplyItem);
      return supplyItem;
    }
  }

  public boolean incAvailableBeds(){
    if(this.availableBeds < this.beds) {
      this.availableBeds++;
      return true;
    }else
      return false;
  }

  public boolean decAvailableBeds(){
    if(this.availableBeds > 0) {
      this.availableBeds--;
      return true;
    }else
      return false;
  }

  public boolean patientsMapIsNullOrEmpty() {
    return this.patients == null || this.patients.isEmpty();
  }
}
