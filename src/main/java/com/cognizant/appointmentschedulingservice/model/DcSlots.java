package com.cognizant.appointmentschedulingservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "dc_slots")
public class DcSlots {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dc_slot_number")
    @ApiModelProperty(hidden = true)
    int dcSlotNumber;
    @Column(name = "time_slot",nullable = false)
    String timeSlot;
    @Column(name = "max_trucks", nullable = false)
    @ApiModelProperty(hidden = true)
    int maxTrucks;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "dc_number",nullable = false)
    private Dc dc;

    @JsonIgnore
    @OneToMany(mappedBy = "dcSlots", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AppointmentScheduling> appointmentSchedulingSet;

    public DcSlots(){}
    public DcSlots( String timeSlot, int maxTrucks, Dc dc){
        this.dc=dc;
        this.maxTrucks=maxTrucks;
        this.timeSlot=timeSlot;
    }

    public int getDcSlotNumber() {
        return dcSlotNumber;
    }

    public Dc getDc() {
        return dc;
    }

    public void setDc(Dc dc) {
        this.dc = dc;
    }

    public void setDcSlotNumber(int dcSlotNumber) {
        this.dcSlotNumber = dcSlotNumber;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public int getMaxTrucks() {
        return maxTrucks;
    }

    public void setMaxTrucks(int maxTrucks) {
        this.maxTrucks = maxTrucks;
    }
}
