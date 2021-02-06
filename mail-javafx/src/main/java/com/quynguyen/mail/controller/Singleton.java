package com.quynguyen.mail.controller;

import com.quynguyen.mail.model.EmailMessageBean;

public class Singleton {
	
	
	private  Singleton() {	}
	private static Singleton instance = new Singleton();
	
	
	public static Singleton getInstance() {
		return instance;
	}


	private EmailMessageBean message;
	public EmailMessageBean getMessage() {
		return message;
	}


	public void setMessage(EmailMessageBean message) {
		this.message = message;
	}
	
	
	
	
	 
}
