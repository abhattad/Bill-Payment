package com.payment.bill.utils;

public class CommonUtils {

	private static final String EMAIL_PATTERN = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";

	public static boolean isEmailValid(String email) {
		return email.matches(EMAIL_PATTERN);
	}
}
