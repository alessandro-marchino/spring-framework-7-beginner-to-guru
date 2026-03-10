package guru.springframework.spring7restmvc.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import guru.springframework.spring7restmvc.config.KafkaConfig;
import guru.springframework.spring7restmvcapi.events.DrinkRequestEvent;
import guru.springframework.spring7restmvcapi.events.OrderPlacedEvent;
import guru.springframework.spring7restmvcapi.model.BeerOrderLineDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DrinkSplitterRouter {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	@KafkaListener(groupId = "DrinkSplitterRouter", topics = KafkaConfig.ORDER_PLACED_TOPIC)
	public void receive(@Payload OrderPlacedEvent event) {
		if(event.getBeerOrderDTO() == null || event.getBeerOrderDTO().getBeerOrderLines() == null || event.getBeerOrderDTO().getBeerOrderLines().isEmpty()) {
			log.error("Invalid Order Placed event");
			return;
		}
		event.getBeerOrderDTO().getBeerOrderLines().forEach(beerOrderLine -> {
			log.debug("Splitting {} order", beerOrderLine.getBeer().getBeerStyle());
			switch(beerOrderLine.getBeer().getBeerStyle()) {
				case LAGER -> sendIceColdBeer(beerOrderLine);
				case PILSNER -> sendIceColdBeer(beerOrderLine);
				case STOUT -> sendCoolBeer(beerOrderLine);
				case GOSE -> sendColdBeer(beerOrderLine);
				case PORTER -> sendCoolBeer(beerOrderLine);
				case ALE -> sendCoolBeer(beerOrderLine);
				case WHEAT -> sendColdBeer(beerOrderLine);
				case IPA -> sendCoolBeer(beerOrderLine);
				case PALE_ALE -> sendCoolBeer(beerOrderLine);
				case SAISON -> sendIceColdBeer(beerOrderLine);
				default -> sendColdBeer(beerOrderLine);
			}
		});
	}

	private void sendIceColdBeer(BeerOrderLineDTO beerOrderLine) {
		kafkaTemplate.send(KafkaConfig.DRINK_REQUEST_ICE_COLD_TOPIC, DrinkRequestEvent.builder()
			.beerOrderLine(beerOrderLine)
			.build());
	}
	private void sendColdBeer(BeerOrderLineDTO beerOrderLine) {
		kafkaTemplate.send(KafkaConfig.DRINK_REQUEST_COLD_TOPIC, DrinkRequestEvent.builder()
			.beerOrderLine(beerOrderLine)
			.build());

	}
	private void sendCoolBeer(BeerOrderLineDTO beerOrderLine) {
		kafkaTemplate.send(KafkaConfig.DRINK_REQUEST_COOL_TOPIC, DrinkRequestEvent.builder()
			.beerOrderLine(beerOrderLine)
			.build());

	}
}
