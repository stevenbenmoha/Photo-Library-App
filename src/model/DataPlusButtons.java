package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;

import controller.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DataPlusButtons implements Serializable {
	
	public static final String photos71 = "photos71";
	public static final String src = "src";
	public static final String model = "model";
	public static final String userListFile = "userListFile.dat";
	public static final String photoLibraryFile = "photoLibraryFile.dat";
	public static final String currentUser = "currentUserFile.dat";
	
	protected static User u;
	public static File albumFile;
	public static File userFile = new File("src\\model\\userListFile.dat");
	public static File currentUserFile = new File("src\\model\\currentUserFile.dat");
	
	
	
	@FXML
	protected Button quitButton, logoutButton, searchButton, returnToAlbumsButton;
	
	protected static ObservableList<User> userList = FXCollections.observableArrayList();
	
	protected static ObservableList<Album> photoLibrary = FXCollections.observableArrayList();
	
		
	@FXML
	protected void quitProgram(ActionEvent event)
	{
		Stage stage = (Stage)quitButton.getScene().getWindow();
		stage.close();
	}
	@FXML
	protected void logout(ActionEvent event) throws IOException
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
	
	@FXML
	protected void search(ActionEvent event) throws IOException
	{
		Stage stage;
		stage = (Stage)searchButton.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/search.fxml"));
		VBox root = (VBox)loader.load();
		SearchController controller = loader.getController();
		controller.start(stage);
		stage.setResizable(true);
		stage.setTitle("Photo Library");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	

	@FXML
	protected void returnToAlbums(ActionEvent event) throws IOException {
		
	  Stage stage;
	  stage = (Stage)logoutButton.getScene().getWindow(); 
	  FXMLLoader loader = new FXMLLoader();
	  loader.setLocation(getClass().getResource("/view/main.fxml"));
	  VBox root = (VBox)loader.load();
	  PhotoLibraryController controller = loader.getController();
	  controller.start(stage);
	  stage.setResizable(true);
	  stage.setTitle("Photo Library"); Scene scene = new Scene(root);
	  stage.setScene(scene); stage.show();
	  }

	

	
	public static ObservableList<User> readUserFile() {
		
		if(userFile.exists()) {
		
        try {
            
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(src+ File.separator+ model + File.separator + userListFile));
            ArrayList<User> userList = (ArrayList<User>) ois.readObject();
            return FXCollections.observableList(userList);
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		}
        return FXCollections.observableList(userList);
        
    }
	
	public static void writeUser(ObservableList<User> userList) {
	 try {
		 	
		 	userFile.createNewFile(); // if file already exists will do nothing 
		 	FileOutputStream output = new FileOutputStream(userFile, false);
		 
            // write object to files
            ObjectOutputStream oos = new ObjectOutputStream(output);
            oos.writeObject(new ArrayList<User>(userList));
            oos.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	
}
	
	
	public static ObservableList<Album> readAlbumFile(User u) {
		
		albumFile = new File("src\\model\\userdata\\"+u.getName()+"Albums.dat");
		
		
		if (albumFile.exists() && !getFileExtension(albumFile).equals("txt") ) {
		
		 try {
            
			 FileInputStream input = new FileInputStream(albumFile); 
			 
            ObjectInputStream ois = new ObjectInputStream(input);
            ArrayList<Album> userPhotoLibrary = (ArrayList<Album>) ois.readObject();
            return FXCollections.observableList(userPhotoLibrary);
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		}
		
        return FXCollections.observableList(photoLibrary);
    }
	
	public static void writeAlbum(User u, ObservableList<Album> userPhotoLibrary) {
		 try {
			 
			 	File albumFile = new File("src\\model\\userdata\\"+u.getName()+"Albums.dat");
			 	albumFile.createNewFile(); // if file already exists will do nothing 
			 	FileOutputStream output = new FileOutputStream(albumFile, false);
			 
	            // write object to files
	            ObjectOutputStream oos = new ObjectOutputStream(output);
	            oos.writeObject(new ArrayList<Album>(userPhotoLibrary));
	            oos.close();


	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
	}
	
	public static void writeCurrentUser(User u) {
		 try {
			 	
			 	currentUserFile.createNewFile(); // if file already exists will do nothing 
			 	FileOutputStream output = new FileOutputStream(currentUserFile, false);
			 	
	            // write object to files
	            ObjectOutputStream oos = new ObjectOutputStream(output);
	            oos.writeObject(u);
	            oos.close();


	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
	}
	
	public static User readCurrentUserFile() {
		
		
		 try {
            
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(src+ File.separator+ model + File.separator + currentUser));
            User u = (User) ois.readObject();
            return u;
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }		
		
    return null;
	
}
	
	private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
        	//System.out.println(fileName.substring(fileName.lastIndexOf(".")+1));
        
        return fileName.substring(fileName.lastIndexOf(".")+1);}
        else return "";
    }
	
	
}