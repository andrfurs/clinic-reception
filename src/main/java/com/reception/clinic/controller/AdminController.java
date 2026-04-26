package com.reception.clinic.controller;

import com.reception.clinic.entity.Doctor;
import com.reception.clinic.entity.Schedule;
import com.reception.clinic.entity.Time;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    TimeService timeService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    DoctorService doctorService;

    @Operation(summary = "Get list of times", description = "Retrieve all times in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List retrieved successfully", content = @Content(examples = @ExampleObject("[{\"id\":1,\"time\":\"2024-11-25\"},{\"id\":2,\"time\":\"2024-11-26\"}]"))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/times")
    ResponseEntity<Iterable<Time>> findTimes() {
        Iterable<Time> times = timeService.getAllTimes();
        return ResponseEntity.ok(times);
    }

    @Operation(summary = "Create a new time", description = "Create a new time in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Time created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/times")
    ResponseEntity<Time> createTime(@Parameter(description = "The time object to be created.", example = "{\"time\": \"2024-08-25\"}") @RequestBody Time time) {
        if (time == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        timeService.saveTime(time);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update a time", description = "Update time by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Time updated successfully"),
            @ApiResponse(responseCode = "404", description = "Time not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/times/{id}")
    ResponseEntity<Time> updateTime(
            @Parameter(description = "The ID of the time to be updated", example = "1") @PathVariable Long id,
            @Parameter(description = "The updated time", example = "{\"id\": 1, \"time\": {\"time\": \"2024-08-25\"}") @RequestBody Time time) {
        if (timeService.getTimeById(id) != null) {
            time.setId(id);
            timeService.saveTime(time);
            return ResponseEntity.ok(time);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a time", description = "Delete an existing time by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Time deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Time not found")
    })
    @DeleteMapping("/times/{id}")
    ResponseEntity<Void> deleteTime(@Parameter(description = "The ID of the time to be deleted", example = "1") @PathVariable Long id) {
        if (timeService.getTimeById(id) != null) {
            timeService.deleteTimeById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get list of schedules", description = "Retrieve all schedules in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List retrieved successfully", content = @Content(examples = @ExampleObject("[{\"id\":1,\"timeIds\":[1,4]},{\"id\":2,\"timeIds\":[1,2,3]}]"))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/schedules")
    ResponseEntity<Iterable<Schedule>> findSchedules() {
        Iterable<Schedule> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok(schedules);
    }

    @Operation(summary = "Create a new schedule", description = "Create a new schedule in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Schedule created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/schedules")
    ResponseEntity<Schedule> createSchedule(@Parameter(description = "The schedule object to be created.", example = "{\"timeIds\": [1, 2, 3]}") @RequestBody Schedule schedule) {
        if (schedule == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        scheduleService.saveSchedule(schedule);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Add time to a schedule", description = "Add an existing time to a specific schedule.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Time added to schedule successfully"),
            @ApiResponse(responseCode = "404", description = "Schedule not found"),
            @ApiResponse(responseCode = "409", description = "Time already exists in the schedule")
    })
    @PutMapping("/schedules/{scheduleId}/{timeId}")
    ResponseEntity<Schedule> addTimeToSchedule(
            @Parameter(description = "ID of the schedule to which the time will be added.", example = "1") @PathVariable Long scheduleId,
            @Parameter(description = "ID of the time to be added to the schedule.", example = "1") @PathVariable Long timeId) {
        Schedule existingSchedule = scheduleService.getScheduleById(scheduleId).get();
        if (existingSchedule == null || existingSchedule.getTimes() == null) {
            return ResponseEntity.notFound().build();
        }

        List<Time> updatedTimes = new ArrayList<>(existingSchedule.getTimes());
        if (!updatedTimes.contains(timeService.getTimeById(timeId).get())) {
            updatedTimes.add(timeService.getTimeById(timeId).get());
        }
        existingSchedule.setTimes(updatedTimes);
        scheduleService.saveSchedule(existingSchedule);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove time from a schedule", description = "Remove an existing time from a specific schedule.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Time removed from schedule successfully"),
            @ApiResponse(responseCode = "404", description = "Schedule or Time not found")
    })
    @DeleteMapping("/schedules/time/{scheduleId}/{timeId}")
    ResponseEntity<Void> removeTimeFromSchedule(
            @Parameter(description = "The ID of the schedule from which the time will be removed", example = "1") @PathVariable Long scheduleId,
            @Parameter(description = "The ID of the time to be removed from the schedule", example = "1") @PathVariable Long timeId) {
        Schedule existingSchedule = scheduleService.getScheduleById(scheduleId).get();
        if (existingSchedule == null || existingSchedule.getTimes() == null || !existingSchedule.getTimes().contains(timeService.getTimeById(timeId).get())) {
            return ResponseEntity.notFound().build();
        }

        List<Time> updatedTimes = new ArrayList<>(existingSchedule.getTimes());
        updatedTimes.remove(timeService.getTimeById(timeId).get());
        existingSchedule.setTimes(updatedTimes);
        scheduleService.saveSchedule(existingSchedule);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete an appointment", description = "Delete an existing appointment by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Appointment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @DeleteMapping("/schedules/{id}")
    ResponseEntity<Void> deleteSchedule(@Parameter(description = "The ID of the schedule to be deleted", example = "1") @PathVariable Long id) {
        if (scheduleService.getScheduleById(id) != null) {
            scheduleService.deleteScheduleById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Assign a schedule to a doctor", description = "Assign an existing schedule to a specific doctor by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schedule assigned to doctor successfully"),
            @ApiResponse(responseCode = "404", description = "Doctor or Schedule not found")
    })
    @PutMapping("/schedules/doctor/{scheduleId}/{doctorId}")
    ResponseEntity<Doctor> setDoctorSchedule(
            @Parameter(description = "The ID of the schedule to be assigned to the doctor", example = "1") @PathVariable Long scheduleId,
            @Parameter(description = "The ID of the doctor to whom the schedule will be assigned", example = "1") @PathVariable Long doctorId) {
        Schedule existingSchedule = scheduleService.getScheduleById(scheduleId).get();
        Doctor existingDoctor = doctorService.getDoctorById(doctorId).get();
        if (existingSchedule == null || existingDoctor == null) {
            return ResponseEntity.notFound().build();
        }
        existingDoctor.setSchedule(existingSchedule);
        doctorService.saveDoctor(existingDoctor);
        return ResponseEntity.ok(existingDoctor);
    }
}
