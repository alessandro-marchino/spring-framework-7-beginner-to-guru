package guru.springframework.spring7restmvc.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import guru.springframework.spring7restmvc.entities.BeerAudit;
import guru.springframework.spring7restmvc.events.BeerCreatedEvent;
import guru.springframework.spring7restmvc.mappers.BeerMapper;
import guru.springframework.spring7restmvc.repositories.BeerAuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class BeerCreatedListener {
	private final BeerMapper beerMapper;
	private final BeerAuditRepository beerAuditRepository;

	@EventListener
	@Async
	public void listen(BeerCreatedEvent event) {
		BeerAudit beerAudit = beerMapper.beerToBeerAudit(event.getBeer());
		beerAudit.setAuditEventType("BEER_CREATED");
		if(event.getAuthentication() != null) {
			beerAudit.setPrincipalName(event.getAuthentication().getName());
		}
		BeerAudit savedBeerAudit = beerAuditRepository.save(beerAudit);
		log.debug("Beer audit saved: {} - beerId: {}", savedBeerAudit.getAuditId(), savedBeerAudit.getId());
	}
}
