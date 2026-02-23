package guru.springframework.spring7restmvc.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import guru.springframework.spring7restmvc.mappers.CustomerMapper;
import guru.springframework.spring7restmvc.model.CustomerDTO;
import guru.springframework.spring7restmvc.repositories.CustomerRepository;
import guru.springframework.spring7restmvc.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Primary
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceJPA implements CustomerService {
	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;
	private final CacheManager cacheManager;

	@Override
	@Cacheable(cacheNames = "customerCache")
	public Optional<CustomerDTO> getCustomerById(UUID customerId) {
		log.info("Get customer by Id - in service - {}", customerId);
		return customerRepository.findById(customerId)
			.map(customerMapper::customerToCustomerDto);
	}

	@Override
	@Cacheable(cacheNames = "customerListCache")
	public List<CustomerDTO> listCustomers() {
		log.info("List Customers - in service");
		return customerRepository.findAll()
			.stream()
			.map(customerMapper::customerToCustomerDto)
			.toList();
	}

	@Override
	public CustomerDTO saveNewCustomer(CustomerDTO customer) {
		LocalDateTime now = LocalDateTime.now();
		customer.setCreatedDate(now);
		customer.setUpdatedDate(now);
		clearCache(null);
		return customerMapper.customerToCustomerDto(customerRepository.save(customerMapper.customerDtoToCustomer(customer)));
	}

	@Override
	public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO dto) {
		dto.setUpdatedDate(LocalDateTime.now());
		AtomicReference<Optional<CustomerDTO>> reference = new AtomicReference<>();
		customerRepository.findById(customerId).ifPresentOrElse(customer -> {
			customer.setCustomerName(dto.getCustomerName());
			customer.setUpdatedDate(LocalDateTime.now());
			reference.set(Optional.of(customerMapper.customerToCustomerDto(customerRepository.save(customer))));
		}, () -> reference.set(Optional.empty()));
		clearCache(customerId);
		return reference.get();
	}

	@Override
	public boolean deleteById(UUID customerId) {
		if(!customerRepository.existsById(customerId)) {
			return false;
		}
		customerRepository.deleteById(customerId);
		clearCache(customerId);
		return true;
	}

	@Override
	public Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO dto) {
		dto.setUpdatedDate(LocalDateTime.now());
		AtomicReference<Optional<CustomerDTO>> reference = new AtomicReference<>();
		customerRepository.findById(customerId).ifPresentOrElse(customer -> {
			if(dto.getCustomerName() != null) {
				customer.setCustomerName(dto.getCustomerName());
			}
			customer.setUpdatedDate(LocalDateTime.now());
			reference.set(Optional.of(customerMapper.customerToCustomerDto(customerRepository.save(customer))));
		}, () -> reference.set(Optional.empty()));
		clearCache(customerId);
		return reference.get();
	}

	private void clearCache(Object key) {
		Cache customerListCache = cacheManager.getCache("customerListCache");
		if(customerListCache != null) {
			customerListCache.clear();
		}
		if(key != null) {
			Cache customerCache = cacheManager.getCache("customerCache");
			if(customerCache != null) {
				customerCache.evict(key);
			}
		}
	}

}
