package com.cognizant.appointmentschedulingservice.model;



import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "po_details")
public class PODetails {

    @Id
    @Column(name = "po_number",unique = true,nullable = false)
    int poNumber;
    @Column(name= "po_date",nullable = false)
    @ApiModelProperty(hidden = true)
    String poDate;
    @Column(name="po_address",nullable = false)
    @ApiModelProperty(hidden = true)
    String poAddress;
    @Column(name = "po_line_number",nullable = false)
    @ApiModelProperty(hidden = true)
    int poLineNumber;
    @Column(name = "item_name",nullable = false)
    @ApiModelProperty(hidden = true)
    String itemName;
    @Column(name="quantity",nullable = false)
    @ApiModelProperty(hidden = true)
    int quantity;

    @JsonIgnore
    @OneToMany(mappedBy = "poDetails", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<AppointmentScheduling> appointmentSchedulingSet;


}
