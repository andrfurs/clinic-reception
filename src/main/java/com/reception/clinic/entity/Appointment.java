package com.reception.clinic.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the appointment", example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    @Schema(description = "ID of the doctor in the appointment", example = "1")
    private Doctor doctor;

    @Schema(description = "Name of the patient", example = "Ivan Ivanov")
    private String patientName;

    @ManyToOne
    @JoinColumn(name = "schedule_time_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Schema(description = "ID of the time in the appointment", example = "1")
    private Time scheduleTime;

    public Appointment() {
    }

    public Appointment(Long id, Doctor doctor, String patientName, Time scheduleTime) {
        this.id = id;
        this.doctor = doctor;
        this.patientName = patientName;
        this.scheduleTime = scheduleTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Time getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(Time scheduleTime) {
        this.scheduleTime = scheduleTime;
    }
}
