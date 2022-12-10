package com.payment.bill.exceptionhandler;

public class CustomerAlreadyExistsException extends Exception {

    public CustomerAlreadyExistsException(String email) {
        super("Customer already exists with email id : "+email);
    }
}
