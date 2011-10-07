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
package org.alfredlibrary.postalservices.internal.tracking.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.alfredlibrary.postalservices.tracking.validation.TrackingCode;

/**
 * Validator based on JSR 303 - Beans Validation.
 * 
 * @author Marlon Silva Carvalho
 * @since 2.0.0
 */
public class TrackingValidator implements ConstraintValidator<TrackingCode, String> {

	@Override
	public void initialize(TrackingCode constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value != null && value.length() == 13 && !isNumber(value.substring(0, 2)) && isNumber(value.substring(2, 11))
				&& !isNumber(value.substring(11, 13));
	}

	private boolean isNumber(String num) {
		boolean isNumber = false;
		try {
			Integer.valueOf(num.substring(0, 2));
			isNumber = true;
		} catch (NumberFormatException e) {
		}
		return isNumber;
	}

}