package com.example.demo.exception.handler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.controller.IndexController;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ExceptionHandler({Exception.class})
	public ModelAndView exceptionhandler(HttpServletRequest request, Exception e) throws Exception {
		
		logger.error("Request URL:{},Exception:{}" ,request.getRequestURL(),e);
		
		
		//如果是404跑這裡
		if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
			throw e;
		}
		
		ModelAndView mw = new ModelAndView();
		mw.addObject("url",request.getRequestURL());
		mw.addObject("exception",e);
		mw.setViewName("error/error");
		return mw;
		
		
	}

}
