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
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Alternative;

import org.alfredlibrary.postalservices.tracking.Status;
import org.alfredlibrary.postalservices.tracking.Tracking;
import org.alfredlibrary.postalservices.tracking.TrackingNotFoundException;

/**
 * Default implementation to get tracking informations of packages coming from
 * Brazil.<br>
 * This implementation uses 'web scraping' (http://en.wikipedia.org/wiki/Web_scraping) <br>
 * to get data from http://www.correios.com.br/.<br>
 * 
 * No API is provided by the fucking brazillian Postal Service called Correios  :-(
 * 
 * @author Marlon Silva Carvalho
 * @since 2.0.0
 */
@Alternative
public class CorreiosTracking implements Tracking {
	private static final long serialVersionUID = 1L;

	@Override
	public List<Status> track(String code) {
		// validarCodObjeto(codObjeto);
		String content = ""; // WorldWideWeb.obterConteudoSite("http://websro.correios.com.br/sro_bin/txect01$.QueryList?P_LINGUA=001&P_TIPO=001&P_COD_UNI="
								// + codObjeto, "ISO-8859-1");

		BufferedReader br = new BufferedReader(new StringReader(content));
		String line = null;

		List<Status> listRegistroRastreamento = new ArrayList<Status>();

		if (content.indexOf("O nosso sistema não possui dados sobre o objeto") > -1) {
			throw new TrackingNotFoundException();
		}
		//
		// try {
		// while ((line = br.readLine()) != null) {
		// line = HTML.desconverterElementosHTMLEspeciais(line, 0);
		// if (line.indexOf("<tr><td ") > -1) {
		// if (line.indexOf("rowspan=1") > -1) {
		// listRegistroRastreamento.add(formarRegisto(line));
		// } else if (line.indexOf("rowspan=2") > -1
		// || line.indexOf("rowspan=3") > -1) {
		// Status status = formarRegisto(line);
		// line = br.readLine();
		// status = complementarRegistro(status, line);
		// listRegistroRastreamento.add(status);
		// }
		// }
		// }
		// } catch (IOException e) {
		// throw new RuntimeException(e);
		// }
		return listRegistroRastreamento;
	}

	private void validate(String code) {
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

	/**
	 * Obter o conteúdo de um site.
	 * 
	 * @param u
	 *            URL do Site.
	 * @return String contendo todo o conteúdo do site em HTML.
	 */
	public String obterConteudoSite(String u, String characterSet) {
		URL url;
		try {
			url = new URL(u);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), Charset.forName(characterSet)));
			String line;
			StringBuilder resultado = new StringBuilder();
			while ((line = rd.readLine()) != null) {
				resultado.append(line);
				resultado.append("\n");
			}
			rd.close();
			return resultado.toString();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
