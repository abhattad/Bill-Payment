package com.payment.bill.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.payment.bill.dto.CustomerDTO;
import com.payment.bill.exceptionhandler.CustomerAlreadyExistsException;
import com.payment.bill.exceptionhandler.InvalidEmailException;
import com.payment.bill.exceptionhandler.NoRecordFoundException;
import com.payment.bill.model.CustomerRequest;
import com.payment.bill.service.CustomerService;
import com.payment.bill.utils.CommonUtils;

@RestController
@RequestMapping("customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("registration")
	public CustomerDTO registerCustomer(@RequestBody CustomerRequest request) throws Exception {
		if (CommonUtils.isEmailValid(request.getEmail())) {
			if (customerService.checkIfCustomeAlreadyExists(request.getEmail())) {
				throw new CustomerAlreadyExistsException(request.getEmail());
			}
			return customerService.createCustomer(request.getEmail()).toDTO();
		} else {
			throw new InvalidEmailException("Please provide valid email address");
		}
	}

	@PutMapping("/wallet/add")
	public void addFundsToWallet(@RequestParam("email") String email, @RequestHeader("amount") Long amountInRupees)
			throws Exception {
		if (!customerService.checkIfCustomeAlreadyExists(email)) {
			throw new InvalidEmailException("Customer is not registor with this email address:"+ email);
		}
		customerService.addFundsToWallet(email, amountInRupees);
	}

	@GetMapping
	public CustomerDTO getCustomer(@RequestParam("email") String email) throws InvalidEmailException {
		if (!customerService.checkIfCustomeAlreadyExists(email)) {
			throw new InvalidEmailException("Customer is not exists");
		}
		return customerService.getCustomer(email);
	}
	
	@GetMapping("/getlist")
	public List<CustomerDTO> getCustomers() throws InvalidEmailException, NoRecordFoundException {
		if (customerService.getCustomers().size()<=0) {
			throw new NoRecordFoundException("There is no record in system");
		}
		return customerService.getCustomers();
	}

}
