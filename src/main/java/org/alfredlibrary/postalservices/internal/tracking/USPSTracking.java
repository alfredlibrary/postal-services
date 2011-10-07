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
package org.alfredlibrary.postalservices.internal.tracking;

import java.util.List;

import javax.enterprise.inject.Alternative;

import org.alfredlibrary.postalservices.tracking.Status;
import org.alfredlibrary.postalservices.tracking.Tracking;
import org.alfredlibrary.postalservices.tracking.annotation.USPS;

/**
 * Default implementation to get tracking informations of packages coming from
 * United States.<br>
 * 
 * This implementation uses the Webtools API from USPS.
 * 
 * @author Marlon Silva Carvalho
 * @since 2.0.0
 */
@Alternative
@USPS
public class USPSTracking implements Tracking {
	private static final long serialVersionUID = 1L;

	@Override
	public List<Status> track(String code) {
		return null;
	}

}