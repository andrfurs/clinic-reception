package com.reception.clinic.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the schedule", example = "1")
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "schedule_times",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "time_id")
    )
    @Schema(description = "List of time slots associated with the schedule")
    private List<Time> times;

    public Schedule() {
    }

    public Schedule(Long id, List<Time> times) {
        this.id = id;
        this.times = times;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Time> getTimes() {
        return times;
    }

    public void setTimes(List<Time> times) {
        this.times = times;
    }
}
