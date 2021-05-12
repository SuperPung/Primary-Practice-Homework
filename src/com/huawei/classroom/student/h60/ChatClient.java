package com.huawei.classroom.student.h60;

public class ChatClient {

	 /**
	  * 根据情况适当抛出异常 
	  * @param ip
	  * @param port
	  */
	public ChatClient (String ip, int port) {
		
	}
	/**
	 * 登录,成功返回true，否则返回false，根据情况适当抛出异常 
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public boolean login(String userName,String password) {
		return false;
	}
	/**
	 * 退出，根据情况适当抛出异常 
	 */
	public void logout() {
		
	}
	/**
	 * 发言, 只有登录以后才能发言， 根据情况适当抛出异常 
	 * 如果没有登录 抛出异常
	 *  
	 * @param str
	 */
	public void speak(String str) {
		 
	}
	/**
	 * 读取聊天室目前的发言，根据情况适当抛出异常 
	 * 只有登录以后才可以读到,否则返回null
	 * 得到聊天室里面所有的发言（包括自己的），如果此时没有发言则立刻返回null，否则每次调用read的时候按队的方式返回一个句话
	 */
	public String read() {
		return null;
		
	}
	
}
