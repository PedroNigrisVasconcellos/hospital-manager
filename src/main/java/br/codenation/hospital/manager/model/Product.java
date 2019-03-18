package br.codenation.hospital.manager.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Document(collection = "products")
@TypeAlias("product")
public class Product {

  @Id private String id;

  @NotEmpty @NotBlank private String name;
  @NotEmpty @NotBlank private String description;
  @NotNull private Long quantity;

  public Product(String name, String description, Long quantity) {
    this.name = name;
    this.description = description;
    this.quantity = quantity;
  }
}
