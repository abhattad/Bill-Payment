package com.payment.bill.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.payment.bill.dto.TransactionHistoryDTO;

@Entity
@Getter
@Setter
public class TransactionHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "transaction_id", nullable = false)
	private Long transactionId;

	@Column(name = "sender")
	private String sender;

	@Column(name = "sender_totalbal")
	private Long senderBalance = 0L;

	@Column(name = "recipient")
	private String recipient;

	@Column(name = "recipient_totalbal")
	private Long recipientBalance = 0L;

	@Column(name = "bill_amount")
	private Long billamount = 0L;

	public TransactionHistoryDTO toDTO() {
		return TransactionHistoryDTO.builder().transaction_id(transactionId).sender_name(sender)
				.sender_total_amount(senderBalance).recipient_name(recipient).recipient_total_amount(recipientBalance)
				.total_bill_payed(billamount).build();
	}

}
