package com.cognizant.appointmentschedulingservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Data
@Getter
@Setter
@Entity
@Table(name = "dc_details")
public class Dc  {
    @Id
    @Column(name = "dc_number")
    @ApiModelProperty(hidden = true)
    int dcNumber;
    @Column(name = "dc_city", nullable = false)
    String dcCity;
    @Column(name="dc_type", nullable = false)
    @ApiModelProperty(hidden = true)
    String dcType;

    @JsonIgnore
    @OneToMany(mappedBy = "dc", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DcSlots> dcSlotsSet;

    @JsonIgnore
    @OneToMany(mappedBy = "dc1", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AppointmentScheduling> appointmentSchedulingSet;

    public Dc(){}
    public Dc(String dcCity,String dcType){
        this.dcCity = dcCity;
        this.dcType = dcType;
    }

    public int getDcNumber() {
        return dcNumber;
    }

    public void setDcNumber(int dcNumber) {
        this.dcNumber = dcNumber;
    }

    public String getDcCity() {
        return dcCity;
    }

    public void setDcCity(String dcCity) {
        this.dcCity = dcCity;
    }

    public String getDcType() {
        return dcType;
    }

    public void setDcType(String dcType) {
        this.dcType = dcType;
    }
}
