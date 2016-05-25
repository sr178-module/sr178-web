package com.sr178.module.web.interceptor;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;


import com.opensymphony.xwork2.ActionInvocation;
import com.sr178.game.framework.exception.ServiceException;
import com.sr178.game.framework.log.LogSystem;
import com.sr178.module.web.action.BaseActionSupport;
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
	
	private static final String[] SQL_LIMIT = new String[]{"'","select","update","case","when","drop","alert"};

	public String intercept(ActionInvocation invocation) throws Exception {
		
		BaseActionSupport aldAction = (BaseActionSupport) invocation.getAction();
		// sql注入检查
		String checkResult = checkSql(invocation);
		if (checkResult != null) {
			String msg = getMsgTag() + "[userName]=[" + aldAction.getUserName() + "]";
			aldAction.setCode(WebError.GLOAB_ERROR_SQL);
			aldAction.setDesc("非法词提交："+checkResult);
			LogSystem.warn("注入异常==》"+msg);
			return WebError.GLOAB_ERROR_RESULT;
		}
		
		if(!aldAction.isCanVisite()){
			String msg = getMsgTag() + "[userName]=[" + aldAction.getUserName() + "]";
			aldAction.setCode(WebError.GLOAB_ERROR_VISIT_LIMIT);
			aldAction.setDesc("并发访问限制：");
			LogSystem.warn("并发访问异常==》"+msg+",session="+aldAction.getUserSession());
			return WebError.GLOAB_ERROR_RESULT;
		}
		
		try {
			return invocation.invoke();
		} catch (Exception e) {
			String msg = getMsgTag() + "[userName]=[" + aldAction.getUserName() + "]";
			if (e instanceof ServiceException) {
				ServiceException exception = (ServiceException) e;
				aldAction.setCode(exception.getCode());
				aldAction.setDesc(exception.getMessage());
				LogSystem.info(msg + "error=【" + exception.toString() + "】");
				//如果设置了错误界面  则返回指定的错误界面，没有的话直接跳到全局错误界面
				if (aldAction.getErrorResult() == null) {
					return WebError.GLOAB_ERROR_RESULT;
				} else {
					return aldAction.getErrorResult();
				}
			} else {
				aldAction.setCode(WebError.GLOAB_ERROR_CODE);
				aldAction.setDesc("不可预知的错误！");
				LogSystem.error(e, msg + ",发生了不可预知的异常");
				return WebError.GLOAB_ERROR_RESULT;
			}
		}
	}
	
	private String checkSql(ActionInvocation invocation) {
		Map<String, Object> map = invocation.getInvocationContext().getParameters();
		Set<String> keys = map.keySet();
		Iterator<String> iters = keys.iterator();
		while (iters.hasNext()) {
			String key = iters.next();
			Object value = map.get(key);
			String result = transactSQLInjection((String[]) value);
			if(result!=null){
				return result;
			}
		}
		return null;
	}
	
	public String transactSQLInjection(String[] str)
	{
		for(int i=0;i<str.length;i++){
			for(int j=0;j<SQL_LIMIT.length;j++){
				if(str[i].indexOf(SQL_LIMIT[j])!=-1||(str[i].indexOf(SQL_LIMIT[j].toUpperCase())!=-1)){
					return str[i];
				}
			}
		}
	    return null;
	}
	
}
