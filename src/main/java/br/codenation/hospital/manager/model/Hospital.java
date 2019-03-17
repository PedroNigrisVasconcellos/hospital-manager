package br.codenation.hospital.manager.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Hospital {
    @Id
    private String id;
    private String nome;
    private String endereco;
    private long numeroLeitos;
    private long numeroLeitosDisponiveis;
    private List<ItemEstoque> estoque;
    private List<Paciente> pacientesHospital;
    private float latitude;
    private float longitude;
}
