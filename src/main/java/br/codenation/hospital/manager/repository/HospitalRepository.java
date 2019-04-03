package br.codenation.hospital.manager.repository;

import br.codenation.hospital.manager.model.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends MongoRepository<Hospital, String> {

}
