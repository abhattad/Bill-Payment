package com.payment.bill.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.payment.bill.dto.BillDTO;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Biller {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "bill_name", nullable = false)
	private String billerName;

	@Column(name = "bill_month", nullable = false)
	private String month;

	@Column(name = "mothly_bill", nullable = false)
	private Long mothlyBill;

	@OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
	@JoinColumn(name = "wallet_id", nullable = false, unique = true)
	private Wallet wallet = new Wallet();

	public BillDTO toDTO() {
		return BillDTO.builder().biller_id(id).name(billerName).bill_amount(mothlyBill).month(month)
				.walletBalance(wallet.getBalance()).build();
	}
}
