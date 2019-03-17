package br.codenation.hospital.manager.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Paciente {
    @Id
    private String id;
    private String nomeCompleto;
    private String cpf;
    private LocalDate dataNascimento;
    private Genero genero;
    private LocalDate dataEntradaHospital;
}
