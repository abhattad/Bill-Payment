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

import com.payment.bill.entity.Biller;
import com.payment.bill.entity.Customer;
import com.payment.bill.entity.Wallet;
import com.payment.bill.exceptionhandler.InsufficientBalanceException;
import com.payment.bill.exceptionhandler.InvalidEmailException;
import com.payment.bill.model.BillerRequest;
import com.payment.bill.repository.BillerRepository;
import com.payment.bill.repository.CustomerRepository;
import com.payment.bill.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
class BillerServiceTest {

	@InjectMocks
	private BillerService billerService;
	@Mock
	private BillerRepository billerRepository;
	@Mock
	private CustomerRepository customerRepository;
	@Mock
	private TransactionRepository transactionRepository;

	@BeforeEach
	void setUp() throws Exception {
		List<Biller> billers = new ArrayList<Biller>();
		Wallet wallet = new Wallet();
		wallet.setBalance(10000L);
		Biller biller = new Biller();
		biller.setBillerName("ICICI Mutual Fund");
		biller.setMonth("Jan");
		biller.setMothlyBill(2000L);
		biller.setId(1L);
		biller.setWallet(wallet);
		billers.add(biller);
		BDDMockito.lenient().when(billerRepository.findAll()).thenReturn(billers);
		Optional<Biller> Optbiller = Optional.of(biller);
		BDDMockito.lenient().when(billerRepository.findById(Mockito.anyLong())).thenReturn(Optbiller);

		Wallet walletc = new Wallet();
		walletc.setBalance(10000L);
		Customer customer = new Customer();
		customer.setEmailId("amit.bhattad16@gmail.com");
		customer.setWallet(walletc);
		Optional<Customer> optCust = Optional.of(customer);
		BDDMockito.lenient().when(customerRepository.findById(Mockito.anyString())).thenReturn(optCust);
	}

	@Test
	void testGetAllBillers() {
		Assertions.assertEquals(1, billerService.getAllBillers().size());
	}

	@Test
	void testGetMonthlyBillToPay() {
		Assertions.assertEquals(1L, billerService.getMonthlyBillToPay(Mockito.anyLong()).getBiller_id());
	}

	@Test
	void testPayBill() throws InsufficientBalanceException {
		// BillerService billerSer = Mockito.mock(BillerService.class);
		billerService.payBill(1L, "email");
		// Mockito.verify(billerSer, Mockito.times(1)).payBill(1L, "email");
	}

	@Test
	void testPayBillLessBalance() throws InsufficientBalanceException {
		Wallet walletc = new Wallet();
		walletc.setBalance(100L);
		Customer customer = new Customer();
		customer.setEmailId("amit.bhattad16@gmail.com");
		customer.setWallet(walletc);
		Optional<Customer> optCust = Optional.of(customer);
		BDDMockito.lenient().when(customerRepository.findById(Mockito.anyString())).thenReturn(optCust);
		// BillerService billerSer = Mockito.mock(BillerService.class);
		Assertions.assertThrows(InsufficientBalanceException.class, () -> {
			billerService.payBill(1L, "email");
		});
		// Mockito.verify(billerSer, Mockito.times(1)).payBill(1L, "email");
	}

	@Test
	void testCreateBiller() {
		BillerRequest request = BillerRequest.builder().amount(1l).build();
		// BillerService billerSer = Mockito.mock(BillerService.class);
		billerService.createBiller(request);
		// BDDMockito.lenient().when(billerRepository.saveAndFlush(new
		// Biller())).thenReturn(new Biller());
		// Mockito.verify(billerSer, Mockito.times(1)).createBiller(request);

	}

}
