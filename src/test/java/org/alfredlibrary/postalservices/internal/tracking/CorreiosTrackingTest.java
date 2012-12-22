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

import org.alfredlibrary.postalservices.tracking.Tracking;
import org.alfredlibrary.postalservices.tracking.TrackingFactory;
import org.alfredlibrary.postalservices.tracking.TrackingInfo;
import org.alfredlibrary.postalservices.tracking.exception.IncorrectTrackingCodeException;
import org.alfredlibrary.postalservices.tracking.exception.NullOrEmptyTrackingCodeException;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing Correios Tracking implementation.
 * 
 * @author Marlon Silva Carvalho
 * @since 2.0.0
 */
public class CorreiosTrackingTest {

	private Tracking tracking;
	
	@Before
	public void before() {
		tracking = TrackingFactory.getCorreios();
	}
	
	@Test(expected = IncorrectTrackingCodeException.class)
	public void testIncorrectCode() {
		tracking.track("PB4");
	}

	@Test(expected = NullOrEmptyTrackingCodeException.class)
	public void testFailWithNullCode() {
		tracking.track(null);
	}

	@Test(expected = NullOrEmptyTrackingCodeException.class)
	public void testFailWithEmptyCode() {
		tracking.track("");
	}

	@Test
	public void testSuccessCode() {
		TrackingInfo info = tracking.track("pg185440520br");
		Assert.assertNotNull(info);
	}

}