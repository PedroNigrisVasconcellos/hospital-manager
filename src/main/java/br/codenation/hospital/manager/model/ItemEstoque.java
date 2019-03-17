package br.codenation.hospital.manager.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public abstract class ItemEstoque {
    public String id;
    protected String nome;
    protected TipoProduto tipoProduto;
    protected int quantidade;
}
