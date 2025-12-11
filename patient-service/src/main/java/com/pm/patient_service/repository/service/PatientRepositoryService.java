package com.pm.patient_service.repository.service;

import com.pm.patient_service.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PatientRepositoryService {
    Page<Patient> findAllPatients(Pageable pageable);
    Patient savePatient(Patient patient);
    Patient findPatientById(UUID id);
    void deletePatientById(UUID id);
}
