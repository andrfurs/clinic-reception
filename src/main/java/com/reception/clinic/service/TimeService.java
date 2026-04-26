package com.reception.clinic.service;

import com.reception.clinic.entity.Time;
import com.reception.clinic.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TimeService {
    private final TimeRepository timeRepository;

    @Autowired
    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public Optional<Time> getTimeById(Long id) {
        return timeRepository.findById(id);
    }

    public Iterable<Time> getAllTimes() {
        return timeRepository.findAll();
    }

    @Transactional
    public Time saveTime(Time time) {
        return timeRepository.save(time);
    }

    @Transactional
    public void deleteTimeById(Long id) {
        timeRepository.deleteById(id);
    }
}
