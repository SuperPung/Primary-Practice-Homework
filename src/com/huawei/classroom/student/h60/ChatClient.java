package com.huawei.classroom.student.h60;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author super
 */
public class ChatClient {
	private boolean isLoggedIn;
	private final PrintWriter out;
	private final BufferedReader in;

	 /**
	  * 根据情况适当抛出异常 
	  * @param ip
	  * @param port
	  */
	public ChatClient (String ip, int port) throws IOException {
		Socket client = new Socket(ip, port);
		// 获得输出流
		out = new java.io.PrintWriter(client.getOutputStream());
		// 获得输入流
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
	}
	/**
	 * 登录,成功返回true，否则返回false，根据情况适当抛出异常 
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public boolean login(String userName,String password) throws IOException {
		String passwd = userName + "\t" + password;
		String get;
		// \r\n is necessary
		out.write(passwd + "\r\n");
		out.flush();
		get = in.readLine();
		isLoggedIn = "1".equals(get);
		return isLoggedIn;
	}
	/**
	 * 退出，根据情况适当抛出异常 
	 */
	public void logout() {
		isLoggedIn = false;
	}
	/**
	 * 发言, 只有登录以后才能发言， 根据情况适当抛出异常 
	 * 如果没有登录 抛出异常
	 *  
	 * @param str
	 */
	public void speak(String str) throws IOException {
		 if (!isLoggedIn) {
		 	throw new IOException("Haven't logged in!");
		 }
		 out.write(str + "\r\n");
		 out.flush();
	}
	/**
	 * 读取聊天室目前的发言，根据情况适当抛出异常 
	 * 只有登录以后才可以读到,否则返回null
	 * 得到聊天室里面所有的发言（包括自己的），如果此时没有发言则立刻返回null，否则每次调用read的时候按队的方式返回一个句话
	 */
	public String read() throws IOException {
		if (!isLoggedIn) {
			return null;
		}
		return in.readLine();
	}
	
}
