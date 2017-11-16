package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import controller.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Load implements Serializable {
	
	public static final String photos71 = "photos71";
	public static final String src = "src";
	public static final String model = "model";
	public static final String userListFile = "userListFile.dat";
	
	@FXML
	protected Button quitButton, logoutButton;
	
	protected ObservableList<User> userList = FXCollections.observableArrayList();
	
		
	public static ObservableList<User> readFile() {
        try {
            
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(src+ File.separator+ model + File.separator + userListFile));
            ArrayList<User> userList = (ArrayList<User>) ois.readObject();
            return FXCollections.observableList(userList);
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FXCollections.emptyObservableList();
    }
	
	public static void write(ObservableList<User> userList) {
	 try {
		 
            // write object to files
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(src+File.separator+model+File.separator+userListFile));
            oos.writeObject(new ArrayList<User>(userList));
            oos.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	
}
	
	@FXML
	public void quitProgram(ActionEvent event)
	{
		Stage stage = (Stage)quitButton.getScene().getWindow();
		stage.close();
	}
	@FXML
	public void logout(ActionEvent event) throws IOException
	{
		Stage stage;
		stage = (Stage)logoutButton.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/login.fxml"));
		VBox root = (VBox)loader.load();
		LoginController controller = loader.getController();
		controller.start(stage);
		stage.setResizable(true);
		stage.setTitle("Photo Library");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
}