package guru.springframework.spring7restmvc.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import guru.springframework.spring7restmvc.model.Customer;
import guru.springframework.spring7restmvc.service.CustomerService;
import guru.springframework.spring7restmvc.service.impl.CustomerServiceImpl;
import tools.jackson.databind.json.JsonMapper;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
	@Autowired MockMvc mockMvc;
	@Autowired JsonMapper jsonMapper;
	@MockitoBean CustomerService customerService;
	private CustomerServiceImpl customerServiceImpl;

	@BeforeEach
	void setUp() {
		customerServiceImpl = new CustomerServiceImpl();
	}

    @Test
	@Disabled
    void testDeleteCustomer() {

	}

    @Test
    void testGetCustomerById() throws Exception {
		Customer customer = customerServiceImpl.listCustomers().get(0);
		given(customerService.getCustomerById(customer.getId())).willReturn(customer);

		mockMvc.perform(get("/api/v1/customer/{customerId}", customer.getId())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is(customer.getId().toString())))
			.andExpect(jsonPath("$.customerName", is(customer.getCustomerName())));
    }

    @Test
    void testListCustomers() throws Exception {
		List<Customer> customers = customerServiceImpl.listCustomers();
		given(customerService.listCustomers()).willReturn(customers);

		mockMvc.perform(get("/api/v1/customer")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
	@Disabled
    void testPatchCustomer() {

    }

    @Test
    void testSaveCustomer() throws Exception {
		Customer result = customerServiceImpl.listCustomers().get(0);
		Customer customer = Customer.builder()
			.customerName(result.getCustomerName())
			.build();

		given(customerService.saveNewCustomer(any(Customer.class))).willReturn(result);
		mockMvc.perform(post("/api/v1/customer")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(customer)))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", "/api/v1/customer/" + result.getId()));
    }

    @Test
    void testUpdateCustomer() throws Exception {
		Customer customer = customerServiceImpl.listCustomers().get(0);

		mockMvc.perform(put("/api/v1/customer/{customerId}", customer.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(customer)))
			.andExpect(status().isNoContent());
		verify(customerService).updateCustomerById(eq(customer.getId()), any(Customer.class));
    }
}
