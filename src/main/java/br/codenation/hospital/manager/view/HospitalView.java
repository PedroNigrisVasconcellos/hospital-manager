package br.codenation.hospital.manager.view;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class HospitalView {

    private final String name;
    private final String address;
    private final long beds;
    private final long availableBeds;
}
