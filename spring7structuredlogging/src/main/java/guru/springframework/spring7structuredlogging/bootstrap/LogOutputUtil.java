package guru.springframework.spring7structuredlogging.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LogOutputUtil implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		log.trace("Trace log");
		log.debug("Debugging log");
		log.info("Info log");
		log.warn("Hey, this is a warning");
		log.error("Oops! We have an error");
	}
}
