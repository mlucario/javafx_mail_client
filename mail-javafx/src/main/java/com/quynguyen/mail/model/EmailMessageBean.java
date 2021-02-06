package com.quynguyen.mail.model;

import java.util.HashMap;
import java.util.Map;

import com.quynguyen.mail.model.table.AbstractTableItem;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmailMessageBean extends AbstractTableItem{
	 
	public static Map<String, Integer> formattedValues = new HashMap<>();
	
	private StringProperty sender;
	private StringProperty subject;
	private StringProperty size;	
	private String content;
	private boolean isRead;
	
	
	public EmailMessageBean(String Subject, String Sender, int size, String Content, boolean isRead){
		super(isRead);
		this.subject = new SimpleStringProperty(Subject);
		this.sender = new SimpleStringProperty(Sender);
		this.size = new SimpleStringProperty(formatSize(size));
		this.content = Content;
	}
	
	
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
	
	public boolean isRead() {
		return this.isRead;
	}
	
	public String getSender(){
		return sender.get();
	}
	public String getSubject(){
		return subject.get();
	}
	public String getSize(){
		return size.get();
	}
	public String getContent(){
		return content;
	}

	
	private String formatSize(int size) {
		String returnValue;
		if(size <= 0) {
			returnValue = "0";
		}else if(size < 1024) {
			returnValue = size + " B";
		}else if(size < 1048576) {
			returnValue = size/1024 + " KB";
		}else {
			returnValue = size/1048576 + " MB";
		}
		formattedValues.put(returnValue, size);
		return returnValue;
	}
}
