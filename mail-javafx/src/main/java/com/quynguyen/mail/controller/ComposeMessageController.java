package com.quynguyen.mail.controller;



import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.quynguyen.mail.controller.service.EmailSenderService;
import com.quynguyen.mail.model.EmailStatesConstants;
import com.quynguyen.mail.model.ModelAccess;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;

public class ComposeMessageController extends AbstractController implements Initializable{
	
	private List<File> attachments = new  ArrayList<File>();
	

    @FXML
    private Label attachmentsLable;

    @FXML
    private ChoiceBox<String> senderChoice;

    @FXML
    private TextField recipientField;

    @FXML
    private TextField subjectField;
    
    @FXML
    private HTMLEditor composeArea;

    @FXML
    private Label errorLabel;

    @FXML
    void attchBtnAction() {
    	
    	FileChooser fileChooser = new FileChooser();
    	File selectedFile = fileChooser.showOpenDialog(null);
    	if(selectedFile != null){
    		attachments.add(selectedFile);
    		attachmentsLable.setText(attachmentsLable.getText() + selectedFile.getName() + "; ");
    	}

    }

    @FXML
    void sendBtnAction() {
    	errorLabel.setText("");
    	EmailSenderService emailSenderService = 
    			new EmailSenderService(getModelAccess().getEmailAccountByName(senderChoice.getValue()),
    					subjectField.getText(), 
    					recipientField.getText(),
    					composeArea.getHtmlText(),
    					attachments);
    	emailSenderService.restart();
    	emailSenderService.setOnSucceeded(e->{
    		if(emailSenderService.getValue() == EmailStatesConstants.MESSAGE_SENT_OK){
    			errorLabel.setText("message sent successefully");
    		}else{
    			errorLabel.setText("message sending error!!!");
    		}
    	});
    	
    	
    	
    	
    	

    }
	
	

	public ComposeMessageController(ModelAccess modelAccess) {
		super(modelAccess);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		senderChoice.setItems(getModelAccess().getEmailAccountNames());
		senderChoice.setValue(getModelAccess().getEmailAccountNames().get(0));

		
	}

}
