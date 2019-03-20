package br.codenation.hospital.manager.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@AllArgsConstructor
public class Hospital {
    @Id
    private String id;
    private String name;
    private String address;
    private long beds;
    private long availableBeds;
    private List<SupplyItem> listOfSupplyItems;
    private List<Patient> hospitalPatients;
    private float latitude;
    private float longitude;
}
