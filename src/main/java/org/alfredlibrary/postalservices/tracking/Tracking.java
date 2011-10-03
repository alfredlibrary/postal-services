/*
 * Alfred Library.
 * Copyright (C) 2011 Alfred Team
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.alfredlibrary.postalservices.tracking;

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
