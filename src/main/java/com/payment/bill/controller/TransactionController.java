package com.payment.bill.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.bill.dto.TransactionHistoryDTO;
import com.payment.bill.service.TransactionService;

@RestController
@RequestMapping("transactions")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@GetMapping
	public List<TransactionHistoryDTO> getAllTransactions() {
		return transactionService.getAllTransactions();
	}

}
