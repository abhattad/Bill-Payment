package com.payment.bill.exceptionhandler;

public class InsufficientBalanceException extends Exception {

    public InsufficientBalanceException() {
        super("You do not have sufficient balance to pay the bill");
    }

}
