package com.demo.core.exception;

/** 
 * @ClassName: BusinessException 
 * @Description: 业务异常
 * @author zheng.shk
 * @date 2016年8月31日 上午11:25:20  
 */
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 68246261009830188L;

	private String errorCode;
	private String message;

	public BusinessException(String errorCode,String message) {
		super(errorCode);
		this.message = message;
		this.errorCode = errorCode;
	}
	
	public BusinessException(String errorCode) {
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
