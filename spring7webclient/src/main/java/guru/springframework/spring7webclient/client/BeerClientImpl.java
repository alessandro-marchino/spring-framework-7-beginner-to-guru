package guru.springframework.spring7webclient.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class BeerClientImpl implements BeerClient {
	private final WebClient webClient;

	public BeerClientImpl() {
		this.webClient = WebClient.builder()
			//.baseUrl("http://127.0.0.1:8080")
			.baseUrl("http://localhost:8080")
			.build();
	}

	@Override
	public Flux<String> listBeer() {
		return webClient.get()
			.uri("/api/v3/beer", String.class)
			.retrieve()
			.bodyToFlux(String.class);
	}
}
