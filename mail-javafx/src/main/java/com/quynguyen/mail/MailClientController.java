package com.quynguyen.mail;

import com.quynguyen.mail.view.ViewFactory;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MailClientController extends Application {

    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));      
//        Scene scene = new Scene(root);
//        scene.getStylesheets().add("/styles/Styles.css");
        
    	ViewFactory viewFactory = ViewFactory.defaultFactory;
    	Scene scene = viewFactory.getMainScene();
//    	stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Mail Client with JavaFX");
        stage.show();
    }  

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	launch(args);
    	
    	
		
       
    }

}
