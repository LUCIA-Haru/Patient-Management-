package com.pm.patient_service.mapper;

import com.pm.patient_service.dto.request.PatientRequestDTO;
import com.pm.patient_service.dto.response.PatientResponseDTO;
import com.pm.patient_service.model.Patient;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PatientMapper {

    public static PatientResponseDTO toDTO(Patient patient)
    {
        PatientResponseDTO patientResponseDTO = new PatientResponseDTO();
        patientResponseDTO.setId(patient.getId().toString());
        patientResponseDTO.setName(patient.getName());
        patientResponseDTO.setEmail(patient.getEmail());
        patientResponseDTO.setAddress(patient.getAddress());
        patientResponseDTO.setDateOfBirth(patient.getDateOfBirth().toString());
        return patientResponseDTO;
    }

    public static Patient toModel(PatientRequestDTO dto){
        Patient patient = new Patient();
        patient.setName(dto.getName());
        patient.setEmail(dto.getEmail());
        patient.setAddress(dto.getAddress());
        patient.setRegisteredDate(LocalDateTime.parse(dto.getRegisteredDate()));
        patient.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));

        return patient;
    }

}
