package br.codenation.hospital.manager.repository;

import br.codenation.hospital.manager.model.Hospital;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends ReactiveMongoRepository<Hospital, String> {}
