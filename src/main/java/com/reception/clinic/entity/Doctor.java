package com.reception.clinic.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the doctor", example = "1")
    private Long id;

    @Schema(description = "Name of the doctor", example = "Ivan Ivanov")
    private String name;

    @Schema(description = "Specialty of the doctor", example = "surgeon")
    private String speciality;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    @Schema(description = "ID of the schedule associated with the doctor", example = "1")
    private Schedule schedule;

    public Doctor() {
    }

    public Doctor(Long id, String name, String speciality, Schedule schedule) {
        this.id = id;
        this.name = name;
        this.speciality = speciality;
        this.schedule = schedule;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
