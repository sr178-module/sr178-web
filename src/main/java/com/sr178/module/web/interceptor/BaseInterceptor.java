package com.sr178.module.web.interceptor;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public abstract class BaseInterceptor extends AbstractInterceptor {

	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getMsgTag(){
		String result = "URL=[" + ServletActionContext.getRequest().getRequestURL().toString() + "][ip]=["
				+ ServletActionContext.getRequest().getRemoteAddr() + "],[host]=["
				+ ServletActionContext.getRequest().getRemoteHost() + "]";
		return result;
	}

}
