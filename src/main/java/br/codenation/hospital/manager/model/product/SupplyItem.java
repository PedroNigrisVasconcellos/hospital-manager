package br.codenation.hospital.manager.model.product;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SupplyItem {

  protected final String id;
  @NotEmpty @NotBlank protected final String name;

  @NotNull
  @Min(0)
  protected final Long quantity;

  @NotEmpty @NotBlank protected final SupplyType supplyType;
}
