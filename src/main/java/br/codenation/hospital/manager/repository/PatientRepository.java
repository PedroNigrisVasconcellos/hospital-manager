package br.codenation.hospital.manager.repository;

import br.codenation.hospital.manager.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatientRepository extends MongoRepository<Patient, String> {}
