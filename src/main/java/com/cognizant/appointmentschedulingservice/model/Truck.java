package com.cognizant.appointmentschedulingservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "truck_details")
public class Truck  {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "truck_id")
    @ApiModelProperty(hidden = true)
    int truckId;
    @Column(name = "truck_number",nullable = false,unique = true)
    String truckNumber;
    @ApiModelProperty(hidden = true)
    @Column(name = "truck_name")
    String truckName;
    @ApiModelProperty(hidden = true)
    @Column(name = "truck_type")
    String truckType;

    @JsonIgnore
    @OneToMany(mappedBy = "truck", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AppointmentScheduling> appointmentSchedulingSet;

    public int getTruckId() {
        return truckId;
    }

    public void setTruckId(int truckId) {
        this.truckId = truckId;
    }

    public String getTruckNumber() {
        return truckNumber;
    }

    public void setTruckNumber(String truckNumber) {
        this.truckNumber = truckNumber;
    }

    public String getTruckName() {
        return truckName;
    }

    public void setTruckName(String truckName) {
        this.truckName = truckName;
    }

    public String getTruckType() {
        return truckType;
    }

    public void setTruckType(String truckType) {
        this.truckType = truckType;
    }
}
