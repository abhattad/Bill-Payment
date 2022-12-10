
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

import com.payment.bill.dto.TransactionHistoryDTO;
import com.payment.bill.service.TransactionService;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

	@InjectMocks
	TransactionController transactionController;

	@Mock
	TransactionService transactionService;

	@BeforeEach
	void setUp() throws Exception {
		List<TransactionHistoryDTO> historyDTOs = new ArrayList<TransactionHistoryDTO>();
		BDDMockito.lenient().when(transactionService.getAllTransactions()).thenReturn(historyDTOs);
	}

	@Test
	void testGetAllTransactions() {
		Assertions.assertEquals(0, transactionController.getAllTransactions().size());
	}

}
