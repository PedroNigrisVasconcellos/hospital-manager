package br.codenation.hospital.manager.model;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public abstract class SupplyItem {
    public String id;
    protected String name;
    protected ProductType productType;
    protected int quantity;
}
