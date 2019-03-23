package br.codenation.hospital.manager.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum SupplyType {
  PRODUCT("product"),
  BLOOD_BANK("bloodBank");

  private final String value;
}
