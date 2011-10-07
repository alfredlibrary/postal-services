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

import javax.enterprise.inject.Alternative;

import org.alfredlibrary.network.WWW;
import org.alfredlibrary.postalservices.tracking.Status;
import org.alfredlibrary.postalservices.tracking.Tracking;
import org.alfredlibrary.postalservices.tracking.TrackingInfo;
import org.alfredlibrary.postalservices.tracking.TrackingNotFoundException;
import org.alfredlibrary.postalservices.tracking.annotation.USPS;

/**
 * Default implementation to get tracking informations of packages coming from
 * United States Postal Service.<br>
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
	private String url = "http://testing.shippingapis.com/ShippingAPITest.dll";
	private String userID;

	public USPSTracking() {
	}

	public USPSTracking(String userID) {
		this.setUserID(userID);
	}

	@Override
	public TrackingInfo track(String code) {
		String mountedUrl = url + "?API=TrackV2&XML=%3CTrackRequest%20USERID=%22" + getUserID() + "%22%3E%3CTrackID%20ID=%" + code
				+ "%22%3E%3C/TrackID%3E%3C/TrackRequest%3E";
		String content = WWW.getContent(mountedUrl, "UTF-8");

		if (content.indexOf("<Error>") > -1) {
			int index1 = content.indexOf("<Description>") + 13;
			int index2 = content.indexOf("</Description>");
			throw new TrackingNotFoundException(content.substring(index1, index2 - index1));
		}

		TrackingInfo info = new TrackingInfo();

		int index1 = 0;
		int index2 = 0;

		if (content.contains("<TrackSummary>")) {
			index1 = content.indexOf("<TrackSummary>") + 14;
			index2 = content.indexOf("</TrackSummary>");
			info.setSummary(content.substring(index1, index2 - index1));
		}

		int lastIndex = 0;
		while (content.indexOf("<TrackDetail>", lastIndex) > -1) {
			index1 = content.indexOf("<TrackDetail>", lastIndex) + 13;
			index2 = content.indexOf("</TrackDetail>", lastIndex + 13);

			Status status = new Status();
			status.setDescription(content.substring(index1, index2 - index1));
			info.getStatuses().add(status);
			lastIndex = index2;
		}

		return info;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserID() {
		return userID;
	}

}