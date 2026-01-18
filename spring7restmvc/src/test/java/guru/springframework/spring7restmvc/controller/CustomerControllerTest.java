package guru.springframework.spring7restmvc.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
	@Autowired MockMvc mockMvc;
	@MockitoBean CustomerService customerService;

    @Test
	@Disabled
    void testDeleteCustomer() {

	}

    @Test
    void testGetCustomerById() throws Exception {
		Customer customer = new CustomerServiceImpl().listCustomers().get(0);
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
		List<Customer> customers = new CustomerServiceImpl().listCustomers();
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
	@Disabled
    void testSaveCustomer() {

    }

    @Test
	@Disabled
    void testUpdateCustomer() {

    }
}
