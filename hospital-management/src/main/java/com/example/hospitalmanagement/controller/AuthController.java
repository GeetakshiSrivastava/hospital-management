package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.entity.Staff;
import com.example.hospitalmanagement.repo.StaffRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final StaffRepository staffRepository;

    public AuthController(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody Staff staff) {
        staff.setPassword(passwordEncoder().encode(staff.getPassword()));
        staffRepository.save(staff);
        return ResponseEntity.ok("Staff member created successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Staff staff) {
        Staff authenticatedStaff = staffRepository.findByUsername(staff.getUsername());
        if (authenticatedStaff == null || !passwordEncoder().matches(staff.getPassword(), authenticatedStaff.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        return ResponseEntity.ok("Login successful");
    }

    private PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
    }
}