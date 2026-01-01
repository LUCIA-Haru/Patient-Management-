package com.pm.patient_service.controller;

import com.pm.patient_service.dto.request.PatientRequestDTO;
import com.pm.patient_service.dto.response.PatientResponseDTO;
import com.pm.patient_service.dto.validators.CreatePatientValidationGroup;
import com.pm.patient_service.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/patients")
@Tag(name = "Patient",description = "API for managing Patients")
public class PatientController {
    @Autowired
    PatientService patientService;

    @GetMapping
    @Operation(summary = "Get Patients")
    public Page<PatientResponseDTO> getPatients(
            @PageableDefault(size = 10, sort = "registeredDate", direction = Sort.Direction.DESC) Pageable pageable
    ){
            return patientService.getPatients(pageable);

    }

    @PostMapping
    @Operation(summary = "Create Patient")
    public PatientResponseDTO createPatient( @Validated({Default.class, CreatePatientValidationGroup.class})
                                                 @RequestBody PatientRequestDTO dto){
        return patientService.createPatient(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Patient")
    public PatientResponseDTO updatePatient(@Valid @PathVariable UUID id,
                                            @Validated({Default.class}) @RequestBody PatientRequestDTO dto){
        return patientService.updatePatient(id,dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Patient")
    public void deletePatient(@Valid @PathVariable UUID id){
        patientService.deletePatient(id);
    }

}
