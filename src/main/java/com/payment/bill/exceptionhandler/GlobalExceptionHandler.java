package com.payment.bill.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomerAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public ErrorResponse handleCustomerExistsException(CustomerAlreadyExistsException ex) {
		return prepareErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
	}

	@ExceptionHandler(InvalidEmailException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
	protected ErrorResponse handleInvalidEmailException(InvalidEmailException ex) {
		return prepareErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
	}

	@ExceptionHandler(InsufficientBalanceException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INSUFFICIENT_STORAGE)
	protected ErrorResponse handleInsufficientBalanceException(InsufficientBalanceException ex) {
		return prepareErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
	}

	@ExceptionHandler(NoRecordFoundException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	protected ErrorResponse handleNoRecordFoundException(NoRecordFoundException ex) {
		return prepareErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	protected ErrorResponse handleException(NoRecordFoundException ex) {
		return prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
	}

	private ErrorResponse prepareErrorResponse(int code, String msg) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setStatusCode(code);
		errorResponse.setMessage(msg);
		return errorResponse;
	}
}
