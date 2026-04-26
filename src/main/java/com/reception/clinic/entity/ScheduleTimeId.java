package com.reception.clinic.entity;

import java.io.Serializable;
import java.util.Objects;

public class ScheduleTimeId implements Serializable {

    private Long scheduleId;
    private Long timeId;

    public ScheduleTimeId() {
    }

    public ScheduleTimeId(Long scheduleId, Long timeId) {
        this.scheduleId = scheduleId;
        this.timeId = timeId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getTimeId() {
        return timeId;
    }

    public void setTimeId(Long timeId) {
        this.timeId = timeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleTimeId that = (ScheduleTimeId) o;
        return Objects.equals(scheduleId, that.scheduleId) && Objects.equals(timeId, that.timeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId, timeId);
    }
}
