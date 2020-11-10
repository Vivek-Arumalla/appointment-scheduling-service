package com.cognizant.appointmentschedulingservice.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "appointment_scheduling")
public class AppointmentScheduling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    @ApiModelProperty(hidden = true)
    int appointmentId;
    @Column(name = "appointment_date")
    String dateOfAppointment;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "dc_number",nullable = false)
    private Dc dc1;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "truck_number",nullable = false)
    private Truck truck;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "dc_slot",nullable = false)
    private DcSlots dcSlots;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "po_number", nullable = false)
    private PODetails poDetails;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "vendor_id",nullable = false)
    private VendorDetails vendorDetails;


}
