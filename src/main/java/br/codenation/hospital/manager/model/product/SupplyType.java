package br.codenation.hospital.manager.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@ToString
@AllArgsConstructor
public enum SupplyType {
  PRODUCT("product"),
  BLOOD_BANK("bloodBank");

  @NotEmpty
  @NotBlank
  private final String value;
}
