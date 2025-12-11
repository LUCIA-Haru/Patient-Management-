package com.pm.patient_service.serviceImpl;

import com.pm.patient_service.dto.request.PatientRequestDTO;
import com.pm.patient_service.dto.response.PatientResponseDTO;
import com.pm.patient_service.exception.EmailAlreadyExistsException;
import com.pm.patient_service.exception.PatientNotFoundException;
import com.pm.patient_service.mapper.PatientMapper;
import com.pm.patient_service.model.Patient;
import com.pm.patient_service.repository.PatientRepository;
import com.pm.patient_service.service.PatientService;
import com.pm.patient_service.utils.constants.Commons;
import com.pm.patient_service.utils.message.ErrorMessageUtils;
import com.pm.patient_service.utils.message.SuccessMessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    private static final String className = "PatientServiceImpl";


    @Override
    public Page<PatientResponseDTO> getPatients(Pageable pageable) {
        log.info("{} => getPatients()", className);

        Page<Patient> patients = findAllPatients(pageable);
        log.info(SuccessMessageUtils.SUCCESS_OPERATION.formatted("Fetched","patients"));
        return patients.map(PatientMapper::toDTO);
    }


    @Override
    public PatientResponseDTO createPatient(PatientRequestDTO dto) {
        log.info("{} => createPatient()", className);
            if (patientRepository.existsByEmail(dto.getEmail()))
                throw new EmailAlreadyExistsException(ErrorMessageUtils
                        .ALREADY_EXIST.formatted("A patient", "email" ) + dto.getEmail());

            Patient newPatient = savePatient(PatientMapper.toModel(dto));
            log.info(SuccessMessageUtils.SUCCESS_OPERATION.formatted("✅"+Commons.PATIENT,"created"));
            return PatientMapper.toDTO(newPatient);
    }

    @Override
    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO dto) {
        log.info("{} => updatePatient()", className);
            Patient patient = findPatientById(id);

        if (patientRepository.existsByEmail(dto.getEmail()))
            throw new EmailAlreadyExistsException(ErrorMessageUtils
                    .ALREADY_EXIST.formatted("A patient", "email" ) + dto.getEmail());

        patient.setName(dto.getName());
        patient.setAddress(dto.getAddress());
        patient.setEmail(dto.getEmail());
        patient.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));
        patient.setUpdatedOn(LocalDateTime.now());
       Patient updatedPatient = savePatient(patient);
        log.info(SuccessMessageUtils.SUCCESS_OPERATION.formatted("✅"+Commons.PATIENT,"updated"));

        return PatientMapper.toDTO(updatedPatient);
    }
    @Override
    public void deletePatient(UUID id) {
        deletePatientById(id);
    }


    ///////////
    @Override
    public Page<Patient> findAllPatients(Pageable pageable){
        return patientRepository.findAll(pageable);
    }

    @Override
    public Patient savePatient(Patient patient){
        return patientRepository.save(patient);
    }

    @Override
    public Patient findPatientById(UUID id){

        return patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException(ErrorMessageUtils.NOT_FOUND.formatted(Commons.PATIENT + id))
        );
    }

    @Override
    public void deletePatientById(UUID id) {
        patientRepository.deleteById(id);
    }



}

