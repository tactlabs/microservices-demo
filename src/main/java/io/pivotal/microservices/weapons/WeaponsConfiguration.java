package io.pivotal.microservices.weapons;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

/**
 * The weapons Spring configuration.
 * 
 * @author Paul Chapman
 */
@Configuration
@ComponentScan
@EntityScan("io.pivotal.microservices.weapons")
@EnableJpaRepositories("io.pivotal.microservices.weapons")
@PropertySource("classpath:db-config.properties")
public class WeaponsConfiguration {

	protected Logger logger;

	public WeaponsConfiguration() {
		logger = Logger.getLogger(getClass().getName());
	}

	/**
	 * Creates an in-memory "rewards" database populated with test data for fast
	 * testing
	 */
	@Bean
	public DataSource dataSource() {
		logger.info("dataSource() invoked");

		// Create an in-memory H2 relational database containing some demo
		// weapons.
		DataSource dataSource = (new EmbeddedDatabaseBuilder()).addScript("classpath:testdb/schema.sql")
				.addScript("classpath:testdb/data.sql").build();

		logger.info("dataSource = " + dataSource);

		// Sanity check
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String, Object>> weapons = jdbcTemplate.queryForList("SELECT weaponid FROM T_WEAPON");
		logger.info("System has " + weapons.size() + " weapons");

		// Populate with random balances
		Random rand = new Random();

		for (Map<String, Object> item : weapons) {
			String name = (String) item.get("name");
			Integer balance = rand.nextInt(10000000);
			jdbcTemplate.update("UPDATE T_WEAPON SET balance = ? WHERE name = ?", balance, name);
		}

		return dataSource;
	}
}
