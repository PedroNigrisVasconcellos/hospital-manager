package br.codenation.hospital.manager.model.product;

import lombok.Data;

@Data
public abstract class StockItem {
  protected final String id;
  protected final String name;
  protected final Integer quantity;
}
