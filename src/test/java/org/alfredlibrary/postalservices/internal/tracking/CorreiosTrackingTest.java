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

import junit.framework.Assert;

import org.alfredlibrary.postalservices.tracking.IncorrectTrackingCodeException;
import org.alfredlibrary.postalservices.tracking.NullOrEmptyTrackingCodeException;
import org.alfredlibrary.postalservices.tracking.Tracking;
import org.alfredlibrary.postalservices.tracking.TrackingFactory;
import org.alfredlibrary.postalservices.tracking.TrackingInfo;
import org.alfredlibrary.postalservices.tracking.TrackingServices;
import org.junit.Test;

/**
 * Testing Correios Tracking implementation.
 * 
 * @author Marlon Silva Carvalho
 * @since 2.0.0
 */
public class CorreiosTrackingTest {

	@Test(expected = IncorrectTrackingCodeException.class)
	public void testIncorrectCode() {
		Tracking tracking = TrackingFactory.getInstance(TrackingServices.CORREIOS);
		tracking.track("PB4");
	}

	@Test(expected = NullOrEmptyTrackingCodeException.class)
	public void testFailWithNullCode() {
		Tracking tracking = TrackingFactory.getInstance(TrackingServices.CORREIOS);
		tracking.track(null);
	}

	@Test(expected = NullOrEmptyTrackingCodeException.class)
	public void testFailWithEmptyCode() {
		Tracking tracking = TrackingFactory.getInstance(TrackingServices.CORREIOS);
		tracking.track("");
	}

	@Test
	public void testSuccessCode() {
		Tracking tracking = TrackingFactory.getInstance(TrackingServices.CORREIOS);
		TrackingInfo info = tracking.track("RM050887654IN");
		Assert.assertEquals("AC CONCHAS - CONCHAS/SP", info.getStatuses().get(0).getCity());
		Assert.assertEquals("Entregue", info.getStatuses().get(0).getDescription());
	}

}