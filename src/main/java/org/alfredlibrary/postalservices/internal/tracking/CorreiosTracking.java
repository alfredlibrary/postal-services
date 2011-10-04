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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Alternative;

import org.alfredlibrary.network.WWW;
import org.alfredlibrary.postalservices.tracking.Status;
import org.alfredlibrary.postalservices.tracking.Tracking;
import org.alfredlibrary.postalservices.tracking.TrackingNotFoundException;
import org.alfredlibrary.text.HTML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation to get tracking informations of packages coming from
 * Brazil.<br>
 * This implementation uses 'web scraping'
 * (http://en.wikipedia.org/wiki/Web_scraping) <br>
 * to get data from http://www.correios.com.br/.<br>
 * 
 * No API is provided by the brazillian Postal Service called Correios :-(
 * 
 * @author Marlon Silva Carvalho
 * @since 2.0.0
 */
@Alternative
public class CorreiosTracking implements Tracking {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(CorreiosTracking.class);

	/*
	 * (non-Javadoc)
	 * @see org.alfredlibrary.postalservices.tracking.Tracking#track(java.lang.String)
	 */
	@Override
	public List<Status> track(String code) {
		logger.debug("starting tracking of the code " + code);

		validate(code);

		logger.debug("asking Correios about the code.");
		String content = WWW.getContent("http://websro.correios.com.br/sro_bin/txect01$.QueryList?P_LINGUA=001&P_TIPO=001&P_COD_UNI=" + code, "ISO-8859-1");

		BufferedReader bufferedReader = new BufferedReader(new StringReader(content));
		String line = null;

		List<Status> listStatuses = new ArrayList<Status>();

		if (content.indexOf("O nosso sistema não possui dados sobre o objeto") > -1) {
			logger.info("tracking code " + code + " not found at Correios.");
			throw new TrackingNotFoundException();
		}

		try {
			while ((line = bufferedReader.readLine()) != null) {
				line = HTML.decodeSpecialHTMLEntities(line, 0);
				if (line.indexOf("<tr><td ") > -1) {
					if (line.indexOf("rowspan=1") > -1) {
						listStatuses.add(createStatus(line));
					} else if (line.indexOf("rowspan=2") > -1 || line.indexOf("rowspan=3") > -1) {
						Status status = createStatus(line);
						line = bufferedReader.readLine();
						setDetails(status, line);
						listStatuses.add(status);
						logger.debug("status found: " + status.getDescription() + ", " + status.getDetails() + ", " + status.getLocation());
					}
				}
			}
		} catch (IOException e) {
			logger.error("error tracking the code " + code, e);
			throw new RuntimeException(e);
		}
		return listStatuses;
	}

	/**
	 * Create the tracking status.
	 * 
	 * @param line Text containing the status details.
	 * @return Status.
	 */
	private Status createStatus(String line) {
		logger.debug("creating status from text: " + line);

		int indexLine = line.indexOf("rowspan=") + 10;
		Status status = new Status();
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			status.setDate(simpleDateFormat.parse(line.substring(indexLine, line.indexOf("</td><td>", indexLine) + 1)));
		} catch (Exception exception) {

		}
		indexLine = line.indexOf("</td><td>", indexLine) + 9;
		status.setLocation(line.substring(indexLine, line.indexOf("</td><td>", indexLine)));
		indexLine = line.indexOf("</td><td>", indexLine) + 30;
		status.setDescription(line.substring(indexLine, line.indexOf("</font></td></tr>", indexLine)));
		return status;
	}

	/**
	 * Extract tracking details from the string.
	 * 
	 * @param line Text containing the details.
	 */
	private void setDetails(Status status, String line) {
		logger.debug("finding details from text: " + line);
		int indexLine = line.indexOf("colspan=") + 10;
		status.setDetails(line.substring(indexLine, line.indexOf("</td></tr>", indexLine)));
	}

	private void validate(String code) {
		logger.debug("validating code " + code);
		// if (code == null) {
		// throw new NullOrEmptyTrackingCodeException();
		// } else if ( "".equals(code) ) {
		// throw new NullOrEmptyTrackingCodeException();
		// } else if (code.length() != 13) {
		// throw new IncorrectTrackingCodeException();
		// } else if (!"".equals(Texto.manterNumeros(code.substring(0, 1)))
		// || !"".equals(Texto.manterNumeros(code.substring(11, 13)))
		// || Texto.manterNumeros(code.substring(2, 11)).length() != 9) {
		// throw new AlfredException("Código de Rastreamento fora do padrão.");
		// }
	}

}
