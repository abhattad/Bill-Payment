package com.payment.bill.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerDTO {

    private String emailId;
    private Long walletBalance;

}
