package io.pivotal.microservices.services.weapons;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;


import io.pivotal.microservices.weapons.WeaponRepository;
import io.pivotal.microservices.weapons.WeaponsConfiguration;


/**
 * Run as a micro-service, registering with the Discovery Server (Eureka).
 * <p>
 * Note that the configuration for this application is imported from
 * {@link AccountsConfiguration}. This is a deliberate separation of concerns.
 * 
 * @author Paul Chapman
 */
@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(WeaponsConfiguration.class)
public class WeaponsServer {

	@Autowired
	protected WeaponRepository weaponRepository;

	protected Logger logger = Logger.getLogger(WeaponsServer.class.getName());

	/**
	 * Run the application using Spring Boot and an embedded servlet engine.
	 * 
	 * @param args
	 *            Program arguments - ignored.
	 */
	public static void main(String[] args) {
		// Tell server to look for weapons-server.properties or
		// weapons-server.yml
		System.setProperty("spring.config.name", "weapons-server");

		SpringApplication.run(WeaponsServer.class, args);
	}
}
