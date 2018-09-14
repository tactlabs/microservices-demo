package io.pivotal.microservices.weapons;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.pivotal.microservices.exceptions.WeaponNotFoundException;

/**
 * A RESTFul controller for accessing weapon information.
 * 
 * @author Raja CSP Raman
 */
@RestController
public class WeaponsController {

	protected Logger logger = Logger.getLogger(WeaponsController.class.getName());
	protected WeaponRepository weaponRepository;

	/**
	 * Create an instance plugging in the respository of Weapons.
	 * 
	 * @param weaponRepository
	 *            An weapon repository implementation.
	 */
	@Autowired
	public WeaponsController(WeaponRepository weaponRepository) {
		this.weaponRepository = weaponRepository;

		logger.info("WeaponRepository says system has "
				+ weaponRepository.countWeapons() + " weapons");
	}
	
	@RequestMapping("/weapons")
	public List<Weapon> findAll() {

		logger.info("weapons-service findAll invoked: ");
		List<Weapon> weapons = (List<Weapon>) weaponRepository.findAll();
		logger.info("weapons-service finaAll() found: " + weapons);

		if (weapons == null)
			throw new WeaponNotFoundException();
		
		return weapons;		
	}

	/**
	 * Fetch an weapon with the specified weapon number.
	 * 
	 * @param weaponNumber
	 *            A numeric, 9 digit weapon number.
	 * @return The weapon if found.
	 * @throws WeaponNotFoundException
	 *             If the number is not recognised.
	 */
	@RequestMapping("/weapons/{id}")
	public Weapon byNumber(@PathVariable("id") Long id) {

		logger.info("weapons-service byNumber() invoked: " + id);
		Weapon weapon = weaponRepository.findByWeaponid(id);
		logger.info("weapons-service byNumber() found: " + weapon);

		if (weapon == null)
			throw new WeaponNotFoundException(id);
		else {
			return weapon;
		}
	}

	/**
	 * Fetch weapons with the specified name. A partial case-insensitive match
	 * is supported. So <code>http://.../weapons/owner/a</code> will find any
	 * weapons with upper or lower case 'a' in their name.
	 * 
	 * @param partialName
	 * @return A non-null, non-empty set of weapons.
	 * @throws WeaponNotFoundException
	 *             If there are no matches at all.
	 */
	@RequestMapping("/weapons/owner/{name}")
	public List<Weapon> byOwner(@PathVariable("name") String partialName) {
		logger.info("weapons-service byOwner() invoked: "
				+ weaponRepository.getClass().getName() + " for "
				+ partialName);

		List<Weapon> weapons = weaponRepository
				.findByNameContainingIgnoreCase(partialName);
		logger.info("weapons-service byOwner() found: " + weapons);

		if (weapons == null || weapons.size() == 0)
			throw new WeaponNotFoundException(partialName);
		else {
			return weapons;
		}
	}
}
