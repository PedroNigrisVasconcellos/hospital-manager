package br.codenation.hospital.manager.repository;

import br.codenation.hospital.manager.model.Hospital;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends MongoRepository<Hospital, String> {
    List<Hospital> findByLocationNear(Point location);
}
