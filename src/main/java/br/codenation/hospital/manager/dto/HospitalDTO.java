package br.codenation.hospital.manager.dto;

import br.codenation.hospital.manager.model.Hospital;
import lombok.Data;

@Data
public class HospitalDTO {

    private String name;
    private String address;
    private Long beds;
    private Long availableBeds;

    public HospitalDTO(Hospital hospital){
        this.name = hospital.getName();
        this.address = hospital.getAddress();
        this.beds = hospital.getBeds();
        this.availableBeds = hospital.getAvailableBeds();
    }
}
