package com.reception.clinic.controller;

import com.reception.clinic.entity.Appointment;
import com.reception.clinic.entity.Doctor;
import com.reception.clinic.service.AppointmentService;
import com.reception.clinic.service.DoctorService;
import com.reception.clinic.service.ScheduleService;
import com.reception.clinic.service.TimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    DoctorService doctorService;
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    TimeService timeService;
    @Autowired
    AppointmentService appointmentService;

    @Operation(summary = "Get list of doctors", description = "Retrieve all doctors in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List retrieved successfully", content = @Content(examples = @ExampleObject("[{\"id\":1,\"name\":\"Ivan Ivanov\",\"speciality\":\"pediatrician\",\"scheduleId\":1},{\"id\":2,\"name\":\"Maksym Maksymenko\",\"speciality\":\"traumatologist\",\"scheduleId\":2}]"))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/doctors")
    ResponseEntity<Iterable<Doctor>> findDoctors() {
        Iterable<Doctor> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    @Operation(summary = "Get list of appointments", description = "Retrieve all appointments in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List retrieved successfully", content = @Content(examples = @ExampleObject("[{\"id\":1,\"doctorId\":1,\"patientName\":\"Petro Petrov\",\"scheduleTimeId\":3},{\"id\":2,\"doctorId\":3,\"patientName\":\"Ivan Ivanov\",\"scheduleTimeId\":1}]"))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/appointments")
    ResponseEntity<Iterable<Appointment>> findAppointments() {
        Iterable<Appointment> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    @Operation(summary = "Delete an appointment", description = "Delete an existing appointment by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Appointment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @DeleteMapping("/appointments/{id}")
    ResponseEntity<Void> deleteAppointment(@Parameter(description = "The ID of the appointment to be deleted", example = "1") @PathVariable Long id) {
        if (appointmentService.getAppointmentById(id) != null) {
            appointmentService.deleteAppointmentById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Create a new appointment", description = "Create a new appointment for a specific doctor and time.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Appointment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/appointments")
    ResponseEntity<Appointment> createAppointment(
            @Parameter(description = "The appointment data", example = "{\"doctorId\": 1, \"patientName\": \"Ivan Ivanov\", \"timeId\": 1}") @RequestBody Map<String, Object> appointmentData) {
        Long doctorId = Long.parseLong(appointmentData.get("doctorId").toString());
        String patientName = appointmentData.get("patientName").toString();
        Long timeId = Long.parseLong(appointmentData.get("scheduleTimeId").toString());

        if (!scheduleService.getScheduleById(doctorService.getDoctorById(doctorId).get().getSchedule().getId()).get().getTimes().contains(timeService.getTimeById(timeId).get())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Appointment appointment = new Appointment(null, doctorService.getDoctorById(doctorId).get(), patientName, timeService.getTimeById(timeId).get());
        appointmentService.saveAppointment(appointment);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
