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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.alfredlibrary.network.WWW;
import org.alfredlibrary.postalservices.tracking.Status;
import org.alfredlibrary.postalservices.tracking.Tracking;
import org.alfredlibrary.postalservices.tracking.TrackingInfo;
import org.alfredlibrary.postalservices.tracking.exception.IncorrectTrackingCodeException;
import org.alfredlibrary.postalservices.tracking.exception.NullOrEmptyTrackingCodeException;
import org.alfredlibrary.postalservices.tracking.exception.TrackingException;
import org.alfredlibrary.postalservices.tracking.exception.TrackingNotFoundException;
import org.alfredlibrary.validation.internal.validator.TrackingValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation to get tracking informations of packages coming from Brazil.<br>
 * This implementation uses 'web scraping' (http://en.wikipedia.org/wiki/Web_scraping) <br>
 * to get data from http://www.correios.com.br/.<br>
 * 
 * No API is provided by the brazillian Postal Service called Correios :-(
 * 
 * @author Marlon Silva Carvalho
 * @since 2.0.0
 */
public class CorreiosTracking implements Tracking {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(CorreiosTracking.class);

	@Override
	public TrackingInfo track(final String code) {

		logger.debug("starting tracking of the code " + code);
		validate(code);
		logger.debug("asking Correios about the code.");

		final String content = WWW.getContent("http://websro.correios.com.br/sro_bin/txect01$.QueryList?P_LINGUA=001&P_TIPO=001&P_COD_UNI=" + code, "UTF-8");
		checkExists(code, content);

		return new TrackingInfo(getStatuses(code, content));
	}

	private void checkExists(final String code, final String content) {
		if (content.indexOf("O nosso sistema não possui dados sobre o objeto") > -1) {
			logger.info("tracking code " + code + " not found at Correios.");
			throw new TrackingNotFoundException();
		}
	}

	private List<Status> getStatuses(final String code, final String content) {
		List<Status> listStatuses = new ArrayList<Status>();

		try {
			String dateRegex = "[0-9][0-o]/[0-9][0-9]/[0-9][0-9][0-9][0-9]";
			String timeRegex = "[0-9][0-9]:[0-9][0-9]";
			String regex = "(rowspan=[12].(" + dateRegex + " " + timeRegex + ")</td><td>(.*)?</td><.*>(.*)</font></td.*(\\n.*colspan=2>(.*)</td>)?)";
			Matcher matcher = Pattern.compile(regex).matcher(content);
			while (matcher.find()) {
				listStatuses.add(createStatus(matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(6)));
			}
		} catch (Exception e) {
			logger.error("error tracking the code " + code, e);
			throw new TrackingException(e);
		}

		return listStatuses;
	}

	/**
	 * Creating Status.
	 * 
	 * @param date Date.
	 * @param location Location.
	 * @param description Description.
	 * @param details Details.
	 * @return Status.
	 */
	private Status createStatus(String date, String location, String description, String details) {
		logger.debug("creating status");

		Status status = new Status();
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			status.setDate(simpleDateFormat.parse(date));
		} catch (Exception exception) {

		}
		if (location != null) {
			status.setCity(location.trim());
			status.setState(location.trim());
		}
		if (description != null) {
			status.setDescription(description.trim());
		}
		if (details != null) {
			status.setDetails(details.trim());
		}
		return status;
	}

	/**
	 * Validating the code.
	 * 
	 * @param code Code.
	 */
	private void validate(String code) {
		logger.debug("validating code " + code);

		TrackingValidator validator = new TrackingValidator();

		if (code == null) {
			throw new NullOrEmptyTrackingCodeException();
		} else if ("".equals(code)) {
			throw new NullOrEmptyTrackingCodeException();
		} else if (!validator.isValid(code, null)) {
			throw new IncorrectTrackingCodeException();
		}
	}

}
