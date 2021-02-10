package com.quynguyen.mail.model;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Folder;

import com.quynguyen.mail.model.folder.EmailFolderBean;

public class ModelAccess {		 
	
	// will be share between Controller which Extends Abstract class
	
	
	private EmailMessageBean selectedMessage;

	public EmailMessageBean getSelectedMessage() {
		return selectedMessage;
	}

	public void setSelectedMessage(EmailMessageBean selectedMessage) {
		this.selectedMessage = selectedMessage;
	}
	
	
	
	
	private EmailFolderBean<String> selectedFolder;
	public EmailFolderBean<String> getSelectedFolder() {
		return selectedFolder;
	}

	public void setSelectedFolder(EmailFolderBean<String> selectedFolder) {
		this.selectedFolder = selectedFolder;
	}


	private List<Folder> foldersList = new ArrayList<Folder>();

	public List<Folder> getFoldersList() {
		return foldersList;
	}

	public void addFoldersList(Folder aFolder) {
		this.foldersList.add(aFolder);
	}
	
	
}
