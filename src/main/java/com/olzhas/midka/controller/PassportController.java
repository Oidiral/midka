package com.olzhas.midka.controller;

import com.olzhas.midka.entity.Passport;
import com.olzhas.midka.repository.EmployeeRepository;
import com.olzhas.midka.repository.PassportRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passports")
@RequiredArgsConstructor
public class PassportController {

    private final PassportRepository passportRepository;
    private final EmployeeRepository employeeRepository;

    @GetMapping
    public List<Passport> getAll() {
        return passportRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passport> getById(@PathVariable Long id) {
        return passportRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Passport> create(@RequestParam Long employeeId, @Valid @RequestBody Passport passport) {
        return employeeRepository.findById(employeeId)
                .map(employee -> {
                    passport.setEmployee(employee);
                    return ResponseEntity.ok(passportRepository.save(passport));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Passport> update(@PathVariable Long id, @Valid @RequestBody Passport passport) {
        return passportRepository.findById(id)
                .map(existing -> {
                    existing.setPassportNumber(passport.getPassportNumber());
                    existing.setIssuedCountry(passport.getIssuedCountry());
                    return ResponseEntity.ok(passportRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!passportRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        passportRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
