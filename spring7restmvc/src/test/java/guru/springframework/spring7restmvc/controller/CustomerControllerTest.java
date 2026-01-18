package guru.springframework.spring7restmvc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import guru.springframework.spring7restmvc.model.CustomerDTO;
import guru.springframework.spring7restmvc.service.CustomerService;
import tools.jackson.databind.json.JsonMapper;

@WebMvcTest(CustomerController.class)
@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
	@Autowired MockMvc mockMvc;
	@Autowired JsonMapper jsonMapper;
	@MockitoBean CustomerService customerService;
	@Captor ArgumentCaptor<UUID> uuidArgumentCaptor;
	@Captor ArgumentCaptor<CustomerDTO> customerArgumentCaptor;

    @Test
    void testDeleteCustomer() throws Exception {
		UUID uuid = UUID.randomUUID();

		mockMvc.perform(delete(CustomerController.PATH_ID, uuid))
			.andExpect(status().isNoContent());
		verify(customerService).deleteById(uuidArgumentCaptor.capture());
		assertThat(uuidArgumentCaptor.getValue()).isEqualTo(uuid);
	}

    @Test
    void testGetCustomerById() throws Exception {
		UUID uuid = UUID.randomUUID();
		CustomerDTO result = CustomerDTO.builder()
			.id(uuid)
			.customerName("Test customer")
			.version(1)
			.build();

		given(customerService.getCustomerById(uuid)).willReturn(Optional.of(result));

		mockMvc.perform(get(CustomerController.PATH_ID, uuid)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is(uuid.toString())))
			.andExpect(jsonPath("$.customerName", is("Test customer")))
			.andExpect(jsonPath("$.version", is(1)));
    }

	@Test
	void testGetCustomerByIdNotFound() throws Exception {
		given(customerService.getCustomerById(any(UUID.class))).willReturn(Optional.empty());

		mockMvc.perform(get(CustomerController.PATH_ID, UUID.randomUUID())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
	}

    @Test
    void testListCustomers() throws Exception {
		List<CustomerDTO> result = List.of(
			CustomerDTO.builder().customerName("Customer 1").id(UUID.randomUUID()).build(),
			CustomerDTO.builder().customerName("Customer 2").id(UUID.randomUUID()).build(),
			CustomerDTO.builder().customerName("Customer 3").id(UUID.randomUUID()).build()
		);
		given(customerService.listCustomers()).willReturn(result);

		mockMvc.perform(get(CustomerController.PATH)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void testPatchCustomer() throws Exception {
		UUID uuid = UUID.randomUUID();
		Map<String, Object> customerMap = Map.of("customerName", "Test customer");

		mockMvc.perform(patch(CustomerController.PATH_ID, uuid)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(customerMap)))
			.andExpect(status().isNoContent());
		verify(customerService).patchCustomerById(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());
		assertThat(uuidArgumentCaptor.getValue()).isEqualTo(uuid);
		assertThat(customerArgumentCaptor.getValue().getCustomerName()).isEqualTo("Test customer");
    }

    @Test
    void testSaveCustomer() throws Exception {
		UUID uuid = UUID.randomUUID();
		CustomerDTO requestCustomer = CustomerDTO.builder()
			.customerName("Test customer")
			.build();
		CustomerDTO result = CustomerDTO.builder()
			.id(uuid)
			.build();

		given(customerService.saveNewCustomer(any(CustomerDTO.class))).willReturn(result);
		mockMvc.perform(post(CustomerController.PATH)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(requestCustomer)))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", "/api/v1/customer/" + uuid));
    }

    @Test
    void testUpdateCustomer() throws Exception {
		UUID uuid = UUID.randomUUID();
		CustomerDTO customer = CustomerDTO.builder()
			.customerName("Test customer")
			.build();

		mockMvc.perform(put(CustomerController.PATH_ID, uuid)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(customer)))
			.andExpect(status().isNoContent());
		verify(customerService).updateCustomerById(eq(uuid), any(CustomerDTO.class));
    }
}
