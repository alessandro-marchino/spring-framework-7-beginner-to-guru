package guru.springframework.spring7restmvc.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.spring7restmvc.config.KafkaConfig;
import guru.springframework.spring7restmvc.repositories.BeerOrderLineRepository;
import guru.springframework.spring7restmvcapi.events.DrinkPreparedEvent;
import guru.springframework.spring7restmvcapi.model.BeerOrderLineStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class DrinkPreparedListener {

	private final BeerOrderLineRepository repository;

	@KafkaListener(groupId = "DrinkPreparedListener", topics = KafkaConfig.DRINK_PREPARED_TOPIC)
	@Transactional
	public void listen(DrinkPreparedEvent event) {
		log.info("Drink prepared: {}", event);

		repository.findById(event.getBerrOrderLine().getId()).ifPresentOrElse(bol -> {
			bol.setOrderLineStatus(BeerOrderLineStatus.COMPLETE);
			repository.saveAndFlush(bol);
		}, () -> log.error("Beer order line not found for id {}", event.getBerrOrderLine().getId()));
	}
}
