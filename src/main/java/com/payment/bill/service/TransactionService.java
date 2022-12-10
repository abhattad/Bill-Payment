package com.payment.bill.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.payment.bill.dto.TransactionHistoryDTO;
import com.payment.bill.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@GetMapping
	public List<TransactionHistoryDTO> getAllTransactions() {
		return transactionRepository.findAll().stream().map(e -> e.toDTO()).collect(Collectors.toList());
	}

}
