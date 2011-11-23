package org.alfredlibrary.postalservices.internal.tracking;

import org.alfredlibrary.postalservices.tracking.TrackingFactory;
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
		tracking = (USPSTracking) TrackingFactory.getUSPS("146SERPR6292", true);
	}

	@Test
	public void success() {
		tracking.track("EJ958088694US");
	}

}
