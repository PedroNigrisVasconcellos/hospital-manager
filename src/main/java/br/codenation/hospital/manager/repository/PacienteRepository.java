package br.codenation.hospital.manager.repository;

import br.codenation.hospital.manager.model.Paciente;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PacienteRepository extends MongoRepository<Paciente, String> {
}
