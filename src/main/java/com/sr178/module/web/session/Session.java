package com.sr178.module.web.session;

public class Session {
	private String sessionId;
	
	private String userName;
	//上次访问时间
	private long preVisitTime;
	//访问不合格次数
	private int badVisitTimes;
	//登录时间
	private long loginTime;
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
	
}
