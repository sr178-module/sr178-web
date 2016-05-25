package com.sr178.module.web.interceptor;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;


import com.opensymphony.xwork2.ActionInvocation;
import com.sr178.game.framework.exception.ServiceException;
import com.sr178.game.framework.log.LogSystem;
import com.sr178.module.web.action.JsonBaseActionSupport;

/**
 * struts异常拦截器
 * 
 * @author mc
 * 
 */
public class JsonExceptionInterceptor extends BaseInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String intercept(ActionInvocation invocation) throws Exception {
		
		JsonBaseActionSupport jsonAction = (JsonBaseActionSupport) invocation.getAction();
		// sql注入检查
//		String checkResult = checkSql(invocation);
//		if (checkResult != null) {
//			String msg = getMsgTag() + "[userName]=[" + jsonAction.getLoginUser()+ "]";
//			jsonAction.renderErrorResult(WebError.GLOAB_ERROR_SQL, "注入==》关键字="+checkResult);
//			LogSystem.warn("注入异常==》"+msg);
//			return JsonBaseActionSupport.JSON;
//		}
		
//		if(!jsonAction.isCanVisite()){
//			String msg = getMsgTag() + "[userName]=[" + aldAction.getUserName() + "]";
//			aldAction.setCode(WebError.GLOAB_ERROR_VISIT_LIMIT);
//			aldAction.setDesc("并发访问限制：");
//			LogSystem.warn("并发访问异常==》"+msg);
//			return WebError.GLOAB_ERROR_RESULT;
//		}
		String userName = jsonAction.getLoginUser();
		try {
			return invocation.invoke();
		} catch (Exception e) {
			String msg = getMsgTag() + "[userName]=[" + userName + "]";
			if(e instanceof ServiceException){
				ServiceException exception = (ServiceException)e;
				if(exception.getCode()>=3000){
					jsonAction.renderErrorResult(exception.getCode(), exception.getMessage());
				}else{
					jsonAction.renderErrorResult(exception.getMessage());
				}
				LogSystem.info(msg+exception.getMessage());
				return JsonBaseActionSupport.JSON;
			}else{
				LogSystem.error(e, msg);
				jsonAction.renderErrorResult("未知错误！");
				return JsonBaseActionSupport.JSON;
			}
		}
	}
}
