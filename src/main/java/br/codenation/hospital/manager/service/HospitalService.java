package br.codenation.hospital.manager.service;

import br.codenation.hospital.manager.model.Hospital;
import br.codenation.hospital.manager.repository.HospitalRepository;
import br.codenation.hospital.manager.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HospitalService {

  private final HospitalRepository hospitalRepository;

  @Autowired
  public HospitalService(HospitalRepository hospitalRepository) {
    this.hospitalRepository = hospitalRepository;
  }

  public Hospital findById(String id) {
    return hospitalRepository
        .findById(id)
        .orElseThrow(
            () -> new ObjectNotFoundException("Hospital com id " + id + " não encontrado."));
  }
}
