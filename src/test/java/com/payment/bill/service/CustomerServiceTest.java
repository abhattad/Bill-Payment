package com.payment.bill.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.payment.bill.entity.Customer;
import com.payment.bill.entity.Wallet;
import com.payment.bill.repository.CustomerRepository;
import com.payment.bill.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

	@InjectMocks
	CustomerService customerService;
	@Mock
	private CustomerRepository customerRepository;
	@Mock
	private TransactionRepository transactionRepository;

	@BeforeEach
	void setUp() throws Exception {
		Customer customer = new Customer();
		customer.setEmailId("amit.bhattad16@gmail.com");
		customer.setWallet(new Wallet());
		Optional<Customer> OptCust = Optional.of(customer);

		List<Customer> custs = new ArrayList<Customer>();
		custs.add(customer);
		BDDMockito.lenient().when(customerRepository.findAll()).thenReturn(custs);
		BDDMockito.lenient().when(customerRepository.findById("amit.bhattad16@gmail.com")).thenReturn(OptCust);

	}

	@Test
	void testCreateCustomer() {
		Customer customer = new Customer();
		customer.setEmailId("amit.bhattad16@gmail.com");
		customer.setWallet(new Wallet());
		BDDMockito.lenient().when(customerRepository.saveAndFlush(customer)).thenReturn(customer);
		Assertions.assertNull(customerService.createCustomer("amit.bhattad16@gmail.com"));
	}

	@Test
	void testCheckIfCustomeAlreadyExists() {
		Assertions.assertEquals(Boolean.TRUE.booleanValue(),
				customerService.checkIfCustomeAlreadyExists("amit.bhattad16@gmail.com"));
	}

	@Test
	void testAddFundsToWallet() {
		//CustomerService customerSer = Mockito.mock(CustomerService.class);
		customerService.addFundsToWallet("amit.bhattad16@gmail.com", 1000L);
		//Mockito.verify(customerSer, Mockito.times(1)).addFundsToWallet("amit.bhattad16@gmail.com", 1000L);

	}

	@Test
	void testGetCustomer() {
		Customer customer = new Customer();
		customer.setEmailId("amit.bhattad16@gmail.com");
		Optional<Customer> OptCust = Optional.of(customer);
		BDDMockito.lenient().when(customerRepository.findById("amit.bhattad16@gmail.com".toLowerCase()))
				.thenReturn(OptCust);
		Assertions.assertEquals("amit.bhattad16@gmail.com",
				customerService.getCustomer("amit.bhattad16@gmail.com").getEmailId());
	}

	@Test
	void testGetCustomers() {
		Assertions.assertEquals(1, customerService.getCustomers().size());
	}

}
