package com.quynguyen.mail.view;

import java.io.IOException;

import javax.naming.OperationNotSupportedException;

import com.quynguyen.mail.controller.AbstractController;
import com.quynguyen.mail.controller.EmailDetailsController;
import com.quynguyen.mail.controller.MainController;
import com.quynguyen.mail.model.ModelAccess;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ViewFactory {
	
	public static ViewFactory defaultFactory = new ViewFactory();

	private static boolean mainViewInitialized = false;
	
	private static final String FXML_MAIN_FXML = "/fxml/Main.fxml";

	private static final String FXML_EMAIL_DETAILS_LAYOUT_FXML = "/fxml/EmailDetailsLayout.fxml";

	private static final String STYLES_EMAIL_DETAILS_STYLE_CSS = "/styles/EmailDetailsStyle.css";

	private static final String STYLES_STYLES_CSS = "/styles/Styles.css";

	private ModelAccess modelAccess = new ModelAccess();

	private MainController mainController;
	private EmailDetailsController emailDetailsController;

	public Scene getMainScene() throws OperationNotSupportedException {
//		Pane pane;
//		try {
//			pane = FXMLLoader.load(getClass().getResource(FXML_MAIN_FXML));
//		} catch (IOException e) {
//			pane = null;
//			e.printStackTrace();
//		}
//
//		Scene scene = new Scene(pane);
//		scene.getStylesheets().add(STYLES_STYLES_CSS);
//		return scene;

		if(!mainViewInitialized) {
			mainController = new MainController(modelAccess);
			mainViewInitialized = true;
			return initializeScene(FXML_MAIN_FXML, mainController);
		}else {
			throw new OperationNotSupportedException("Main Scene already initialized!!!");
		}
		
		
	}

	public Scene getEmailDetailsScene() {
//		Pane pane;
//		try {
//			pane = FXMLLoader.load(getClass().getResource(FXML_EMAIL_DETAILS_LAYOUT_FXML));
//		} catch (IOException e) {
//			pane = null;
//			e.printStackTrace();
//		}
//		Scene scene = new Scene(pane);
//		scene.getStylesheets().add(STYLES_EMAIL_DETAILS_STYLE_CSS);
//		return scene;
		
		emailDetailsController = new EmailDetailsController(modelAccess);
		return initializeScene(FXML_EMAIL_DETAILS_LAYOUT_FXML, emailDetailsController);
	}

	// Set controller for View FXML
	private Scene initializeScene(String fxmlPath, AbstractController abstractController) {
		FXMLLoader loader;
		Parent parent;
		Scene scene;

		try {
			loader = new FXMLLoader(getClass().getResource(fxmlPath));
			loader.setController(abstractController);
			parent = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		scene = new Scene(parent);
		scene.getStylesheets().add(STYLES_STYLES_CSS);
		return scene;
	}

	// Get Item from our resources
	public Node resolveIcon(String treeItemValue) {
		String lowerCaseTreeItemValue = treeItemValue.toLowerCase();
		ImageView returnIcon;
		try {
			if (lowerCaseTreeItemValue.contains("inbox")) {
				returnIcon = new ImageView(
						new Image(getClass().getClassLoader().getResourceAsStream("images/inbox.png")));
			} else if (lowerCaseTreeItemValue.contains("sent")) {
				returnIcon = new ImageView(
						new Image(getClass().getClassLoader().getResourceAsStream("images/sent2.png")));
			} else if (lowerCaseTreeItemValue.contains("spam")) {
				returnIcon = new ImageView(
						new Image(getClass().getClassLoader().getResourceAsStream("images/spam.png")));
			} else if (lowerCaseTreeItemValue.contains("@")) {
				returnIcon = new ImageView(
						new Image(getClass().getClassLoader().getResourceAsStream("images/email.png")));
			} else {
				returnIcon = new ImageView(
						new Image(getClass().getClassLoader().getResourceAsStream("images/folder.png")));
			}
		} catch (NullPointerException e) {
			System.out.println("Invalid image location!!!");
			e.printStackTrace();
			returnIcon = new ImageView();
		}

		returnIcon.setFitHeight(16);
		returnIcon.setFitWidth(16);

		return returnIcon;
	}
}
