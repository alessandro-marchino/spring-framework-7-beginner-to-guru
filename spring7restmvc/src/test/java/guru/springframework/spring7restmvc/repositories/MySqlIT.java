package guru.springframework.spring7restmvc.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;

import guru.springframework.spring7restmvc.entities.Beer;

@Testcontainers
@SpringBootTest
@ActiveProfiles("localmysql")
public class MySqlIT {

	@Container
	@ServiceConnection
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:9.5.0");

	@Autowired BeerRepository beerRepository;

	@Test
	void testListBeers() {
		List<Beer> beers = beerRepository.findAll();
		assertThat(beers).hasSize(2413);
	}
}
