package com.material.lease.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.material.lease.model.CustomerActivity;
import com.material.lease.repository.CustomerActivityRepository;

@RestController
@RequestMapping("api/customer_activity")
public class CustomerActivityController {
	
	@Autowired
	private CustomerActivityRepository customerActivityRepo;
	
	@GetMapping("/all")
	public ResponseEntity<List<CustomerActivity>> getAllCustomerActivity(@RequestParam(required = false) String site){
		
		List<CustomerActivity> customerActivities = new ArrayList<CustomerActivity>();
		
		try {
			if(site == null) 
				customerActivityRepo.findAll().forEach(customerActivities::add);				
			else 
				customerActivityRepo.findBySiteContaining(site).forEach(customerActivities::add);
			
			if(customerActivities.isEmpty())
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			
			return new ResponseEntity<List<CustomerActivity>>(customerActivities, HttpStatus.OK);
			
		}catch(Exception e) {
			//Loggers needed
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CustomerActivity> getCustomerActivityById(@PathVariable("id") long id){
		try {
			Optional<CustomerActivity> customerActivity = customerActivityRepo.findById(id);
			
			if(customerActivity.isPresent()) {
				return new ResponseEntity<CustomerActivity>(customerActivity.get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			//Need to log the error
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<CustomerActivity> createCustomerActivity(@RequestBody CustomerActivity customerActivity){
		try {
			return new ResponseEntity<CustomerActivity>(customerActivityRepo.save(customerActivity), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<CustomerActivity> updateCustomerActivityById(@PathVariable("id") long id, 
																		@RequestBody CustomerActivity customerActivity){
		
		Optional<CustomerActivity> retrivedCustomerActivity = customerActivityRepo.findById(id);
		try {
			if(retrivedCustomerActivity.isPresent()) {
				CustomerActivity updatedCustomerActivity = retrivedCustomerActivity.get();
				updatedCustomerActivity.setSite(customerActivity.getSite());
				updatedCustomerActivity.setMateialQuantity(customerActivity.getMateialQuantity());
				updatedCustomerActivity.setShipment(customerActivity.getShipment());
				updatedCustomerActivity.setMaterialName(customerActivity.getMaterialName());
				updatedCustomerActivity.setFromDate(customerActivity.getFromDate());
				updatedCustomerActivity.setToDate(customerActivity.getToDate());
				customerActivityRepo.save(updatedCustomerActivity);
				return new ResponseEntity<CustomerActivity>(updatedCustomerActivity, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpStatus> deleteCustomerActivityById(@PathVariable("id") long id){
		try {
			customerActivityRepo.deleteById(id);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/delete/all")
	public ResponseEntity<HttpStatus> deleteAllCustomerActivity(){
		try {
			customerActivityRepo.deleteAll();
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
