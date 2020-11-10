package com.cognizant.appointmentschedulingservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vendor_details")
public class VendorDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id")
    @ApiModelProperty(hidden = true)
    int vendorId;
    @Column(name = "vendor_name")
    String vendorName;
    @Column(name = "vendor_email", nullable = false,unique = true)
    String vendorEmail;
    @ApiModelProperty(hidden = true)
    @Column(name = "vendor_phone_number")
    String vendorPhoneNumber;
    @ApiModelProperty(hidden = true)
    @Column(name = "vendor_address")
    String vendorAddress;
    @JsonIgnore
    @OneToMany(mappedBy = "vendorDetails", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AppointmentScheduling> appointmentSchedulingSet;


}
