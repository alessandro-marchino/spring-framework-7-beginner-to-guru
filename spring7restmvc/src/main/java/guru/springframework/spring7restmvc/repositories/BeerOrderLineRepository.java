package guru.springframework.spring7restmvc.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import guru.springframework.spring7restmvc.entities.BeerOrderLine;

@Repository
public interface BeerOrderLineRepository extends JpaRepository<BeerOrderLine, UUID> {

}
