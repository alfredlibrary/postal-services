package org.alfredlibrary.postalservices.internal.tracking;

import junit.framework.Assert;

import org.alfredlibrary.postalservices.tracking.Tracking;
import org.alfredlibrary.postalservices.tracking.TrackingFactory;
import org.alfredlibrary.postalservices.tracking.TrackingInfo;
import org.junit.Before;
import org.junit.Test;

public class USPSTest {

	private Tracking tracking;

	@Before
	public void before() {
		tracking = TrackingFactory.getUSPS("", false);
	}

	@Test
	public void testSuccessCode() {
		TrackingInfo info = tracking.track("");
		Assert.assertNotNull(info);
	}

}
