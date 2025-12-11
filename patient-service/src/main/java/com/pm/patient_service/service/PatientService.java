package com.pm.patient_service.service;

import com.pm.patient_service.dto.request.PatientRequestDTO;
import com.pm.patient_service.dto.response.PatientResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;


public interface PatientService {
    Page<PatientResponseDTO> getPatients(Pageable pageable);

    PatientResponseDTO createPatient(PatientRequestDTO dto);

    PatientResponseDTO updatePatient(UUID id, PatientRequestDTO dto);

    void deletePatient(UUID id);

}
