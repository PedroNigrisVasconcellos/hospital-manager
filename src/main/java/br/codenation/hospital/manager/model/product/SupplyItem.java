package br.codenation.hospital.manager.model.product;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SupplyItem {

  @NotNull @NotBlank protected final String id;
  @NotEmpty @NotBlank protected final String name;

  @NotNull
  @Min(0)
  protected Long quantity;

  @NotEmpty @NotBlank protected final SupplyType supplyType;

  public SupplyItem(String id, String name, Long quantity, SupplyType supplyType){
      this.id = id;
      this.name = name;
      this.quantity = quantity;
      this.supplyType = supplyType;
  }

  public void addItens(Long quantity){
      this.quantity += quantity;
  }

}
