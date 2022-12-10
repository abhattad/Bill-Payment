
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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.payment.bill.dto.BillDTO;
import com.payment.bill.exceptionhandler.InsufficientBalanceException;
import com.payment.bill.exceptionhandler.NoRecordFoundException;
import com.payment.bill.service.BillerService;

@ExtendWith(MockitoExtension.class)
public class BillerControllerTest {

	@InjectMocks
	private BillerController billerController;

	@Mock
	private BillerService billerService;

	@BeforeEach
	void setUp() throws Exception {
		List<BillDTO> billDTOs = new ArrayList<BillDTO>();
		BillDTO billDTO = BillDTO.builder().biller_id(1L).name("ICICI Mutual Fund").bill_amount(4500L).month("Jan")
				.walletBalance(0L).build();
		billDTOs.add(billDTO);
		BDDMockito.lenient().doNothing().when(billerService).createBiller(Mockito.any());
		BDDMockito.lenient().when(billerService.getAllBillers()).thenReturn(billDTOs);
		BDDMockito.lenient().when(billerService.getMonthlyBillToPay(1L)).thenReturn(billDTO);
		BDDMockito.lenient().doNothing().when(billerService).createBiller(Mockito.any());
		BDDMockito.lenient().doNothing().when(billerService).payBill(1L, "amit.bhattad16@gmail.com");

	}

	@Test
	public void testGetAllBillers() throws NoRecordFoundException {
		Assertions.assertEquals(1, billerController.getAllBillers().size());

	}

	@Test
	public void testGetNoRecordOfAllBillers() throws NoRecordFoundException {
		BDDMockito.lenient().when(billerService.getAllBillers()).thenReturn(new ArrayList<BillDTO>());
		Assertions.assertThrows(NoRecordFoundException.class, () -> {
			billerController.getAllBillers();
		});

	}

	@Test
	public void testGetAllBillersListEmptyList() throws NoRecordFoundException {
		Assertions.assertEquals(1, billerController.getAllBillers().size());
	}

	@Test
	public void testCreateBiller() {
		Assertions.assertEquals("Your Biller Registor Successfully", billerController.createBiller(Mockito.any()));
	}

	@Test
	public void testGetMonthlyBillToPay() throws NoRecordFoundException {
		Assertions.assertEquals(4500L, billerController.getMonthlyBillToPay(1L).getBill_amount().longValue());
	}

	@Test
	public void testGetMonthlyBillToPayNoRecord() throws NoRecordFoundException {
		BDDMockito.lenient().when(billerService.getAllBillers()).thenReturn(new ArrayList<BillDTO>());
		BDDMockito.lenient().when(billerService.getMonthlyBillToPay(1L)).thenReturn(BillDTO.builder().build());
		Assertions.assertThrows(NoRecordFoundException.class, () -> {
			billerController.getMonthlyBillToPay(1L);
		});

	}

	@Test
	public void testbillPay() throws InsufficientBalanceException {
		billerController.payBill(1L, "amit.bhattad16@gmail.com");

	}
}
