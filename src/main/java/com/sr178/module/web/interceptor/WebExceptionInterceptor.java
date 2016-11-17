package com.sr178.module.web.interceptor;


import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.opensymphony.xwork2.ActionInvocation;
import com.sr178.game.framework.exception.ServiceException;
import com.sr178.game.framework.log.LogSystem;
import com.sr178.module.web.action.BaseActionSupport;
import com.sr178.module.web.action.JsonBaseActionSupport;
import com.sr178.module.web.constant.WebError;

/**
 * struts异常拦截器
 * 
 * @author mc
 * 
 */
public class WebExceptionInterceptor extends BaseInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String intercept(ActionInvocation invocation) throws Exception {
		BaseActionSupport aldAction = (BaseActionSupport) invocation.getAction();
		try {
			return invocation.invoke();
		} catch (Exception e) {
			StringBuffer values = new StringBuffer();
			Map<String, Object> map = invocation.getInvocationContext().getParameters();
			Set<String> keys = map.keySet();
			Iterator<String> iters = keys.iterator();
			while (iters.hasNext()) {
				String key = iters.next();
				Object value = map.get(key);
				if(value instanceof String[]){
					String result = getString((String[]) value);
					values.append("key=("+key+"),value=("+result+");");
				}
			}
			String msg = getMsgTag() + "[userName]=[" + aldAction.getUserName() + "],param=["+values.toString()+"]";
			if (e instanceof ServiceException) {
				ServiceException exception = (ServiceException) e;
				aldAction.setCode(exception.getCode());
				aldAction.setDesc(exception.getMessage());
				LogSystem.info(msg + "error=【" + exception.toString() + "】");
				//如果设置了错误界面  则返回指定的错误界面，没有的话直接跳到全局错误界面
				if (aldAction.getErrorResult() == null) {
					return WebError.GLOAB_ERROR_RESULT;
				} else {
					if(aldAction.getErrorResult().equals("json")){
						if(invocation.getAction() instanceof JsonBaseActionSupport){
							JsonBaseActionSupport jsonAction = (JsonBaseActionSupport)invocation.getAction();
							return jsonAction.renderErrorResult(exception.getCode(), exception.getMessage());
						}else{
							throw new RuntimeException("返回json的action必须继承自JsonBaseActionSupport!");
						}
					}
					return aldAction.getErrorResult();
				}
			} else {
				LogSystem.error(e, msg+ "");
				if(aldAction.getErrorResult() == null){
					aldAction.setCode(WebError.GLOAB_ERROR_CODE);
					aldAction.setDesc("不可预知的错误！");
					return WebError.GLOAB_ERROR_RESULT;
				}else{
					if(aldAction.getErrorResult().equals("json")){
						if(invocation.getAction() instanceof JsonBaseActionSupport){
							JsonBaseActionSupport jsonAction = (JsonBaseActionSupport)invocation.getAction();
							return jsonAction.renderErrorResult(8888, "不可预知的错误！");
						}else{
							throw new RuntimeException("返回json的action必须继承自JsonBaseActionSupport!");
						}
					}
					return aldAction.getErrorResult();
				}
			}
		}
	}
	
	public String getString(String[] str)
	{
		String result = "";
		for(int i=0;i<str.length;i++){
			result = result + str[i];
		}
	    return result;
	}
	
}
