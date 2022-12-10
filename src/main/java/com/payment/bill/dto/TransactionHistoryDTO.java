package com.payment.bill.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransactionHistoryDTO {

	private Long transaction_id;
	private String sender_name;
	private String recipient_name;
	private Long sender_total_amount;
	private Long recipient_total_amount;
	private Long total_bill_payed;
	
}
