package guru.springframework.spring7restclient.client;

import java.net.URI;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import guru.springframework.spring7restclient.model.BeerDTO;
import guru.springframework.spring7restclient.model.BeerStyle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BeerClientImpl implements BeerClient {

	public static final String GET_BEER_PATH = "/api/v1/beer";
	public static final String GET_BEER_BY_ID_PATH = "/api/v1/beer/{beerId}";

	private final RestClient.Builder restClientBuilder;

	@Override
	public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize) {
		return null;
	}

	@Override
	public Page<BeerDTO> listBeers() {
		return null;
	}

	@Override
	public BeerDTO getBeerById(UUID beerId) {
		RestClient restClient = restClientBuilder.build();
		return restClient.get()
			.uri(uriBuilder -> uriBuilder.path(GET_BEER_BY_ID_PATH).build(beerId))
			.retrieve()
			.body(BeerDTO.class);
	}

	@Override
	public BeerDTO createBeer(BeerDTO beerDTO) {
		RestClient restClient = restClientBuilder.build();

		URI location = restClient.post()
			.uri(uriBuilder -> uriBuilder.path(GET_BEER_PATH).build())
			.body(beerDTO)
			.retrieve()
			.toBodilessEntity()
			.getHeaders()
			.getLocation();

		return restClient.get()
			.uri(location.getPath())
			.retrieve()
			.body(BeerDTO.class);
	}

	@Override
	public BeerDTO updateBeer(BeerDTO beerDTO) {
		return null;
	}

	@Override
	public void deleteBeer(UUID beerId) {
	}
}
