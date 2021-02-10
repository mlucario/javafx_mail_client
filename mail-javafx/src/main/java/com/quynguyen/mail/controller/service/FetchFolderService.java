package com.quynguyen.mail.controller.service;

import java.io.IOException;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;

import com.quynguyen.mail.model.EmailAccountBean;
import com.quynguyen.mail.model.ModelAccess;
import com.quynguyen.mail.model.folder.EmailFolderBean;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class FetchFolderService extends Service<Void> {

	private EmailFolderBean<String> folderRoot;
	private EmailAccountBean emailAccountBean;
	private ModelAccess modelAccess;
	private static int NUMBER_OF_FETCH_HOLDER_ACTIVE = 0;

	public FetchFolderService(EmailFolderBean<String> folderRoot, EmailAccountBean emailAccountBean, ModelAccess modelAccess) {
		this.folderRoot = folderRoot;
		this.emailAccountBean = emailAccountBean;
		this.modelAccess = modelAccess;
	}

	@Override
	protected Task<Void> createTask() {

		return new Task<Void>() {

			@Override
			protected Void call() throws Exception {

				if (emailAccountBean != null) {
					Folder[] folders = emailAccountBean.getStore().getDefaultFolder().list();

					for (Folder aFolder : folders) {
						modelAccess.addFoldersList(aFolder);
						
						
						EmailFolderBean<String> item = new EmailFolderBean<>(aFolder.getName(), aFolder.getFullName());
						folderRoot.getChildren().add(item);
						item.setExpanded(true);

						// Turn on listener for javamail
						addMessageListenerToFolder(aFolder, item);

						// Get message for Folder
						FetchMessageOnFolderService fetchMessageOnFolderService = new FetchMessageOnFolderService(item,
								aFolder);
						fetchMessageOnFolderService.start();

						System.out.println("Added : " + aFolder.getName());

						Folder[] subFolders = aFolder.list();
						for (Folder subFolder : subFolders) {
							modelAccess.addFoldersList(subFolder);
							EmailFolderBean<String> subItem = new EmailFolderBean<>(subFolder.getName(),
									subFolder.getFullName());
							item.getChildren().add(subItem);
							addMessageListenerToFolder(subFolder, subItem);
							// Get message for Folder
							FetchMessageOnFolderService fetchMessageOnSubFolderService = new FetchMessageOnFolderService(
									subItem, subFolder);
							fetchMessageOnSubFolderService.start();

							System.out.println("Added subFolder : " + subFolder.getName());
						}
					}

				}

				return null;
			}

		};
	}

	private void addMessageListenerToFolder(Folder folder, EmailFolderBean<String> item) {
		folder.addMessageCountListener(new MessageCountAdapter() {
			@Override
			public void messagesAdded(MessageCountEvent e) {

				for (int i = 0; i < e.getMessages().length; i++) {
					try {
						Message currentMessage = folder.getMessage(folder.getMessageCount() - i);
						item.addEmail(0, currentMessage);
					} catch (MessagingException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}

}
