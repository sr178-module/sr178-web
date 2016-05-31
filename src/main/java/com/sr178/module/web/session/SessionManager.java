package com.sr178.module.web.session;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
/**
 * session管理器
 * @author ThinkPad User
 *
 */
public class SessionManager {
	
	

  	private Cache<String,Session> sessionMap = CacheBuilder.newBuilder().expireAfterAccess(24, TimeUnit.HOURS).maximumSize(20000).build();
  	
  	
  	private static final SessionManager manager = new SessionManager();
  	
  	public static SessionManager ins(){
  		return manager;
  	}
  	private SessionManager(){
  		
  	}
  	public Session getSession(String sessionId){
  		return sessionMap.getIfPresent(sessionId);
  	}
  	
  	public void addSession(String sessionId,Session session){
  		sessionMap.put(sessionId, session);
  	}
  	public void removeSession(String sessionId){
  		sessionMap.invalidate(sessionId);
  	}

}
