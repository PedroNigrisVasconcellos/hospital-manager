package br.codenation.hospital.manager.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplyItem {

  protected String id;
  @NotEmpty @NotBlank protected String name;

  @NotNull
  @Min(0)
  protected Long quantity;

  @NotNull protected SupplyType supplyType;
}
