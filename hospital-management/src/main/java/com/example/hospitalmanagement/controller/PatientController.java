package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.entity.Patient;
import com.example.hospitalmanagement.repo.PatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientRepository patientRepository;

    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @PostMapping("/admit")
    public ResponseEntity<String> admitPatient(@RequestBody Patient patient) {
        patient.setStatus("admitted");
        patientRepository.save(patient);
        return ResponseEntity.ok("Patient admitted successfully");
    }

    @GetMapping("/admitted")
    public List<Patient> getAllAdmittedPatients() {
        return patientRepository.findByStatus("admitted");
    }

    @PostMapping("/{id}/discharge")
    public ResponseEntity<String> dischargePatient(@PathVariable Long id) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Patient patient = optionalPatient.get();
        patient.setStatus("discharged");
        patientRepository.save(patient);
        return ResponseEntity.ok("Patient discharged successfully");
    }
}
