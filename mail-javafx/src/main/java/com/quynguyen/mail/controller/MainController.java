package com.quynguyen.mail.controller;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeView;
import com.quynguyen.mail.controller.service.CreateAndRegisterEmailAccountService;
import com.quynguyen.mail.controller.service.FolderUpdaterService;
import com.quynguyen.mail.model.EmailAccountBean;
import com.quynguyen.mail.model.EmailMessageBean;
import com.quynguyen.mail.model.ModelAccess;
import com.quynguyen.mail.model.folder.EmailFolderBean;
import com.quynguyen.mail.model.table.BoldableRowFactory;
import com.quynguyen.mail.view.ViewFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class MainController extends AbstractController implements Initializable {

	public MainController(ModelAccess modelAccess) {
		super(modelAccess);
	}

	private TreeItem<String> rootTreeView = new TreeItem<String>();
//	private SampleData sampleData = new SampleData();
	private MenuItem showDetails = new MenuItem("show details"); // show detail when we do right click
//	private Singleton singleton;
	@FXML
	private JFXTreeView<String> treeViewEmailFolders;

	@FXML
	private TableView<EmailMessageBean> emailTableView;

	@FXML
	private TableColumn<EmailMessageBean, String> subjectCol;

	@FXML
	private TableColumn<EmailMessageBean, String> senderCol;

	@FXML
	private TableColumn<EmailMessageBean, String> sizeCol;

	@FXML
	private WebView messageRenderer;

	@FXML
	private JFXButton btnOne;

	@FXML
	private JFXButton btnTwo;

	@FXML
	private JFXButton btnThree;

	@FXML
	void ButtonOneAction(ActionEvent event) {

		Service<Void> emailService = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						ObservableList<EmailMessageBean> data = getModelAccess().getSelectedFolder().getData();
//				    	
//						emailAccountBean.addEmailsToData(data);
						return null;
					}

				};
			}

		};
		emailService.start();

	}

	@FXML
	void changeReadAction() {
		EmailMessageBean message = getModelAccess().getSelectedMessage();
		if (message != null) {
			boolean value = message.isRead();
			message.setRead(!value);
			EmailFolderBean<String> selectedFolder = getModelAccess().getSelectedFolder();
			if (selectedFolder != null) {
				if (value) {
					selectedFolder.incrementUnreadMessagesCount(1);
				} else {
					selectedFolder.decrementUnreadMessagesCount();
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// start check FolderUpdaterService
		FolderUpdaterService folderUpdaterService = new FolderUpdaterService(getModelAccess().getFoldersList());
		folderUpdaterService.start();
		
		ViewFactory viewFactory = ViewFactory.defaultFactory;
		// NODE advantaged skill : Dynamic CSS style
		this.emailTableView.setRowFactory(e -> new BoldableRowFactory<EmailMessageBean>());
		// Display Table View
		// Step 1 : Generate Col with Data Object
		subjectCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("subject"));
		senderCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("sender"));
		sizeCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("size"));

		// Step 1.1 : We can create SORT for each COL
		sizeCol.setComparator(new Comparator<String>() {
			Integer int1, int2;

			@Override
			public int compare(String o1, String o2) {
				int1 = EmailMessageBean.formattedValues.get(o1);
				int2 = EmailMessageBean.formattedValues.get(o2);
				return int1.compareTo(int2);
			}
		});

		// Step 1.2: Set up size for each Column
		this.emailTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		senderCol.setMaxWidth(1f * Integer.MAX_VALUE * 40);
		subjectCol.setMaxWidth(1f * Integer.MAX_VALUE * 40);
		sizeCol.setMaxWidth(1f * Integer.MAX_VALUE * 20);

		EmailFolderBean<String> root = new EmailFolderBean<>("");
		treeViewEmailFolders.setRoot(root);
		treeViewEmailFolders.setShowRoot(false);

		CreateAndRegisterEmailAccountService createAndRegisterEmailAccountService1 = 
				new CreateAndRegisterEmailAccountService(
				"nhonquy2019@gmail.com", "RQaVi2sHN6PfZcy", root, getModelAccess());

		createAndRegisterEmailAccountService1.start();

		CreateAndRegisterEmailAccountService createAndRegisterEmailAccountService2 = 
				new CreateAndRegisterEmailAccountService(
				"huong.sj.2017@gmail.com", "12345678Aa", root, getModelAccess());

		createAndRegisterEmailAccountService2.start();
		// FetchFolderService Already Handle lines of code
//		EmailFolderBean<String> quynguyen = new EmailFolderBean<>("example@yahoo.com");
//		root.getChildren().add(quynguyen);
//		EmailFolderBean<String> Inbox = new EmailFolderBean<>("Inbox", "CompleteInbox");
//		EmailFolderBean<String> Sent = new EmailFolderBean<>("Sent", "CompleteSent");
//			Sent.getChildren().add(new EmailFolderBean<>("Subfolder1", "SubFolder1Complete"));
//			Sent.getChildren().add(new EmailFolderBean<>("Subfolder2", "SubFolder1Complete2"));
//		EmailFolderBean<String> Spam = new EmailFolderBean<>("Spam", "CompleteSpam");
//		quynguyen.getChildren().addAll(Inbox, Sent, Spam);
//		Inbox.getData().addAll(SampleData.Inbox);
//		Sent.getData().addAll(SampleData.Sent);
//		Spam.getData().addAll(SampleData.Spam);

		// CREATE TREEVIEW FOR EMAIL FOLDERS
//		treeViewEmailFolders.setRoot(rootTreeView);
//		rootTreeView.setValue("Your_Email@yahoo.com");
//		rootTreeView.setGraphic(viewFactory.resolveIcon(rootTreeView.getValue()));
//
//		TreeItem<String> inboxFolder = new TreeItem<>("Inbox", viewFactory.resolveIcon("Inbox"));
//
//		TreeItem<String> sentFolder = new TreeItem<>("Sent", viewFactory.resolveIcon("Sent"));
//		TreeItem<String> subItem1 = new TreeItem<>("Sub Item 1", viewFactory.resolveIcon("Subitem1"));
//		TreeItem<String> subItem2 = new TreeItem<>("Sub Item 2", viewFactory.resolveIcon("Subitem2"));
//		sentFolder.getChildren().addAll(subItem1, subItem2);
//
//		TreeItem<String> spamFolder = new TreeItem<>("Spam", viewFactory.resolveIcon("Spam"));
//
//		TreeItem<String> trashFolder = new TreeItem<>("Trash", viewFactory.resolveIcon("Trash"));
//
//		rootTreeView.setExpanded(true);
//		rootTreeView.getChildren().addAll(inboxFolder, sentFolder, spamFolder, trashFolder);

		// left-side action
		this.treeViewEmailFolders.setOnMouseClicked(e -> {
			// NOTE Get the selected item with .getSelectionModel().getSelectedItem();
//			TreeItem<String> selectedItem = this.treeViewEmailFolders.getSelectionModel().getSelectedItem();
//
//			if (selectedItem != null) {
//				this.emailTableView.setItems(sampleData.emailFolders.get(selectedItem.getValue()));
//			}

			EmailFolderBean<String> item = (EmailFolderBean<String>) treeViewEmailFolders.getSelectionModel()
					.getSelectedItem();
			if (item != null && !item.isTopElement()) {
				emailTableView.setItems(item.getData());
				getModelAccess().setSelectedFolder(item);
				// clear the selected message:
				getModelAccess().setSelectedMessage(null);
			}

		});

		this.emailTableView.setOnMouseClicked(e -> {
			EmailMessageBean message = this.emailTableView.getSelectionModel().getSelectedItem();
			if (message != null) {
				getModelAccess().setSelectedMessage(message);
				messageRenderer.getEngine().loadContent(message.getContent());
			}
		});

		// NOTE We may set default Load Inbox
//		this.emailTableView.setItems(SampleData.Inbox);

		// LOAD Context Menu
		this.emailTableView.setContextMenu(new ContextMenu(showDetails));
		showDetails.setOnAction(e -> {
			Scene scene = viewFactory.getEmailDetailsScene();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Email Details");
			stage.setScene(scene);
			stage.show();
		});
//		messageRenderer.getEngine().load("http://www.google.com");
	}

}
