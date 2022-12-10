package com.payment.bill.exceptionhandler;

public class InvalidEmailException extends Exception {

    public InvalidEmailException(String msg) {
        super(msg);
    }
}
