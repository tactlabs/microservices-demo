package io.pivotal.microservices.services.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.pivotal.microservices.weapons.Weapon;




/**
 * Hide the access to the microservice inside this local service.
 * 
 * @author Paul Chapman
 */
@Service
public class WebWeaponsService {

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	protected String serviceUrl;

	protected Logger logger = Logger.getLogger(WebWeaponsService.class
			.getName());

	public WebWeaponsService(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl
				: "http://" + serviceUrl;
	}

	/**
	 * The RestTemplate works because it uses a custom request-factory that uses
	 * Ribbon to look-up the service to use. This method simply exists to show
	 * this.
	 */
	@PostConstruct
	public void demoOnly() {
		// Can't do this in the constructor because the RestTemplate injection
		// happens afterwards.
		logger.warning("The RestTemplate request factory is "
				+ restTemplate.getRequestFactory().getClass());
	}
	
	public List<Weapon> findAll(){
		logger.info("findAll() invoked: for ");
		
		
		//List<Weapon> weaponList = restTemplate.getForObject(serviceUrl + "/weapons", WeaponList.class);		
		//ResponseEntity<? extends ArrayList<Weapon>> responseEntity = restTemplate.getForEntity(serviceUrl, (Class<? extends ArrayList<Weapon>>) ArrayList.class);		
		//restTemplate.getForEntity(serviceUrl, (Class<? extends ArrayList<Weapon>>) ArrayList.class);
		
		ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(serviceUrl + "/weapons", Object[].class);
		Object[] objects = responseEntity.getBody();
		//MediaType contentType = responseEntity.getHeaders().getContentType();
		//HttpStatus statusCode = responseEntity.getStatusCode();
		
		logger.info("findAll() found: "+objects);
		
		List<Weapon> weaponList = new LinkedList<Weapon>();
		for (Object object : objects) {
			
			//logger.info("single object: "+object);
			
			Map<String, Object> objectMap = (LinkedHashMap<String, Object>) object;
			
			Long id = new Long(objectMap.get("id").toString());
			Long weaponid = new Long(objectMap.get("weaponid").toString());;
			String name = objectMap.get("name").toString();
			Integer balance = new Integer(objectMap.get("balance").toString());;
			String image = objectMap.get("image").toString();
			String description = objectMap.get("description").toString();
			
			Weapon weapon = new Weapon(id, weaponid, balance, image, name, description);
			
			logger.info("single weapon: "+object);
			
			weaponList.add(weapon);
		}
				
		return weaponList;
	}	
	
	class WeaponList extends ArrayList<Weapon> {
		
	}
	

	public Weapon findByNumber(String weaponid) {

		logger.info("findByNumber() invoked: for " + weaponid);
		return restTemplate.getForObject(serviceUrl + "/weapons/{weaponid}",
				Weapon.class, weaponid);
	}
}
