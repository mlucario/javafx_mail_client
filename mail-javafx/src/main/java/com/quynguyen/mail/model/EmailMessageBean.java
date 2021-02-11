package com.quynguyen.mail.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;

import com.quynguyen.mail.model.table.AbstractTableItem;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmailMessageBean extends AbstractTableItem {

	public static Map<String, Integer> formattedValues = new HashMap<>();

	private StringProperty sender;
	private StringProperty subject;
	private StringProperty size;
	private boolean isRead;
	private Message messageReference;
	private SimpleObjectProperty<Date> date;

	// Attachments handling
	private List<MimeBodyPart> attachmentsList = new ArrayList<MimeBodyPart>();
	private StringBuffer attachmentsNames = new StringBuffer();

	public EmailMessageBean(String Subject, String Sender, int size, boolean isRead, Date date,
			Message messageReference) {
		super(isRead);
		this.subject = new SimpleStringProperty(Subject);
		this.sender = new SimpleStringProperty(Sender);
		this.size = new SimpleStringProperty(formatSize(size));
		this.messageReference = messageReference;
		this.date = new SimpleObjectProperty<Date>(date);
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public boolean isRead() {
		return this.isRead;
	}

	public String getSender() {
		return sender.get();
	}

	public String getSubject() {
		return subject.get();
	}

	public String getSize() {
		return size.get();
	}

	public Message getMessageReference() {
		return messageReference;
	}

	public Date getDate() {
		return date.get();
	}

	private String formatSize(int size) {
		String returnValue;
		if (size <= 0) {
			returnValue = "0";
		} else if (size < 1024) {
			returnValue = size + " B";
		} else if (size < 1048576) {
			returnValue = size / 1024 + " KB";
		} else {
			returnValue = size / 1048576 + " MB";
		}
		formattedValues.put(returnValue, size);
		return returnValue;
	}

	public List<MimeBodyPart> getAttachmentsList() {
		return attachmentsList;
	}

	public String getAttachmentsNames() {
		return attachmentsNames.toString();
	}

	public void addAttachment(MimeBodyPart mbp) {
		attachmentsList.add(mbp);
		try {
			attachmentsNames.append(mbp.getFileName() + "; ");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public boolean hasAttachments() {
		return attachmentsList.size() > 0;
	}

	// clear methods:
	public void clearAttachments() {
		attachmentsList.clear();
		attachmentsNames.setLength(0);
	}

	@Override
	public String toString() {
		return String.format("EmailMessageBean [sender=%s, subject=%s, size=%s, isRead=%s, messageReference=%s]",
				sender, subject, size, isRead, messageReference);
	}

}
