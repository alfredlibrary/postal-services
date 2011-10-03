package org.alfredlibrary.postalservices;

import java.io.Serializable;
import java.util.List;

/**
 * Defines the common behavior to get tracking informations.
 * 
 * @author Marlon Silva Carvalho
 * @since 2.0.0
 */
public interface Tracking extends Serializable {

	/**
	 * Get tracking statuses.
	 * 
	 * @param code Tracking code.
	 * @return List of tracking statuses.
	 */
	List<Status> track(String code);

}
