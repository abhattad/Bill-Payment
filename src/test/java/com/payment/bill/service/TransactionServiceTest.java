package com.payment.bill.service;

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

import com.payment.bill.entity.TransactionHistory;
import com.payment.bill.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

	@InjectMocks
	private TransactionService transactionService;
	@Mock
	private TransactionRepository transactionRepository;

	@BeforeEach
	void setUp() throws Exception {
		List<TransactionHistory> history = new ArrayList<TransactionHistory>();
		BDDMockito.lenient().when(transactionRepository.findAll()).thenReturn(history);
	}

	@Test
	void testGetAllTransactions() {
		Assertions.assertEquals(0, transactionService.getAllTransactions().size());
	}

}
