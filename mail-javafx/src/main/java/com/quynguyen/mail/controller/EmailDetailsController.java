package com.quynguyen.mail.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.quynguyen.mail.model.EmailMessageBean;
import com.quynguyen.mail.model.ModelAccess;

import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;


public class EmailDetailsController  extends AbstractController implements Initializable{
	
	
	public EmailDetailsController(ModelAccess modelAccess) {
		super(modelAccess);
	}

	//	private Singleton singleton;
    @FXML
    private WebView webviewEmailDetails;

    @FXML
    private Label labelSubject;

    @FXML
    private Label labelSender;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// WE WILL REMOTE SINGLETON OUT 
//		this.singleton = Singleton.getInstance();
//		this.labelSubject.setText(this.singleton.getMessage().getSubject().get());
//		this.labelSender.setText(this.singleton.getMessage().getSender().get());		
//		this.webviewEmailDetails.getEngine().loadContent(this.singleton.getMessage().getContent());
		System.out.println("EmailDetailsController");
		EmailMessageBean selectedMessage = getModelAccess().getSelectedMessage();
		this.labelSubject.setText(selectedMessage.getSubject());
		this.labelSender.setText(selectedMessage.getSender());		
		this.webviewEmailDetails.getEngine().loadContent(selectedMessage.getContent());
		
		
	}

}
