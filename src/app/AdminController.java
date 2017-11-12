package app;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminController {

	@FXML
	Button quitButton, createUserButton, deleteUserButton, logoutButton;
	
	@FXML
	ListView<User> userListView;
	
	private ObservableList<User> userList;
	private final VBox rootPane;
	
	public AdminController() {
		
		rootPane = new VBox();
	}
	
	
	
	public void start(Stage primaryStage) {
		
		
		userList = FXCollections.observableArrayList();
			
		
	}
	
	
	public Pane getRootPane() {
        return rootPane ;
    }

	
	
	
}
