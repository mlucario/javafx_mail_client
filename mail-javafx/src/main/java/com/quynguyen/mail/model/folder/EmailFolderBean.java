package com.quynguyen.mail.model.folder;

import java.io.IOException;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Flags.Flag;
import javax.mail.internet.MimeMultipart;

import com.quynguyen.mail.model.EmailMessageBean;
import com.quynguyen.mail.view.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class EmailFolderBean<T> extends TreeItem<String> {
	
	private boolean topElement  = false;
	private int unreadMessageCount;
	private String name;
	@SuppressWarnings("unused")
	private String completeName;
	private ObservableList<EmailMessageBean> data = FXCollections.observableArrayList();
	
	public String getName() {
		return this.name;
	}
	
	/**
	 * Constructor for top elements
	 * @param value
	 */
	
	public EmailFolderBean(String value){
		super(value, ViewFactory.defaultFactory.resolveIcon(value));
		this.name = value;
		this.completeName = value;
		data = null;
		topElement = true;
		this.setExpanded(true);
	}
	
	public EmailFolderBean(String value, String compleName){
		super(value, ViewFactory.defaultFactory.resolveIcon(value));
		this.name = value;
		this.completeName = compleName;
	}
	
	private void updateValue(){
		if(unreadMessageCount > 0){
			this.setValue((String)(name + "(" + unreadMessageCount + ")"));
		}else{
			this.setValue(name);
		}
	}
	
	public void incrementUnreadMessagesCount(int newMessages){
		unreadMessageCount = unreadMessageCount + newMessages;
		updateValue();
	}
	
	public void decrementUnreadMessagesCount(){
		unreadMessageCount--;
		updateValue();
	}
	
	public void addEmailDemo(EmailMessageBean message){
		data.add(message);
		if(!message.isRead()){
			incrementUnreadMessagesCount(1);
		}
	}
	
	
	
	public void addEmail(int position, Message message) throws MessagingException, IOException{
		boolean isRead = message.getFlags().contains(Flag.SEEN);
		EmailMessageBean messageBean = new EmailMessageBean(message.getSubject(), 
				message.getFrom()[0].toString(),
				message.getSize(), 
				getTextFromMessage(message), 
				isRead);
		
		data.add(messageBean);
		if(position < 0) {
			data.add(messageBean);
		}else {
			data.add(position,messageBean);
		}
		
		if(!isRead) {
			incrementUnreadMessagesCount(1);
		}
	}
	
	public String getTextFromMessage(Message message) throws MessagingException, IOException {
	    String result = "";
	    if (message.isMimeType("text/plain")) {
	        result = message.getContent().toString();
	    } else if (message.isMimeType("multipart/*")) {
	        MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
	        result = getTextFromMimeMultipart(mimeMultipart);
	    }
	    return result;
	}
	
	private String getTextFromMimeMultipart(
	        MimeMultipart mimeMultipart)  throws MessagingException, IOException{
	    String result = "";
	    int count = mimeMultipart.getCount();
	    for (int i = 0; i < count; i++) {
	        BodyPart bodyPart = mimeMultipart.getBodyPart(i);
	        if (bodyPart.isMimeType("text/plain")) {
	            result = result + "\n" + bodyPart.getContent();
	            break; // without break same text appears twice in my tests
	        } else if (bodyPart.isMimeType("text/html")) {
	            String html = (String) bodyPart.getContent();
	            result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
	        } else if (bodyPart.getContent() instanceof MimeMultipart){
	            result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
	        }
	    }
	    return result;
	}
	
	
	public boolean isTopElement(){
		return topElement;
	}
	
	public ObservableList<EmailMessageBean> getData(){
		return data;
	}
	
	
	
	
	
	
}
