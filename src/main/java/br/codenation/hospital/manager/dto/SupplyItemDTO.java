package br.codenation.hospital.manager.dto;

import br.codenation.hospital.manager.model.product.SupplyItem;
import br.codenation.hospital.manager.model.product.SupplyType;
import lombok.Data;

@Data
public class SupplyItemDTO {

    protected final String name;
    protected Long quantity;
    protected final SupplyType supplyType;

    public SupplyItemDTO(SupplyItem supplyItem){
        this.name = supplyItem.getName();
        this.quantity = supplyItem.getQuantity();
        this.supplyType = supplyItem.getSupplyType();
    }

}
