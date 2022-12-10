
package com.payment.bill.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.payment.bill.dto.CustomerDTO;
import com.payment.bill.entity.Customer;
import com.payment.bill.exceptionhandler.CustomerAlreadyExistsException;
import com.payment.bill.exceptionhandler.InvalidEmailException;
import com.payment.bill.exceptionhandler.NoRecordFoundException;
import com.payment.bill.model.CustomerRequest;
import com.payment.bill.service.CustomerService;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

	@InjectMocks
	private CustomerController customerController;
	private static final String email_id = "amit.bhattad16@gmail.com";

	@Mock
	private CustomerService customerService;

	@BeforeEach
	void setUp() throws Exception {

		Customer customer = new Customer();
		customer.setEmailId(email_id);
		CustomerDTO customerDTO = CustomerDTO.builder().emailId(email_id).build();
		List<CustomerDTO> custDTOs = new ArrayList<CustomerDTO>();
		custDTOs.add(customerDTO);
		BDDMockito.lenient().when(customerService.createCustomer(email_id)).thenReturn(customer);
		BDDMockito.lenient().when(customerService.getCustomers()).thenReturn(custDTOs);
		BDDMockito.lenient().when(customerService.getCustomer(email_id))
				.thenReturn(CustomerDTO.builder().emailId(email_id).build());
		BDDMockito.lenient().doNothing().when(customerService).addFundsToWallet(email_id, 10000L);

		BDDMockito.lenient().when(customerService.checkIfCustomeAlreadyExists(email_id))
				.thenReturn(Boolean.TRUE.booleanValue());

	}

	@Test
	void testRegisterCustomer() throws Exception {
		CustomerRequest customerRequest = new CustomerRequest();
		customerRequest.setEmail(email_id);
		BDDMockito.lenient().when(customerService.checkIfCustomeAlreadyExists(email_id))
				.thenReturn(Boolean.FALSE.booleanValue());
		Assertions.assertEquals("amit.bhattad16@gmail.com",
				customerController.registerCustomer(customerRequest).getEmailId());
	}

	@Test
	void testRegisterCustomerAlreadyExist() throws Exception {
		CustomerRequest customerRequest = new CustomerRequest();
		customerRequest.setEmail(email_id);
		BDDMockito.lenient().when(customerService.checkIfCustomeAlreadyExists(email_id))
				.thenReturn(Boolean.TRUE.booleanValue());
		Assertions.assertThrows(CustomerAlreadyExistsException.class, () -> {
			customerController.registerCustomer(customerRequest);
		});
	}

	@Test
	void testRegisterCustomerInvalidEmail() throws Exception {
		CustomerRequest customerRequest = new CustomerRequest();
		customerRequest.setEmail(email_id + "@");
		Assertions.assertThrows(InvalidEmailException.class, () -> {
			customerController.registerCustomer(customerRequest);
		});
	}

	@Test
	void testAddFundsToWallet() throws Exception {
		customerController.addFundsToWallet(email_id, 1000L);
	}

	@Test
	void testAddFundsToWalletInvalidEmail() throws Exception {
		Assertions.assertThrows(InvalidEmailException.class, () -> {
			customerController.addFundsToWallet(email_id + "@", 1000L);
		});
	}

	@Test
	void testGetCustomer() throws InvalidEmailException {
		BDDMockito.lenient().when(customerService.checkIfCustomeAlreadyExists(email_id))
				.thenReturn(Boolean.TRUE.booleanValue());
		Assertions.assertEquals(email_id, customerController.getCustomer(email_id).getEmailId());
	}

	@Test
	void testGetCustomerInvalidEmail() throws InvalidEmailException {
		BDDMockito.lenient().when(customerService.checkIfCustomeAlreadyExists(email_id))
				.thenReturn(Boolean.FALSE.booleanValue());
		Assertions.assertThrows(InvalidEmailException.class, () -> {
			customerController.getCustomer(email_id);
		});
	}

	@Test
	void testGetCustomers() throws InvalidEmailException, NoRecordFoundException {
		Assertions.assertEquals(1, customerController.getCustomers().size());
	}

	@Test
	void testGetNoRecordCustomers() throws NoRecordFoundException, InvalidEmailException {
		BDDMockito.lenient().when(customerService.getCustomers()).thenReturn(new ArrayList<CustomerDTO>());
		Assertions.assertThrows(NoRecordFoundException.class, () -> {
			customerController.getCustomers();
		});
	}

}
