package com.payment.bill.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;

import com.payment.bill.dto.CustomerDTO;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer {
	@Id
	@Column(name = "email_id", nullable = false)
	@Email
	private String emailId;

	@OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
	@JoinColumn(name = "wallet_id", nullable = false, unique = true)
	private Wallet wallet = new Wallet();

	public CustomerDTO toDTO() {
		return CustomerDTO.builder().emailId(emailId).walletBalance(wallet.getBalance()).build();
	}
}