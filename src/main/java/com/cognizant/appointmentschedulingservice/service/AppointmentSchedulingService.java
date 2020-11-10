package com.cognizant.appointmentschedulingservice.service;

import com.cognizant.appointmentschedulingservice.exception.*;
import com.cognizant.appointmentschedulingservice.model.*;
import com.cognizant.appointmentschedulingservice.repository.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AppointmentSchedulingService {

    String dcUrl        =     "http://localhost:8083/dc-service/api/v1/dc/";
    String dcSlotUrl    =     "http://localhost:8083/dcSlot-service/api/v1/dcslots/";
    String truckUrl     =     "http://localhost:8083/truck-service/api/v1/truck/";
    String vendorUrl    =     "http://localhost:8083/vendor-service/api/v1/vendor/";
    String poUrl        =     "http://localhost:8083/consumer-service/api/v1/podetails/";

    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    AppointmentSchedulingRepository appointmentSchedulingRepository;

    @Autowired
    private MessageChannel output;

    List<AppointmentScheduling> appointmentSchedulingList = new ArrayList<AppointmentScheduling>();

    public List<AppointmentScheduling> getAllAppointments(){
        return (List<AppointmentScheduling>) appointmentSchedulingRepository.findAll();
    }

    public AppointmentScheduling createAppointment(AppointmentScheduling appointmentScheduling){
        appointmentScheduling.setPoDetails(getPOById(appointmentScheduling.getPoDetails().getPoNumber()));
        appointmentScheduling.setDc1(getDcByCityName(appointmentScheduling.getDc1().getDcCity()));
        DcSlots dcSlot = getDcSlotsByTime(timeSlot(appointmentScheduling.getDcSlots().getTimeSlot()),appointmentScheduling.getDc1().getDcNumber());
        appointmentScheduling.setDcSlots(dcSlot);
        int maxTrucks = dcSlot.getMaxTrucks();
        if(appointmentSchedulingRepository.countOfTrucks(dcSlot.getDcSlotNumber(),appointmentScheduling.getDateOfAppointment())== maxTrucks){
            throw new DcSlotUnavailableException();
        }
        appointmentScheduling.setTruck(getTruckByTruckNumber(appointmentScheduling.getTruck().getTruckNumber()));
        appointmentScheduling.setVendorDetails(getVendorByName(appointmentScheduling.getVendorDetails().getVendorName()));
        if(appointmentSchedulingRepository.truckAlreadyAllocated(appointmentScheduling.getTruck().getTruckId(),appointmentScheduling.getDateOfAppointment(),dcSlot.getDcSlotNumber())==0){
            AppointmentScheduling data = appointmentSchedulingRepository.save(appointmentScheduling);
           Message message = MessageBuilder.withPayload(appointmentSchedulingRepository.findAll()).build();
           output.send(message);
           return data;
        }
        throw new TruckAlreadyAllocatedException();
    }

    public String updateAppointment(AppointmentScheduling appointmentScheduling,int appointmentId){

        if(appointmentSchedulingRepository.findById(appointmentId).isPresent()) {
            appointmentScheduling.setPoDetails(getPOById(appointmentScheduling.getPoDetails().getPoNumber()));
            appointmentScheduling.setDc1(getDcByCityName(appointmentScheduling.getDc1().getDcCity()));

            DcSlots dcSlot = getDcSlotsByTime(appointmentScheduling.getDcSlots().getTimeSlot(), appointmentScheduling.getDc1().getDcNumber());
            appointmentScheduling.setDcSlots(dcSlot);
            int maxTrucks = dcSlot.getMaxTrucks();
            if (appointmentSchedulingRepository.countOfTrucks(dcSlot.getDcSlotNumber(), appointmentScheduling.getDateOfAppointment()) == maxTrucks) {
                throw new DcSlotUnavailableException();
            }
            appointmentScheduling.setTruck(getTruckByTruckNumber(appointmentScheduling.getTruck().getTruckNumber()));
            appointmentScheduling.setVendorDetails(getVendorByName(appointmentScheduling.getVendorDetails().getVendorName()));
            if (appointmentSchedulingRepository.truckAlreadyAllocated(appointmentScheduling.getTruck().getTruckId(), appointmentScheduling.getDateOfAppointment(), dcSlot.getDcSlotNumber()) == 0) {
                appointmentSchedulingRepository.save(appointmentScheduling);
                Message message = MessageBuilder.withPayload(appointmentSchedulingRepository.findAll()).build();
                output.send(message);
                return "Appointment update Success......";
            }
            throw new TruckAlreadyAllocatedException();
        }
        throw new AppointmentNotFoundException();

    }

    public List<AppointmentScheduling> getAppointmentsByTruckNumber(String truckNumber) {
        if (getTruckByTruckNumber(truckNumber) == null)
            throw  new TruckNotFoundException();
        else {
            appointmentSchedulingList = appointmentSchedulingRepository.findByTruckNumber(getTruckByTruckNumber(truckNumber).getTruckId());
            return appointmentSchedulingList;
        }
    }

    public void deleteAppointment(int appointmentId){
        if(appointmentSchedulingRepository.findById(appointmentId).isPresent()){
            appointmentSchedulingRepository.deleteById(appointmentId);
        }
        else
            throw new AppointmentNotFoundException();


    }

    public AppointmentScheduling getByAppointmentId(int appointmentId){

        if(appointmentSchedulingRepository.findById(appointmentId).isPresent())
            return appointmentSchedulingRepository.findById(appointmentId).get();
        else
            throw new AppointmentNotFoundException();

    }


    public Dc getDcByCityName(String dcCityName)
    {
        String dcCityNameUrl = dcUrl +"name/" + dcCityName;
        try{
            return restTemplate.getForObject(dcCityNameUrl, Dc.class);
        }
        catch(Exception e){
            throw new DcNotFoundException();
        }
    }

    public DcSlots getDcSlotsByTime(String dcSlotTime, int dcNumber){
        String dcSlotsByTimeUrl = dcSlotUrl +"dcId/"+ dcNumber;
        try{
            ResponseEntity<DcSlots[]> responseEntity = restTemplate.getForEntity(dcSlotsByTimeUrl, DcSlots[].class);
            List<DcSlots> resultDcSlots = Arrays.asList(responseEntity.getBody());
            for(DcSlots dcSlot : resultDcSlots) {
               if (dcSlot.getTimeSlot().equalsIgnoreCase(dcSlotTime)){

                    return dcSlot;
                }
            }
            throw new DcSlotNotFoundException();
        }
        catch (Exception e){
            throw new DcSlotNotFoundException();
        }
    }

    public Truck getTruckByTruckNumber(String truckNumber){
        String truckByNumberUrl = truckUrl + truckNumber;
        Truck truck = restTemplate.getForObject(truckByNumberUrl, Truck.class);
        if(truck != null)
            return truck;
        else
            throw new TruckNotFoundException();
    }

    public VendorDetails getVendorByName(String vendorName){
        String vendorByNameUrl = vendorUrl + vendorName;
        VendorDetails vendorDetails = restTemplate.getForObject(vendorByNameUrl, VendorDetails.class);
        System.out.println(vendorDetails.toString());
        if(vendorDetails != null)
            return vendorDetails;
        else
            return null;
    }

    public PODetails getPOById(int poId){
        String poByIdUrl = poUrl + poId;
        PODetails poDetails = restTemplate.getForObject(poByIdUrl, PODetails.class);
        if(poDetails != null)
            return poDetails;
        else
            return null;
    }

    public void deleteAll(){
        appointmentSchedulingRepository.deleteAll();
    }


    public AppointmentScheduling getAppointmentById(int appoinmentId) {
        if(appointmentSchedulingRepository.findById(appoinmentId).isPresent())
            return appointmentSchedulingRepository.findById(appoinmentId).get();
        throw  new AppointmentNotFoundException();
    }

    public String timeSlot(String timeSlot){
        int startTime = Integer.parseInt(timeSlot);
        int endTime=0;
        if(startTime>23) {
            if(startTime==24) {
                startTime=0;
            }
        }
        endTime = startTime+1;
        return String.format("%d.00 to %d.00",startTime,endTime);

    }
}
