package com.reception.clinic.service;

import com.reception.clinic.entity.Schedule;
import com.reception.clinic.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public Optional<Schedule> getScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }

    public Iterable<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    @Transactional
    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Transactional
    public void deleteScheduleById(Long id) {
        scheduleRepository.deleteById(id);
    }
}
