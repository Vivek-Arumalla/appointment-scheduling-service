package com.cognizant.appointmentschedulingservice.controller;

import com.cognizant.appointmentschedulingservice.model.AppointmentScheduling;
import com.cognizant.appointmentschedulingservice.service.AppointmentSchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointmentscheduling")
public class AppointmentSchedulingController {
    @Autowired
    AppointmentSchedulingService appointmentSchedulingService;

    @GetMapping
    public ResponseEntity<List<AppointmentScheduling>> getAllAppointments(){
        return ResponseEntity.ok(appointmentSchedulingService.getAllAppointments());
    }

    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentScheduling appointmentScheduling){
        return ResponseEntity.ok(appointmentSchedulingService.createAppointment(appointmentScheduling));
    }

    @GetMapping("/{appoinmentId}")
    public ResponseEntity<?> getByAppoinmentId(@PathVariable(name = "appoinmentId") int appoinmentId){
        return ResponseEntity.ok(appointmentSchedulingService.getAppointmentById(appoinmentId));
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<?> deleteAppoinment(@PathVariable(value="appointmentId") int appointmentId) {
        appointmentSchedulingService.deleteAppointment(appointmentId);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{appointmentId}")
    public ResponseEntity<?> updateAppointment(@RequestBody AppointmentScheduling appointmentScheduling,@PathVariable(value = "appointmentId") int appointmentId){
        return ResponseEntity.ok(appointmentSchedulingService.updateAppointment(appointmentScheduling,appointmentId));
    }
    @ApiIgnore
    @DeleteMapping
    public void deleteAll(){
        appointmentSchedulingService.deleteAll();
    }

}
