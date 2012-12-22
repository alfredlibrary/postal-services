package org.alfredlibrary.postalservices.internal.tracking;

import junit.framework.Assert;

import org.alfredlibrary.postalservices.tracking.TrackingFactory;
import org.alfredlibrary.postalservices.tracking.TrackingInfo;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for USPSTracking.
 * 
 * @author Marlon Silva Carvalho
 * @since 2.0.0
 */
public class USPSTrackingTest {

	private USPSTracking tracking;

	@Before
	public void before() {
		tracking = (USPSTracking) TrackingFactory.getUSPS("", true);
	}

	@Test
	public void success() {
		TrackingInfo info = tracking.track("EJ958088694US");
		Assert.assertNotNull(info);
	}

}
