package br.codenation.hospital.manager.repository;

import br.codenation.hospital.manager.model.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HospitalRepository extends MongoRepository<Hospital, String> {
}
