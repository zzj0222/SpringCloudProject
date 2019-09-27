package com.demo.core.exception;

/** 
 * @ClassName: Business2Exception
 * @Description: 业务异常  http code 固定返回200
 * @author zheng.shk
 * @date 2016年8月31日 上午11:25:20  
 */
public class Business2Exception extends RuntimeException {
	private static final long serialVersionUID = 68246261009830188L;

	private String errorCode;
	private String message;

	public Business2Exception(String errorCode, String message) {
		super(errorCode);
		this.message = message;
		this.errorCode = errorCode;
	}

	public Business2Exception(String errorCode) {
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
