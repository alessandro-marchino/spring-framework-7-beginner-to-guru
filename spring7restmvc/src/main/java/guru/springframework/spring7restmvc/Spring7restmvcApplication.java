package guru.springframework.spring7restmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Spring7restmvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring7restmvcApplication.class, args);
	}

}
