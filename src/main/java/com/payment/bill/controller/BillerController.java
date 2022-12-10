package com.payment.bill.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.bill.dto.BillDTO;
import com.payment.bill.exceptionhandler.InsufficientBalanceException;
import com.payment.bill.exceptionhandler.NoRecordFoundException;
import com.payment.bill.model.BillerRequest;
import com.payment.bill.service.BillerService;

@RestController
@RequestMapping("biller")
public class BillerController {

	@Autowired
	private BillerService billerService;

	@GetMapping("getbillers")
	public List<BillDTO> getAllBillers() throws NoRecordFoundException {
		if (billerService.getAllBillers().size() <= 0) {
			throw new NoRecordFoundException("There is no record in system");
		}
		return billerService.getAllBillers();
	}

	@PostMapping("create")
	public String createBiller(@RequestBody BillerRequest billerRequest) {
		billerService.createBiller(billerRequest);
		return "Your Biller Registor Successfully";
	}

	@GetMapping("{id}")
	public BillDTO getMonthlyBillToPay(@PathVariable("id") Long id) throws NoRecordFoundException {
		if (billerService.getAllBillers().isEmpty()) {
			throw new NoRecordFoundException("There is no record in system");
		}
		return billerService.getMonthlyBillToPay(id);
	}

	@PostMapping("{id}")
	public void payBill(@PathVariable("id") Long id, @RequestHeader("email") String email)
			throws InsufficientBalanceException {
		billerService.payBill(id, email);
	}

}
