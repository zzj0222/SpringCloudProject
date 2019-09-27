package com.demo.core.handler;

import com.alibaba.fastjson.JSON;
import com.demo.core.Result;
import com.demo.core.enums.ResultCode;
import com.demo.core.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ExceptionHander implements HandlerExceptionResolver {
	private final Logger logger = LoggerFactory.getLogger(ExceptionHander.class);


	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,Exception e) {
		Result result = new Result();
		if (e instanceof BusinessException) {
			// 业务异常
			String errorCode = ((BusinessException) e).getErrorCode();
			String message = e.getMessage();
			result.setCode(errorCode);
			result.setMsg(message);
			logger.info("错误码：{}，错误信息：{}",errorCode,message);
		}else if (e instanceof Business2Exception) {
			// 业务异常
			String errorCode = ((Business2Exception) e).getErrorCode();
			String message = e.getMessage();
			result.setCode(errorCode);
			result.setMsg(message);
			logger.info("错误码：{}，错误信息：{}",errorCode,message);
		}else if (e instanceof TokenException) {
			// Toke ivalid
			result.setCode(ResultCode.TOKEN_IVALID.getCode());
			result.setMsg(ResultCode.TOKEN_IVALID.getMessage());
		} else if (e instanceof TokenExpiredException) {
            // Token异常处理
            result.setCode(ResultCode.TOKEN_EXPIRE.getCode());
            result.setMsg(ResultCode.TOKEN_EXPIRE.getMessage());
        } else if (e instanceof NotAuthException) {
			// NOT AUTH异常处理
			result.setCode(ResultCode.NOT_AUTH.getCode());
			result.setMsg(ResultCode.NOT_AUTH.getMessage());
		}else if (e instanceof ValidatorException) {
			// 校验异常处理
			result.setCode(ResultCode.VALIDATE_FAIL.getCode());
			result.setMsg(e.getMessage());
		}else if (e instanceof FeignException) {
			// FeignException异常处理
			result.setCode(((FeignException) e).getErrorCode());
			result.setMsg(e.getMessage());
		}  else {
			String message;
			if (handler instanceof HandlerMethod) {
				HandlerMethod handlerMethod = (HandlerMethod) handler;
//				String exception = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s", request.getRequestURI(),
//						handlerMethod.getBean().getClass().getName(), handlerMethod.getMethod().getName(),
//						e.getMessage());
				logger.error("exception:{}",e);
				message = "一不小心跑到火星来了";
			} else {
				message = e.getMessage();
			}
			logger.debug(message, e);
			result.setCode(ResultCode.ERROR.getCode());
			result.setMsg(message);
		}
		result.setSuccess(false);
		responseResult(response, result);
		return new ModelAndView();
	}

	private void responseResult(HttpServletResponse response, Result result) {
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "application/json;charset=UTF-8");
		response.setStatus(200);
		try {
			response.getWriter().write(JSON.toJSONString(result));
		} catch (IOException ex) {
			logger.error(ex.getMessage());
		}
	}


}
