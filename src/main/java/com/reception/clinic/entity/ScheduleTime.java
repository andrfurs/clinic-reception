package com.reception.clinic.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "schedule_times")
public class ScheduleTime {

    @EmbeddedId
    private ScheduleTimeId id;

    @ManyToOne
    @MapsId("scheduleId")
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne
    @MapsId("timeId")
    @JoinColumn(name = "time_id")
    private Time time;

    public ScheduleTime() {
    }

    public ScheduleTime(Schedule schedule, Time time) {
        this.schedule = schedule;
        this.time = time;
        this.id = new ScheduleTimeId(schedule.getId(), time.getId());
    }

    public ScheduleTimeId getId() {
        return id;
    }

    public void setId(ScheduleTimeId id) {
        this.id = id;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
