package com.pm.patient_service.repository.service.impl;

import com.pm.patient_service.exception.PatientNotFoundException;
import com.pm.patient_service.model.Patient;
import com.pm.patient_service.repository.PatientRepository;
import com.pm.patient_service.repository.service.PatientRepositoryService;
import com.pm.patient_service.utils.constants.Commons;
import com.pm.patient_service.utils.message.ErrorMessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientRepositoryServiceImpl implements PatientRepositoryService {
    private final PatientRepository patientRepository;

    @Override
    public Page<Patient> findAllPatients(Pageable pageable) {
        return patientRepository.findAll(pageable);
    }

    @Override
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient findPatientById(UUID id) {
        return patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException(ErrorMessageUtils.NOT_FOUND.formatted(Commons.PATIENT + id))
        );
    }

    @Override
    public void deletePatientById(UUID id) {
        patientRepository.deleteById(id);
    }
}
