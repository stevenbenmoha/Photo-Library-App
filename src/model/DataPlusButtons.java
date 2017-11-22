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
import java.util.Base64;

import controller.LoginController;
import controller.PhotoLibraryController;
import controller.SearchController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class DataPlusButtons implements Serializable
{
	protected static User u;
	protected static Album a;
	public static File albumFile;
	public static File currentAlbumFile;
	public static File userFile = new File("src\\model\\userdata\\users\\userListFile.dat");
	public static File currentUserFile = new File("src\\model\\userdata\\users\\currentUserFile.dat");
	@FXML
	protected Button quitButton, logoutButton, searchButton, returnToAlbumsButton;
	protected static ObservableList<User> userList = FXCollections.observableArrayList();
	protected static ObservableList<Album> photoLibrary = FXCollections.observableArrayList();
	protected static ObservableList<String> photoAlbum = FXCollections.observableArrayList();
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
	protected void returnToAlbums(ActionEvent event) throws IOException
	{
		Stage stage;
		stage = (Stage)logoutButton.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/main.fxml"));
		VBox root = (VBox)loader.load();
		PhotoLibraryController controller = loader.getController();
		controller.start(stage);
		stage.setResizable(true);
		stage.setTitle("Photo Library");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public static ObservableList<User> readUserFile()
	{ // Deserializes .dat file containing user list and updates ObservableList
		if(userFile.exists())
		{
			try
			{
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userFile));
				ArrayList<User> userList = (ArrayList<User>)ois.readObject();
				return FXCollections.observableList(userList);
			}
			catch(ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		return FXCollections.observableList(userList);
	}
	public static void writeUser(ObservableList<User> userList)
	{ // Serializes user list into .dat file
		try
		{
			userFile.createNewFile(); // if file already exists will do nothing
			FileOutputStream output = new FileOutputStream(userFile, false);
			// write object to files
			ObjectOutputStream oos = new ObjectOutputStream(output);
			oos.writeObject(new ArrayList<User>(userList));
			oos.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public static ObservableList<Album> readUsersAlbumsFile(User u)
	{ // Deserializes .dat file containing user's list of albums and updates
		// ObservableList
		albumFile = new File("src\\model\\userdata\\albums\\" + u.getName() + " 's Albums.dat");
		if(albumFile.exists() && !getFileExtension(albumFile).equals("txt"))
		{
			try
			{
				FileInputStream input = new FileInputStream(albumFile);
				ObjectInputStream ois = new ObjectInputStream(input);
				ArrayList<Album> userPhotoLibrary = (ArrayList<Album>)ois.readObject();
				return FXCollections.observableList(userPhotoLibrary);
			}
			catch(ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		return FXCollections.observableList(photoLibrary);
	}
	public static void writeUsersAlbums(User u, ObservableList<Album> userPhotoLibrary)
	{ // Serializes user's list of albums into .dat file
		try
		{
			File albumFile = new File("src\\model\\userdata\\albums\\" + u.getName() + " 's Albums.dat");
			albumFile.createNewFile(); // if file already exists will do nothing
			FileOutputStream output = new FileOutputStream(albumFile, false);
			// write object to files
			ObjectOutputStream oos = new ObjectOutputStream(output);
			oos.writeObject(new ArrayList<Album>(userPhotoLibrary));
			oos.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public static void writeCurrentUser(User u)
	{ // Serializes the current user so later screen knows who's logged in
		try
		{
			currentUserFile.createNewFile(); // if file already exists will do nothing
			FileOutputStream output = new FileOutputStream(currentUserFile, false);
			// write object to files
			ObjectOutputStream oos = new ObjectOutputStream(output);
			oos.writeObject(u);
			oos.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public static User readCurrentUserFile()
	{ // Deserializes .dat file containing the current user
		// and uses this to inform program of who's logged in
		try
		{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(currentUserFile));
			User u = (User)ois.readObject();
			return u;
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public static void writeCurrentAlbum(User u, Album a, ObservableList<String> albumPhotos)
	{ 	
				
		try
		{
			File currentAlbumFile = new File(
					"src\\model\\userdata\\albums\\" + u.getName() + "-" + a.albumName + ".dat");
			currentAlbumFile.createNewFile(); // if file already exists will do nothing
			FileOutputStream output = new FileOutputStream(currentAlbumFile, false);
			// write object to files
			ObjectOutputStream oos = new ObjectOutputStream(output);
			oos.writeObject(new ArrayList<String>(albumPhotos));
			oos.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public static ObservableList<String> readCurrentAlbumFile(User u, Album a)
	{ 
		currentAlbumFile = new File("src\\model\\userdata\\albums\\" + u.getName() + "-" + a.albumName + ".dat");
		if(currentAlbumFile.exists() && !getFileExtension(currentAlbumFile).equals("txt"))
		{
			try
			{
				FileInputStream input = new FileInputStream(currentAlbumFile);
				ObjectInputStream ois = new ObjectInputStream(input);
				ArrayList<String> albumPhotos = (ArrayList<String>)ois.readObject();
				return FXCollections.observableList(albumPhotos);
			}
			catch(ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		return FXCollections.observableList(photoAlbum);
	}
	private static String getFileExtension(File file)
	{
		String fileName = file.getName();
		if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
		{
			// System.out.println(fileName.substring(fileName.lastIndexOf(".")+1));
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		}
		else
			return "";
	}
	
	
	public static String encoder(String imagePath) {
		String base64Image = "";
		File file = new File(imagePath);
		try (FileInputStream imageInFile = new FileInputStream(file)) {
			// Reading a Image file from file system
			byte imageData[] = new byte[(int) file.length()];
			imageInFile.read(imageData);
			base64Image = Base64.getEncoder().encodeToString(imageData);
		} catch (FileNotFoundException e) {
			System.out.println("Image not found" + e);
		} catch (IOException ioe) {
			System.out.println("Exception while reading the Image " + ioe);
		}
		return base64Image;
	}
	
	

public static void decoder(String base64Image,String albumName, String pathFile, int num) {
	
	File directory = new File(albumName);
	if (! directory.exists()){
        directory.mkdirs();
	}
	
	
	try (FileOutputStream imageOutFile = new FileOutputStream(pathFile, false)) {
		// Converting a Base64 String into Image byte array
		byte[] imageByteArray = Base64.getDecoder().decode(base64Image);
		imageOutFile.write(imageByteArray);
	} catch (FileNotFoundException e) {
		System.out.println("Image not found" + e);
	} catch (IOException ioe) {
		System.out.println("Exception while reading the Image " + ioe);
	}
}
	
	
	
}