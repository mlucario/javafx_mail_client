package com.quynguyen.mail.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Folder;

import com.quynguyen.mail.model.folder.EmailFolderBean;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ModelAccess {		 
	
	// will be share between Controller which Extends Abstract class
	private Map<String, EmailAccountBean> emailAccounts = new HashMap<String, EmailAccountBean>();
	private ObservableList<String> emailAccountsNames = FXCollections.observableArrayList();
	
	public ObservableList<String> getEmailAccountNames(){
		return emailAccountsNames;
	}
	
	public EmailAccountBean getEmailAccountByName(String name){
		return emailAccounts.get(name);
	}
	
	public void addAccount(EmailAccountBean account){
		emailAccounts.put(account.getEmailAddress(), account);
		emailAccountsNames.add(account.getEmailAddress());
	}
	
	
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
