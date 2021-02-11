package com.quynguyen.mail.controller.service;

import java.util.List;

import javax.mail.Folder;


import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class FolderUpdaterService extends Service<Void>{
	private List<Folder> foldersList;
	
	
	public FolderUpdaterService(List<Folder> foldersList) {
		this.foldersList = foldersList;
	}
	
	@Override
	protected Task<Void> createTask() {
	
		return new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				for(;;) {
					try {
						Thread.sleep(10000);  //10 seconds
						System.out.println("Auto Re-Fetch in 10s . . .");
						if (FetchFolderService.noServicesActive()) {
							for (Folder folder : foldersList) {
								if (folder.getType() != Folder.HOLDS_FOLDERS && folder.isOpen()) {
									folder.getMessageCount();
								}
							} 
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
			
		};
		
	}

}
