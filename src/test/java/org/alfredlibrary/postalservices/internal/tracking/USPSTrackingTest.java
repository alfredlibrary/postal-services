package org.alfredlibrary.postalservices.internal.tracking;

import org.alfredlibrary.postalservices.tracking.TrackingFactory;
import org.alfredlibrary.postalservices.tracking.TrackingServices;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for USPSTracking.
 * 
 * @author Marlon
 * @since 2.0.0
 */
public class USPSTrackingTest {

	private USPSTracking tracking;

	@Before
	public void before() {
		tracking = (USPSTracking) TrackingFactory.getInstance(TrackingServices.USPS, "146SERPR6292");
	}

	@Test
	public void success() {
		tracking.track("EJ958088694US");
	}

}
