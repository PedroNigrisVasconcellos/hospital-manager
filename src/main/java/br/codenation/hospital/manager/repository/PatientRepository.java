package br.codenation.hospital.manager.repository;

import br.codenation.hospital.manager.model.Patient;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends ReactiveMongoRepository<Patient, String> {
}
