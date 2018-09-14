package io.pivotal.microservices.weapons;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for Weapon data implemented using Spring Data JPA.
 * 
 * @author Raja Raman
 */
public interface WeaponRepository extends CrudRepository<Weapon, Long> {
	
	/**
	 * Find an Weapon with the specified Weapon number.
	 *
	 * @param WeaponNumber
	 * @return The Weapon if found, null otherwise.
	 */
	public Weapon findByWeaponid(Long id);
	
	/**
	 * Find an Weapon with the specified Weapon number.
	 *
	 * @param WeaponNumber
	 * @return The Weapon if found, null otherwise.
	 */
	public Weapon findByName(String weaponName);

	/**
	 * Find weapons whose owner name contains the specified string
	 * 
	 * @param partialName
	 *            Any alphabetic string.
	 * @return The list of matching weapons - always non-null, but may be
	 *         empty.
	 */
	public List<Weapon> findByNameContainingIgnoreCase(String partialName);

	/**
	 * Fetch the number of weapons known to the system.
	 * 
	 * @return The number of weapons.
	 */
	@Query("SELECT count(*) from Weapon")
	public int countWeapons();
}
