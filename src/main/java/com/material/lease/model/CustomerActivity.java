package com.material.lease.model;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer activities")
public class CustomerActivity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String site;
	
	private String shipment;
	
	private LocalDateTime fromDate;
	
	private LocalDateTime toDate;
	
	private String materialName;
	
	private int mateialQuantity;

	public CustomerActivity(String site, String shipment, LocalDateTime fromDate, LocalDateTime toDate,
			String materialName, int mateialQuantity) {
		this.site = site;
		this.shipment = shipment;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.materialName = materialName;
		this.mateialQuantity = mateialQuantity;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getShipment() {
		return shipment;
	}

	public void setShipment(String shipment) {
		this.shipment = shipment;
	}

	public LocalDateTime getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDateTime fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDateTime getToDate() {
		return toDate;
	}

	public void setToDate(LocalDateTime toDate) {
		this.toDate = toDate;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public int getMateialQuantity() {
		return mateialQuantity;
	}

	public void setMateialQuantity(int mateialQuantity) {
		this.mateialQuantity = mateialQuantity;
	}
	
	
	
}
