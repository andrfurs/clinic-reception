package com.reception.clinic.service;

import com.reception.clinic.entity.Appointment;
import com.reception.clinic.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public Iterable<Appointment> getAppointmentByDoctorId(Long id) {
        return appointmentRepository.findAppointmentsByDoctorId(id);
    }

    public Iterable<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Transactional
    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Transactional
    public void deleteAppointmentById(Long id) {
        appointmentRepository.deleteById(id);
    }
}
