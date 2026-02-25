package guru.springframework.spring7restmvc.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import guru.springframework.spring7restmvc.entities.BeerAudit;
import guru.springframework.spring7restmvc.events.BeerEvent;
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
	public void listen(BeerEvent event) {
		BeerAudit beerAudit = beerMapper.beerToBeerAudit(event.getBeer());
		beerAudit.setAuditEventType(event.getEventType());
		if(event.getAuthentication() != null) {
			beerAudit.setPrincipalName(event.getAuthentication().getName());
		}
		BeerAudit savedBeerAudit = beerAuditRepository.save(beerAudit);
		log.debug("Beer audit ({}) saved: {} - beerId: {}", event.getEventType(), savedBeerAudit.getAuditId(), savedBeerAudit.getId());
	}

}
