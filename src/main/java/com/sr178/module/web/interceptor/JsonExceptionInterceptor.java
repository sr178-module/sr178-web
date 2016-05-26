package com.sr178.module.web.interceptor;



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
