package io.pivotal.microservices.weapons;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Persistent weapon entity with JPA markup. Weapons are stored in an H2
 * relational database.
 * 
 * @author Paul Chapman
 */
@Entity
@Table(name = "T_WEAPON")
public class Weapon implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Long nextId = 0L;

	@Id
	protected Long id;
	
	protected Long weaponid;

	protected String name;

	protected Integer balance;
	
	protected String image;
	
	protected String description;

	/**
	 * This is a very simple, and non-scalable solution to generating unique
	 * ids. Not recommended for a real application. Consider using the
	 * <tt>@GeneratedValue</tt> annotation and a sequence to generate ids.
	 * 
	 * @return The next available id.
	 */
	protected static Long getNextId() {
		synchronized (nextId) {
			return nextId++;
		}
	}

	/**
	 * Default constructor for JPA only.
	 */
	public Weapon() {
		balance = 0;
	}
	
	public Weapon(Long id, Long weaponid, Integer balance, String image, String name, String description) {
		this.id = id;
		this.weaponid = weaponid;
		this.balance = balance;
		this.name = name;
		this.image = image;
		this.description = description;
	}

	public Weapon(String number, String owner) {
		id = getNextId();
		this.name = number;
		balance = 0;
	}

	public long getId() {
		return id;
	}

	/**
	 * Set JPA id - for testing and JPA only. Not intended for normal use.
	 * 
	 * @param id
	 *            The new id.
	 */
	protected void setId(long id) {
		this.id = id;
	}
	
	public long getWeaponid() {
		return weaponid;
	}
	
	protected void setWeaponid(long weaponid) {
		this.weaponid = weaponid;
	}

	public String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}
	
	public String getImage() {
		return image;
	}

	protected void setImage(String image) {
		this.image = image;
	}
	
	public String getDescription() {
		return image;
	}

	protected void setDescription(String description) {
		this.description = description;
	}

	public Integer getBalance() {
		return balance;
	}

	@Override
	public String toString() {
		return weaponid+" - "+name + " - " + balance;
	}
}
