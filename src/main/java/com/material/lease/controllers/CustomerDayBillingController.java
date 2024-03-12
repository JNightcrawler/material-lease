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

import com.material.lease.model.CustomerDayBilling;
import com.material.lease.repository.CustomerDayBillingRepository;


@RestController
@RequestMapping("api/customer_billing")
public class CustomerDayBillingController {
	
	@Autowired
	private CustomerDayBillingRepository customerDayBillingRepo;
	
	@GetMapping("/all")
	public ResponseEntity<List<CustomerDayBilling>> getAllCustomerDayBilling(@RequestParam(required = false) String phoneNo){
		
		List<CustomerDayBilling> customerDayBillings = new ArrayList<CustomerDayBilling>();
		
		try {
			if(phoneNo == null) 
				customerDayBillingRepo.findAll().forEach(customerDayBillings::add);				
			else 
				customerDayBillingRepo.findByPhoneNoContaining(phoneNo).forEach(customerDayBillings::add);
			
			if(customerDayBillings.isEmpty())
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			
			return new ResponseEntity<List<CustomerDayBilling>>(customerDayBillings, HttpStatus.OK);
			
		}catch(Exception e) {
			//Loggers needed
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CustomerDayBilling> getAllCustomerDayBillingById(@PathVariable("id") long id){
		try {
			Optional<CustomerDayBilling> customerDayBilling = customerDayBillingRepo.findById(id);
			
			if(customerDayBilling.isPresent()) {
				return new ResponseEntity<CustomerDayBilling>(customerDayBilling.get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			//Need to log the error
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<CustomerDayBilling> createCustomerDayBilling(@RequestBody CustomerDayBilling customerDayBilling){
		try {
			return new ResponseEntity<CustomerDayBilling>(customerDayBillingRepo.save(customerDayBilling), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<CustomerDayBilling> updateCustomerDayBillingById(@PathVariable("id") long id, 
																		@RequestBody CustomerDayBilling customerDayBilling){
		
		Optional<CustomerDayBilling> retrivedCustomerDayBilling = customerDayBillingRepo.findById(id);
		try {
			if(retrivedCustomerDayBilling.isPresent()) {
				CustomerDayBilling updatedCustomerDayBilling = retrivedCustomerDayBilling.get();
				updatedCustomerDayBilling.setPhoneNo(customerDayBilling.getPhoneNo());
				updatedCustomerDayBilling.setAmount(customerDayBilling.getAmount());
				updatedCustomerDayBilling.setItemsCount(customerDayBilling.getItemsCount());
				updatedCustomerDayBilling.setDate(customerDayBilling.getDate());
				updatedCustomerDayBilling.setMaterialName(customerDayBilling.getMaterialName());
				updatedCustomerDayBilling.setSite(customerDayBilling.getSite());
					
				return new ResponseEntity<CustomerDayBilling>(updatedCustomerDayBilling, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpStatus> deleteCustomerDayBillingById(@PathVariable("id") long id){
		try {
			customerDayBillingRepo.deleteById(id);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/delete/all")
	public ResponseEntity<HttpStatus> deleteAllCustomerDayBilling(){
		try {
			customerDayBillingRepo.deleteAll();
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
