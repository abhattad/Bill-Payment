package com.payment.bill.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.bill.dto.BillDTO;
import com.payment.bill.entity.Biller;
import com.payment.bill.entity.Customer;
import com.payment.bill.entity.TransactionHistory;
import com.payment.bill.entity.Wallet;
import com.payment.bill.exceptionhandler.InsufficientBalanceException;
import com.payment.bill.model.BillerRequest;
import com.payment.bill.repository.BillerRepository;
import com.payment.bill.repository.CustomerRepository;
import com.payment.bill.repository.TransactionRepository;

@Service
public class BillerService {

	@Autowired
	private BillerRepository billerRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private TransactionRepository transactionRepository;

	public List<BillDTO> getAllBillers() {
		return billerRepository.findAll().stream().map(bill -> bill.toDTO()).collect(Collectors.toList());
	}

	public BillDTO getMonthlyBillToPay(Long id) {
		BillDTO billDTO = null;
		Optional<Biller> Optbiller = billerRepository.findById(id);
		if (Optbiller.isPresent()) {
			billDTO = Optbiller.get().toDTO();
		}
		return billDTO;
	}

	public void payBill(Long id, String email) throws InsufficientBalanceException {
		Biller biller = billerRepository.findById(id).get();
		Customer customer = customerRepository.findById(email).get();

		Long existingBalance = customer.getWallet().getBalance();
		if (existingBalance < biller.getMothlyBill())
			throw new InsufficientBalanceException();

		biller.getWallet().setBalance(biller.getMothlyBill());
		billerRepository.saveAndFlush(biller);

		customer.getWallet().setBalance(existingBalance - biller.getMothlyBill());
		customerRepository.saveAndFlush(customer);

		TransactionHistory t = new TransactionHistory();
		t.setSender(email);
		t.setRecipient(biller.getBillerName());
		t.setSenderBalance(customer.getWallet().getBalance());
		t.setRecipientBalance(biller.getWallet().getBalance());
		t.setBillamount(biller.getMothlyBill());

		transactionRepository.saveAndFlush(t);

	}

	public void createBiller(BillerRequest billerRequest) {
		Biller biller = new Biller();
		biller.setBillerName(billerRequest.getName());
		biller.setMothlyBill(billerRequest.getAmount());
		biller.setMonth(billerRequest.getMonth());
		biller.setWallet(new Wallet());
		billerRepository.saveAndFlush(biller);
	}
}
