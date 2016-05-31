package com.sr178.module.web.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.sr178.game.framework.config.ConfigLoader;
import com.sr178.game.framework.log.LogSystem;
import com.sr178.module.web.action.BaseActionSupport;
import com.sr178.module.web.constant.WebError;
import com.sr178.module.web.session.Session;
/**
 * 用户并发访问速度限制拦截器  默认只支持session在本地的情况
 * @author ThinkPad User
 *
 */
public abstract class AbstractUserVisitSpeedInterceptor extends BaseInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		BaseActionSupport aldAction = (BaseActionSupport) invocation.getAction();
		if(!isCanVisite(aldAction.getUserSession())){
			String msg = getMsgTag() + "[userName]=[" + aldAction.getUserName() + "]";
			aldAction.setCode(WebError.GLOAB_ERROR_VISIT_LIMIT);
			aldAction.setDesc("并发访问限制：");
			LogSystem.warn("并发访问异常==》"+msg+",session="+aldAction.getUserSession());
			afterTrigger(aldAction.getUserSession());
			return WebError.GLOAB_ERROR_RESULT;
		}
		return invocation.invoke();
	}
	
	/**
	 * 连续多次 访问间隔小于500ms  则等待1分钟后才能访问
	 * @param userSession
	 * @return
	 */
	public boolean isCanVisite(Session userSession){
		if(userSession==null){
			return true;
		}
		long minVisitInterVal = ConfigLoader.getLongValue("min_visit_interval", 0l);
		if(minVisitInterVal>0){
			long now = System.currentTimeMillis();
			int maxBadVisitTimes = ConfigLoader.getIntValue("max_bad_visit_times", 5);
			if(userSession.getBadVisitTimes()>maxBadVisitTimes){
				//如果出现多余5次的超标  则要等待1分钟才能进行访问  超过一分钟后    重置次数与上次访问时间
				int stopVisitTime = ConfigLoader.getIntValue("stop_visit_time", 60);
				if(now-userSession.getPreVisitTime()>stopVisitTime*1000){
					userSession.setBadVisitTimes(0);
					userSession.setPreVisitTime(now);
					saveSession(userSession);
					return true;
				}
				return false;
			}
			long visitInterVal = now-userSession.getPreVisitTime();
			if(visitInterVal<minVisitInterVal){
				userSession.increaseBadVisitTimes();
			}else{
				userSession.setBadVisitTimes(0);
			}
			userSession.setPreVisitTime(now);
			saveSession(userSession);
		}
		return true;
	}
	
	public abstract void saveSession(Session userSession);
	
	public abstract void afterTrigger(Session userSession);
}
