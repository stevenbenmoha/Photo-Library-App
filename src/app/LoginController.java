package app;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	
	@FXML
	Button quitButton, loginButton;
	@FXML
	TextField usernameTextField;
	
	ObservableList<User> userList;
	
	
	public void start(Stage primaryStage) {
		
		usernameTextField.setEditable(false);
		
		if(usernameTextField.getText() == "admin") {
				
			loginButton.setOnAction(e->  {
				
				AdminController adminPage = new AdminController();
				primaryStage.getScene().setRoot(adminPage.getRootPane());
				
				});			
			
			}
		
		}
		
		
	}
	