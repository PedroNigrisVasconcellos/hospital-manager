package br.codenation.hospital.manager.model.product;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class SupplyItem {

  @NotNull @NotBlank protected String id;
  @NotEmpty @NotBlank protected String name;

  @NotNull
  @Min(0)
  protected Long quantity;

  protected SupplyType supplyType;

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
