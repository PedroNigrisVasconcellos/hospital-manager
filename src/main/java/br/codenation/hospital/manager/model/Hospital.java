package br.codenation.hospital.manager.model;

import br.codenation.hospital.manager.model.product.StockItem;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

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

  private Collection<StockItem> stock;
  private Collection<Patient> patients;

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
  }
}
