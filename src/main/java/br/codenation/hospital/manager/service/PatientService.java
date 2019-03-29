package br.codenation.hospital.manager.service;

import br.codenation.hospital.manager.exception.ResourceNotFoundException;
import br.codenation.hospital.manager.model.Patient;
import br.codenation.hospital.manager.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PatientService {

    private static final String PATIENT_NOT_FOUND = "Patient %s not found";

    @Autowired
    private final PatientRepository patientRepository;

    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient loadPatient(String patientId) {
        return patientRepository
                .findById(patientId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format(PATIENT_NOT_FOUND, patientId)));
    }

    public List<Patient> loadAllPatients(){
        return patientRepository.findAll();
    }

    public Patient update(Patient patientUpdated){
        Optional<Patient> patient = patientRepository.findById(patientUpdated.getId());

        if(patient.isPresent()){
            Patient updated = patient.get();
            updated.setHospitalCheckIn(patientUpdated.getHospitalCheckIn());
            patientRepository.save(updated);
            return updated;
        }else
            throw new ResourceNotFoundException(String.format(PATIENT_NOT_FOUND,patientUpdated.getId()));

    }

}
