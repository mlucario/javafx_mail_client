package com.quynguyen.mail.controller.service;

import com.quynguyen.mail.model.EmailAccountBean;
import com.quynguyen.mail.model.EmailStatesConstants;
import com.quynguyen.mail.model.ModelAccess;
import com.quynguyen.mail.model.folder.EmailFolderBean;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class CreateAndRegisterEmailAccountService extends Service<Integer>{

	
	private String emailAddress;
	private String password;
	private EmailFolderBean<String> folderRoot;
	private ModelAccess modelAccess;
	
	
	
	public CreateAndRegisterEmailAccountService(String emailAddress, String password,
			EmailFolderBean<String> folderRoot, ModelAccess modelAccess) {
		this.emailAddress = emailAddress;
		this.password = password;
		this.folderRoot = folderRoot;
		this.modelAccess = modelAccess;
	}




	@Override
	protected Task<Integer> createTask() {
		return new Task<Integer>() {

			@Override
			protected Integer call() throws Exception {
				
				EmailAccountBean emailAccount = new EmailAccountBean(emailAddress, password);
				// If login successfully
				// We set Folder Root with Same Email Address
				if(emailAccount.getLoginState() == EmailStatesConstants.LOGIN_STATE_SUCCEDED) {
					EmailFolderBean<String> emailFolderBean = new EmailFolderBean<String>(emailAddress);
					// This will go to Folder Root ( On the top of Tree )					
					folderRoot.getChildren().add(emailFolderBean);
					
					// We fetch Email In here later
					FetchFolderService fetchFolderService = new FetchFolderService(folderRoot, emailAccount,modelAccess);
					fetchFolderService.start();
					
				}
				return emailAccount.getLoginState();
			}
			
		};
	}

}
