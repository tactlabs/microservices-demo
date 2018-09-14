package io.pivotal.microservices.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * Allow the controller to return a 404 if an weapon is not found by simply
 * throwing this exception. The @ResponseStatus causes Spring MVC to return a
 * 404 instead of the usual 500.
 * 
 * @author Raja Raman
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class WeaponNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public WeaponNotFoundException(String weaponNumber) {
		super("No such weapon: " + weaponNumber);
	}
	
	public WeaponNotFoundException(Long id) {
		super("No such weapon: " + id);
	}
	
	public WeaponNotFoundException() {
		super("No weapons: ");
	}
}
