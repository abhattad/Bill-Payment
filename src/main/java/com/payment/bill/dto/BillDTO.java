package com.payment.bill.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BillDTO {
	private Long biller_id;
	private String name;
	private String month;
	private Long bill_amount;
	private Long walletBalance;
}
