package com.demo.core.exception;

/** 
 * @ClassName: FeignException
 * @author zheng.shk
 * @date 2018年2月26日 上午11:25:20
 */
public class FeignException extends RuntimeException {
	private static final long serialVersionUID = 68246261009830188L;

	private String errorCode;
	private String message;

	public FeignException(String errorCode, String message) {
		super(errorCode);
		this.message = message;
		this.errorCode = errorCode;
	}

	public FeignException(String errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}


	public String getErrorCode() {
		return errorCode;
	}


	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}


	@Override
	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


}
