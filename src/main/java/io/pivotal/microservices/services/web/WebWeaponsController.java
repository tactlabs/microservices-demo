package io.pivotal.microservices.services.web;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.pivotal.microservices.weapons.Weapon;

/**
 * Client controller, fetches Weapon info from the microservice via
 * {@link WebWeaponsService}.
 * 
 * @author Raja Raman
 */
@Controller
public class WebWeaponsController {

	@Autowired
	protected WebWeaponsService weaponsService;

	protected Logger logger = Logger.getLogger(WebWeaponsController.class.getName());

	public WebWeaponsController(WebWeaponsService weaponsService) {
		this.weaponsService = weaponsService;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields("name", "searchText");
	}

	@RequestMapping("/weapons")
	public String findAllWeapons(Model model) {
		logger.info("web-service all weapons ");

		List<Weapon> weapons = weaponsService.findAll();
		logger.info("web-service byNumber() found: " + weapons);
		model.addAttribute("weapons", weapons);
		
		return "weapons";
	}

	@RequestMapping("/weapons/{weaponid}")
	public String byNumber(Model model,
			@PathVariable("weaponid") String weaponid) {

		logger.info("web-service byNumber() invoked: " + weaponid);

		Weapon weapon = weaponsService.findByNumber(weaponid);
		logger.info("web-service byNumber() found: " + weapon);
		model.addAttribute("weapon", weapon);
		
		return "weapon";
	}
}
