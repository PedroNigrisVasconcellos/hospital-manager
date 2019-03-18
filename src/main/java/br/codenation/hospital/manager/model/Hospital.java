package br.codenation.hospital.manager.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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

  private List<String> products = new ArrayList<>();
  private List<String> patients = new ArrayList<>();

  public Hospital(String name, String address, Long beds, Long availableBeds) {
    this.name = name;
    this.address = address;
    this.beds = beds;
    this.availableBeds = availableBeds;
  }
}
