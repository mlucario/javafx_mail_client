package com.quynguyen.mail.model;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;

import javafx.collections.ObservableList;

public class EmailAccountBean {

	private String emailAddress;
	private String password;
	private Properties properties;
	private Store store;
	private Session session;
	private int loginState = EmailStatesConstants.LOGIN_STATE_NOT_READY;

	
	public String getEmailAddress() {
		return emailAddress;
	}

	public Properties getProperties() {
		return properties;
	}

	public Store getStore() {
		return store;
	}

	public Session getSession() {
		return session;
	}

	public int getLoginState() {
		return loginState;
	}
	
	public EmailAccountBean(String emailAddress, String password){
		loginState = EmailStatesConstants.LOGIN_STATE_NOT_READY;		
		try {
			this.emailAddress = emailAddress;
			this.password = password;
			
			properties = new Properties();
			properties.put("mail.store.protocol", "imaps");
			properties.put("mail.transport.protocol", "smtps");
			properties.put("mail.smtps.host", "smtp.gmail.com");
			properties.put("mail.smtps.auth", "true");
			properties.put("incomingHost", "imap.gmail.com");
			properties.put("outgoingHost", "smtp.gmail.com");
			

			  
			Authenticator auth = new Authenticator(){
				@Override
				protected PasswordAuthentication getPasswordAuthentication(){
					return new PasswordAuthentication(emailAddress, password);
				}			
			};
			
			//Connecting:
			session = Session.getInstance(properties, auth);
			this.store = session.getStore();
			store.connect(properties.getProperty("incomingHost"), this.emailAddress, this.password);
			System.out.println("EmailAccountBean constructed succesufully!!!");
			loginState = EmailStatesConstants.LOGIN_STATE_SUCCEDED;			
			
		}catch (NoSuchProviderException e) {
			loginState = EmailStatesConstants.LOGIN_STATE_FAILED_BY_CREDENTIALS;
			e.printStackTrace();
		} catch (MessagingException e) {
			loginState = EmailStatesConstants.LOGIN_STATE_FAILED_BY_CREDENTIALS;
			e.printStackTrace();
		} catch (Exception e) {
			loginState = EmailStatesConstants.LOGIN_STATE_FAILED_BY_NETWORK;
			e.printStackTrace();
		}
      
		
	}
	

	
	public void addEmailsToDataDemoConsole(ObservableList<EmailMessageBean> data){
		try {
			System.out.println("Thread that is fetching emails: " + Thread.currentThread().getName());
			
			
			Folder inbox = store.getFolder("INBOX");
//			folder.open(Folder.READ_WRITE);
			inbox.open(Folder.READ_ONLY);
			
			
			Message[] messages = inbox.search(new FlagTerm(new Flags(Flag.RECENT), false));
			System.out.println("messages.length---" + messages.length);
		
			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];
				String content  = getTextFromMessage(message).trim();
				System.out.println("---------------------------------");
				System.out.println("Email Number " + (i + 1));
				System.out.println("Subject: " + message.getSubject());
				System.out.println("From: " + message.getFrom()[0]);
				System.out.println("Text: " + content);
				EmailMessageBean messageBean = new EmailMessageBean(message.getSubject(), 
						message.getFrom()[0].toString(),
						message.getSize(), 
						content, 
						message.getFlags().contains(Flag.SEEN));
				data.add(messageBean);
				System.out.println("GOT :  "+ messageBean.toString() );
			}
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void setEmailStatus(Message message, boolean isReaded) throws MessagingException {
		message.setFlag(Flag.SEEN, isReaded);		
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
}
