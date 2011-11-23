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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.alfredlibrary.network.WWW;
import org.alfredlibrary.postalservices.tracking.Status;
import org.alfredlibrary.postalservices.tracking.Tracking;
import org.alfredlibrary.postalservices.tracking.TrackingInfo;
import org.alfredlibrary.postalservices.tracking.TrackingNotFoundException;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * Default implementation to get tracking informations of packages coming from
 * United States Postal Service.<br>
 * 
 * This implementation uses the Webtools API from USPS.
 * 
 * @author Marlon Silva Carvalho
 * @since 2.0.0
 */
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
		String mountedUrl = "";
		try {
			mountedUrl = url + "?API=TrackV2&XML="
					+ URLEncoder.encode("<TrackFieldRequest USERID='" + userID + "'><TrackID ID='" + code + "'></TrackID></TrackFieldRequest>", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new TrackingNotFoundException();
		}
		String content = WWW.getContent(mountedUrl, "UTF-8");
		if (content.indexOf("<Error>") > -1) {
			int index1 = content.indexOf("<Description>") + 13;
			int index2 = content.indexOf("</Description>");
			throw new TrackingNotFoundException(content.substring(index1, index2 - index1));
		}

		TrackingInfo info = new TrackingInfo();
		Serializer serializer = new Persister();
		try {
			TrackResponse trackingResponse = serializer.read(TrackResponse.class, content);
			for (TrackDetail detail : trackingResponse.getTrackInfo().getTrackDetails()) {
				Status status = new Status();
				status.setDescription(detail.getEvent());
				status.setCity(detail.getEventCity());
				status.setState(detail.getEventState());
				status.setCountry(detail.getEventCountry());
				status.setZipCode(detail.getEventZIPCode());
				status.setDetails("");

				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm aa", Locale.US);
				status.setDate(simpleDateFormat.parse(detail.getEventDate() + " " + detail.getEventTime()));
			}
		} catch (Exception e) {
			throw new TrackingNotFoundException();
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

/**
 * 
 * 
 * @author Marlon
 * @since 2.0.0
 */
@Root(name = "TrackResponse")
class TrackResponse {

	@Element(name = "TrackInfo")
	private TrackInfo trackInfo;

	public TrackInfo getTrackInfo() {
		return trackInfo;
	}

	public void setTrackInfo(TrackInfo trackInfo) {
		this.trackInfo = trackInfo;
	}

}

/**
 * 
 * 
 * @author Marlon
 * @since 2.0.0
 */
@Root(name = "TrackDetail")
class TrackDetail extends TrackSummary {
}

/**
 * 
 * 
 * @author Marlon
 * @since 2.0.0
 */
class TrackSummary {

	@Element(name = "EventTime", required = false)
	private String eventTime;

	@Element(name = "EventDate", required = false)
	private String eventDate;

	@Element(name = "Event", required = false)
	private String event;

	@Element(name = "EventCity", required = false)
	private String eventCity;

	@Element(name = "EventState", required = false)
	private String eventState;

	@Element(name = "EventZIPCode", required = false)
	private String eventZIPCode;

	@Element(name = "EventCountry", required = false)
	private String eventCountry;

	@Element(name = "FirmName", required = false)
	private String firmName;

	@Element(name = "Name", required = false)
	private String name;

	@Element(name = "AuthorizedAgent", required = false)
	private String authorizedAgent;

	public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEventCity() {
		return eventCity;
	}

	public void setEventCity(String eventCity) {
		this.eventCity = eventCity;
	}

	public String getEventState() {
		return eventState;
	}

	public void setEventState(String eventState) {
		this.eventState = eventState;
	}

	public String getEventZIPCode() {
		return eventZIPCode;
	}

	public void setEventZIPCode(String eventZIPCode) {
		this.eventZIPCode = eventZIPCode;
	}

	public String getEventCountry() {
		return eventCountry;
	}

	public void setEventCountry(String eventCountry) {
		this.eventCountry = eventCountry;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthorizedAgent() {
		return authorizedAgent;
	}

	public void setAuthorizedAgent(String authorizedAgent) {
		this.authorizedAgent = authorizedAgent;
	}
}

/**
 * 
 * 
 * @author Marlon
 * @since 2.0.0
 */
class TrackInfo {

	@Attribute
	private String ID;

	@Element(name = "TrackSummary")
	private TrackSummary trackSummary;

	@ElementList(inline = true, type = TrackDetail.class, name = "TrackDetail")
	private List<TrackDetail> trackDetails;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public TrackSummary getTrackSummary() {
		return trackSummary;
	}

	public void setTrackSummary(TrackSummary trackSummary) {
		this.trackSummary = trackSummary;
	}

	public List<TrackDetail> getTrackDetails() {
		return trackDetails;
	}

	public void setTrackDetails(List<TrackDetail> trackDetails) {
		this.trackDetails = trackDetails;
	}

}
