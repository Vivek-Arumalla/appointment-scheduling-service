package com.cognizant.appointmentschedulingservice;

import com.cognizant.appointmentschedulingservice.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.client.RestTemplate;


import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class AppointmentSchedulingServiceApplicationTests {
	String dcUrl         =     "http://localhost:8083/dc-service/api/v1/dc/";
	String dcSlotUrl     =     "http://localhost:8083/dcSlot-service/api/v1/dcslots/";
	String truckUrl      =     "http://localhost:8083/truck-service/api/v1/truck/";
	String vendorUrl     =     "http://localhost:8083/vendor-service/api/v1/vendor/";
	String appointmentUrl=	   "http://localhost:8083/appointment-scheduling-service/api/v1/appointmentscheduling";
	String publisherUrl	 =	   "http://localhost:8083/product-service/api/sender/v1";
	String consumerUrl	 =	   "http://localhost:8083/consumer-service/api/v1/podetails";


	RestTemplate template = new RestTemplate();

//	@Test
//	public void testGetAllAppointments(){
//		template.delete(appointmentUrl);
//		ResponseEntity<AppointmentScheduling[]> appointments = template.getForEntity(appointmentUrl,AppointmentScheduling[].class);
//		List<AppointmentScheduling> result = Arrays.asList(appointments.getBody());
//		assertThat(result).isEmpty();
//	}

	@Test
	@Rollback(false)
	public void testCreateAppointment(){
		deleteData();
		insertData();
		ResponseEntity<AppointmentScheduling> appointmentScheduling = template.postForEntity(appointmentUrl,appointmentSchedulingExampleData(),AppointmentScheduling.class);
		assertEquals(200,appointmentScheduling.getStatusCodeValue());
		assertNotNull(appointmentScheduling.getBody());

	}

//	@Test
//	public void testUpdateAppoinment(){
//		deleteData();
//		insertData();
//		ResponseEntity<AppointmentScheduling> appointmentScheduling = template.postForEntity(appointmentUrl,appointmentSchedulingExampleData(),AppointmentScheduling.class);
//		assertEquals(200,appointmentScheduling.getStatusCodeValue());
//		appointmentScheduling.getBody().setDateOfAppointment("09/11/2020");
//		template.put(appointmentUrl+"/"+appointmentScheduling.getBody().getAppointmentId(),appointmentScheduling,AppointmentScheduling.class);
//		Assertions.assertEquals("09/11/2020", template.getForObject(appointmentUrl+"/" +appointmentScheduling.getBody().getAppointmentId(),AppointmentScheduling.class).getDateOfAppointment());
//
//	}
//
//	@Test
//	@Rollback(false)
//	public void testDeleteAppointment() {
//		deleteData();
//		insertData();
//		ResponseEntity<AppointmentScheduling> appointmentScheduling = template.postForEntity(appointmentUrl,appointmentSchedulingExampleData(),AppointmentScheduling.class);
//		assertEquals(200,appointmentScheduling.getStatusCodeValue());
//		template.delete(appointmentUrl+"/"+appointmentScheduling.getBody().getAppointmentId());
//		assertThrows(HttpClientErrorException.NotFound.class, ()->{ template.getForObject(appointmentUrl+"/" +appointmentScheduling.getBody().getAppointmentId(),AppointmentScheduling.class);});
//	}

	public void deleteData(){
		template.delete(appointmentUrl);
		template.delete(vendorUrl);
		template.delete(truckUrl);
		template.delete(dcSlotUrl);
		template.delete(dcUrl);
		template.delete(consumerUrl);
	}

	public void insertData(){
		Dc dc = template.postForEntity(dcUrl,dcExampleData(),Dc.class).getBody();
		DcSlots dcSlots= template.postForEntity(dcSlotUrl,dcSlotsExampleData(), DcSlots.class).getBody();
		Truck truck= template.postForEntity(truckUrl,truckExampleData(), Truck.class).getBody();
		VendorDetails vendorDetails= template.postForEntity(vendorUrl,vendorExampleData(), VendorDetails.class).getBody();
		PODetails poDetails = template.postForEntity(publisherUrl,poDetailsExampleData(),PODetails.class).getBody();
	}




	Dc dcExampleData(){
		Dc dc = new Dc();
		dc.setDcNumber(101);
		dc.setDcCity("Banglore");
		dc.setDcType("Imports");
		return  dc;
	}
	DcSlots dcSlotsExampleData(){
		DcSlots dcSlots =  new DcSlots();
		dcSlots.setTimeSlot("6");
		dcSlots.setMaxTrucks(2);
		dcSlots.setDc(dcExampleData());
		return dcSlots;

	}
	Truck truckExampleData(){
		Truck truck = new Truck();
		truck.setTruckType("Refrigerated (Reefer) Trailers");
		truck.setTruckName("TATA");
		truck.setTruckNumber("AP 07 RT 7864");
		return truck;
	}
	VendorDetails vendorExampleData(){
		VendorDetails vendorDetails = new VendorDetails();
		vendorDetails.setVendorPhoneNumber("7896541230");
		vendorDetails.setVendorName("Vivek");
		vendorDetails.setVendorEmail("vivek@gmail.com");
		vendorDetails.setVendorAddress("Guntur,AndhraPradesh,522001");
		return vendorDetails;
	}
	PODetails poDetailsExampleData(){
		PODetails poDetails = new PODetails();
		poDetails.setPoNumber(1);
		poDetails.setQuantity(8);
		poDetails.setPoDate("08/11/2020");
		poDetails.setPoLineNumber(8);
		poDetails.setItemName("Clothes");
		poDetails.setPoAddress("Vijayawada");
		return poDetails;
	}

	AppointmentScheduling appointmentSchedulingExampleData(){
		AppointmentScheduling appointment = new AppointmentScheduling();
		appointment.setDateOfAppointment("05/11/2020");
		appointment.setDc1(dcExampleData());
		appointment.setDcSlots(dcSlotsExampleData());
		appointment.setVendorDetails(vendorExampleData());
		appointment.setTruck(truckExampleData());
		appointment.setPoDetails(poDetailsExampleData());
		return appointment;
	}




}
