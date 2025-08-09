package org.nology.postcodeapi.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="postcodes",
uniqueConstraints = @UniqueConstraint(name="uk_postcodes_code", columnNames = {"postcode"})) // to ensure each postcode is unique in table
public class Postcode {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) //mySQL auto-increment
    private Long id;

    @NotNull(message = "Postcode is required")
    @Pattern(regexp = "[0-9]{4}$", message = "AU postcodes are 4 digits (e.g, 3000)")
    @Column(name="postcode", nullable=false, length = 4)
    private String code;

    @NotNull(message="State is required")
    @Enumerated(EnumType.STRING)
    @Column(name="state",  nullable=false, length = 10)
    private State state;

    @OneToMany(mappedBy = "postcode", cascade = CascadeType.ALL)
    private List<Suburb> suburbs = new ArrayList<>();


    // ───────────────────────────── // Getters & Setters ──────────────────────────────
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
