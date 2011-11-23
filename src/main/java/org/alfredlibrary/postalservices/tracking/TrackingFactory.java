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

import org.alfredlibrary.postalservices.internal.tracking.CorreiosTracking;
import org.alfredlibrary.postalservices.internal.tracking.USPSTracking;

/**
 * Create instances of {@link Tracking}. <br>
 * If you don't use CDI, then use this factory to get instances of {@link Tracking}.<br>
 * If you're using CDI, then use the @Inject and our qualifiers (@USPS, @Correios and so on).
 * 
 * @author Marlon Silva Carvalho
 * @since 2.0.0
 */
final public class TrackingFactory {

	/**
	 * Get an instance of @{link Tracking} to work with USPS trackings.
	 * 
	 * @param userID Your USPS user identification.
	 * @param test Are you testing your application or using in production environment?
	 * @return Instance of {@link Tracking} to track packages sent from USPS.
	 */
	public static USPSTracking getUSPS(final String userID, final boolean test) {
		return new USPSTracking(userID, test);
	}

	/**
	 * Get an instance of @{link Tracking} to work with Brazilian Postal Services tracking.
	 * 
	 * @return Instance of @{link Tracking} to track packages sent from Correios. 
	 */
	public static CorreiosTracking getCorreios() {
		return new CorreiosTracking();
	}

}
