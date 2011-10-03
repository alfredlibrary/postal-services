package org.alfredlibrary.postalservices;

import java.util.Date;

/**
 * Tracking single status.
 * 
 * @author Marlon Silva Carvalho
 * @since 2.0.0
 */
public class Status {
	private Date date;
	private String location;
	private String details;
	private String description;

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getDetails() {
		return details;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
