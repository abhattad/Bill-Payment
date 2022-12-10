package com.payment.bill.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BillerRequest {
	String name;
	String month;
	Long amount;
}
