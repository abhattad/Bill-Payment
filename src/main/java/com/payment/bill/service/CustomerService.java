package com.payment.bill.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.bill.dto.CustomerDTO;
import com.payment.bill.entity.Customer;
import com.payment.bill.entity.TransactionHistory;
import com.payment.bill.entity.Wallet;
import com.payment.bill.repository.CustomerRepository;
import com.payment.bill.repository.TransactionRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private TransactionRepository transactionRepository;

	public Customer createCustomer(String email) {
		Customer customer = new Customer();
		customer.setWallet(new Wallet());
		customer.setEmailId(email.toLowerCase());
		customer = customerRepository.saveAndFlush(customer);
		return customer;
	}

	public boolean checkIfCustomeAlreadyExists(String email) {
		return customerRepository.findById(email.toLowerCase()).isPresent();
	}

	public void addFundsToWallet(String email, Long amountInRupees) {
		Customer customer = customerRepository.findById(email.toLowerCase()).get();
		Wallet wl = customer.getWallet();
		Long existingBalance = wl.getBalance();
		wl.setBalance(existingBalance + amountInRupees);
		customerRepository.saveAndFlush(customer);

		TransactionHistory t = new TransactionHistory();
		t.setSender(email);
		t.setSenderBalance(amountInRupees);
		t.setRecipient(" ");
		transactionRepository.saveAndFlush(t);
	}

	public CustomerDTO getCustomer(String email) {
		return customerRepository.findById(email.toLowerCase()).get().toDTO();
	}

	public List<CustomerDTO> getCustomers() {
		return customerRepository.findAll().stream().map(cust -> cust.toDTO()).collect(Collectors.toList());
	}
}
