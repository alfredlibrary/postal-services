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

	public static Tracking getInstance(TrackingServices services, String... params) {
		Tracking tracking = null;

		if (services == TrackingServices.CORREIOS) {
			tracking = new CorreiosTracking();
		} else if (services == TrackingServices.USPS) {
			tracking = new USPSTracking(params[0]);
		}

		return tracking;
	}

}
