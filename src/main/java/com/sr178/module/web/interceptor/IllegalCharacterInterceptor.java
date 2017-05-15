package com.sr178.module.web.interceptor;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.opensymphony.xwork2.ActionInvocation;
import com.sr178.game.framework.log.LogSystem;
import com.sr178.module.web.action.BaseActionSupport;
import com.sr178.module.web.constant.WebError;
/**
 * 检测sql注入拦截器
 * @author ThinkPad User
 *
 */
public class IllegalCharacterInterceptor extends BaseInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String[] SQL_LIMIT = new String[]{"'","select","update","case","when","drop","alert","and ","or ","exec","execute","insert","sleep","<s","t>"};
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		BaseActionSupport aldAction = (BaseActionSupport) invocation.getAction();
		// sql注入检查
		String checkResult = checkSql(invocation);
		if (checkResult != null) {
			String msg = getMsgTag() + "[userName]=[" + aldAction.getUserName() + "]";
			aldAction.setCode(WebError.GLOAB_ERROR_SQL);
			aldAction.setDesc("非法词提交："+checkResult);
			LogSystem.warn("注入异常==》"+msg+",key=["+checkResult+"]");
			return WebError.GLOAB_ERROR_RESULT;
		}
		return invocation.invoke();
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
