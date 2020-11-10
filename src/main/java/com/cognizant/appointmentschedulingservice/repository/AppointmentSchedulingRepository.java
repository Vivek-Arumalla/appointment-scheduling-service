package com.cognizant.appointmentschedulingservice.repository;

import com.cognizant.appointmentschedulingservice.model.AppointmentScheduling;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentSchedulingRepository extends CrudRepository<AppointmentScheduling,Integer> {

    @Query(value = "select * from appointment_scheduling where truck_number=?1",nativeQuery = true)
    List<AppointmentScheduling> findByTruckNumber(int truckId);
    @Query(value = "select count(*) from appointment_scheduling where dc_slot=?1 and appointment_date = ?2",nativeQuery = true)
    int countOfTrucks(int dcSlotId, String appointmentDate);
    @Query(value = "select count(*) from appointment_scheduling where truck_number=?1 and appointment_date = ?2 and dc_slot = ?3",nativeQuery = true)
    int truckAlreadyAllocated(int truckId,String appointmentDate,int dcSlotId);
}
