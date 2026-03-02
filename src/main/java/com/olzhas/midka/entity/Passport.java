package com.olzhas.midka.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Table(name = "passports")
@Getter
@Setter
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Passport number is required")
    @Column(nullable = false, unique = true)
    private String passportNumber;

    @NotBlank(message = "Issued country is required")
    private String issuedCountry;

    @OneToOne
    @JoinColumn(name = "employee_id", unique = true)
    @JsonBackReference
    private Employee employee;
}
