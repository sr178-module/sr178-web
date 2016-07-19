package com.sr178.module.web.session;

import java.util.HashMap;
import java.util.Map;

public class Session {
	private String sessionId;
	
	private String userName;
	//上次访问时间
	private long preVisitTime;
	//访问不合格次数
	private int badVisitTimes;
	//登录时间
	private long loginTime;
	//属性表
	private Map<String,Object> attr = new HashMap<String,Object>();
	
	public String getUserName() {
		return userName;
	}
	public long getPreVisitTime() {
		return preVisitTime;
	}
	public void setPreVisitTime(long preVisitTime) {
		this.preVisitTime = preVisitTime;
	}
	public long getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}
	public int getBadVisitTimes() {
		return badVisitTimes;
	}
	public void setBadVisitTimes(int badVisitTimes) {
		this.badVisitTimes = badVisitTimes;
	}
	public void increaseBadVisitTimes(){
		badVisitTimes++;
	}
	/**
	 * @param userName
	 * @param preVisitTime
	 * @param badVisitTimes
	 * @param loginTime
	 */
	public Session(String userName, long loginTime,String sessionId) {
		super();
		this.userName = userName;
		this.preVisitTime = loginTime;
		this.loginTime = loginTime;
		this.sessionId = sessionId;
	}
	/**
	 * 
	 */
	public Session() {
	}
	@Override
	public String toString() {
		return "Session [sessionId=" + sessionId + ", userName=" + userName + ", preVisitTime=" + preVisitTime
				+ ", badVisitTimes=" + badVisitTimes + ", loginTime=" + loginTime + "]";
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setAttr(String key,Object value){
		attr.put(key, value);
	}
	
	public Object getAttr(String key){
		return attr.get(key);
	}
	
	public String getStringAttr(String key){
		return (String)attr.get(key);
	}
	
	public Integer getIntAttr(String key){
		if(attr.containsKey(key)){
			return (Integer)attr.get(key);
		}
		return null;
	}
	
	public Double getDoubleAttr(String key){
		if(attr.containsKey(key)){
			return (Double)attr.get(key);
		}
		return null;
	}
}
