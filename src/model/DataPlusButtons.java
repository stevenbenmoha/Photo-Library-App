/**
 * @author Colin Ackerley, Steven Benmoha
 */

package model;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class DataPlusButtons implements Serializable
{
	protected static User u;
	protected static Album a;
	public static File albumFile;
	public static File currentAlbumFile;
	public static File userFile = new File("src/model/userdata/users/userListFile.ser");
	public static File currentUserFile = new File("src/model/userdata/users/currentUserFile.ser");
	@FXML
	protected Button quitButton, logoutButton, searchButton, returnToAlbumsButton;
	protected static ObservableList<User> userList = FXCollections.observableArrayList();
	protected static ObservableList<Album> photoLibrary = FXCollections.observableArrayList();
	protected static ObservableList<String> photoAlbum = FXCollections.observableArrayList();
	
	/**
	 *
	 * @param event ActionEvent
	 * 
	 * quits program
	 *  	
	 */
	
	@FXML
	protected void quitProgram(ActionEvent event)
	{
		Stage stage = (Stage)quitButton.getScene().getWindow();
		stage.close();
	}
	
	/**
	 *
	 * @param event ActionEvent
	 * 
	 * logs out of program
	 *  	
	 */
	
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
	
	/**
	 *
	 * @param event ActionEvent
	 * 
	 * moves to search screen
	 *  	
	 */
	
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
	
	/**
	 *
	 * @param event ActionEvent
	 * 
	 * Returns page to album view
	 *  	
	 */
	
	
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
	
	/**
	 *
	 * @return ObservableList of Users
	 * 
	 *  Deserializes .dat file containing user list and updates ObservableList
	 *  	
	 */
	
	public static ObservableList<User> readUserFile()
	{ 
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
	
	/**
	 *
	 * @param userList 
	 * 
	 *  Serializes .dat file containing user list and updates ObservableList
	 *  	
	 */
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
	
	
	/**
	 * @param u User 
	 * @return userPhotoLibrary
	 * 
	 *  deserializes .dat file containing user's album list and updates ObservableList
	 *  	
	 */
	
	
	@SuppressWarnings("resource")
	public static ObservableList<Album> readUsersAlbumsFile(User u)
	{ 
		albumFile = new File("src/model/userdata/albums/" + u.getName() + " 's Albums.ser");
		if(albumFile.exists() && !getFileExtension(albumFile).equals("txt"))
		{
			try
			{
				FileInputStream input = new FileInputStream(albumFile);
				ObjectInputStream ois = new ObjectInputStream(input);
				@SuppressWarnings("unchecked")
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
	
	/**
	 * @param u User
	 * @param userPhotoLibrary 
	 * 
	 * 
	 *  Serializes .dat file containing user's album list and updates ObservableList
	 *  	
	 */
	
	
	public static void writeUsersAlbums(User u, ObservableList<Album> userPhotoLibrary)
	{ // Serializes user's list of albums into .dat file
		try
		{
			File albumFile = new File("src/model/userdata/albums/" + u.getName() + " 's Albums.ser");
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
	
	/**
	 * @param u User 
	 * 
	 * 
	 *  Serializes .dat file containing current user
	 *  	
	 */
	
	public static void writeCurrentUser(User u)
	{ 
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
	
	/**
	 * 
	 * @return u User
	 * 
	 *   Deserializes .dat file containing the current user
	 *  and uses this to inform program of who's logged in
	 *  	
	 */
	
	
	public static User readCurrentUserFile()
	{ 
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
	
	/**
	 * @param u User
	 * @param a Album
	 * @param albumPhotos
	 * 
	 * 
	 *  Serializes .dat file containing string contents of current album.
	 *   String contents contain text bytes that make up images
	 *  	
	 */	
	
	public static void writeCurrentAlbum(User u, Album a, ObservableList<String> albumPhotos)
	{
		try
		{
			File currentAlbumFile = new File(
					"src/model/userdata/albums/" + u.getName() + "-" + a.albumName + ".ser");
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
	

	/**
	 * @param u User
	 * @param a Album
	 * @return albumPhotos 
	 * 
	 *  Deserializes .dat file containing string contents of current album.
	 *   String contents contain text bytes that make up images
	 *  	
	 */	
	
	
	public static ObservableList<String> readCurrentAlbumFile(User u, Album a)
	{
		currentAlbumFile = new File("src/model/userdata/albums/" + u.getName() + "-" + a.albumName + ".ser");
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
	

	/**
	 * @param file File
	 * @return String containing file extension
	 * 
	 *  Gets the file extension of an inputted file
	 *  	
	 */	
	
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
	
	
	/**
	 * @param imagePath String
	 * @return String containing text representation of an image
	 * 
	 *  Turns an image file at a given path into a string representation
	 *  	
	 */	
	
	public static String encoder(String imagePath)
	{
		String base64Image = "";
		File file = new File(imagePath);
		try(FileInputStream imageInFile = new FileInputStream(file))
		{
			// Reading a Image file from file system
			byte imageData[] = new byte[(int)file.length()];
			imageInFile.read(imageData);
			base64Image = Base64.getEncoder().encodeToString(imageData);
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Image not found" + e);
		}
		catch(IOException ioe)
		{
			System.out.println("Exception while reading the Image " + ioe);
		}
		return base64Image;
	}
	
	/**
	 * 
	 * @param base64Image String
	 * @param albumName String
	 * @param pathFile String
	 * @param num int
	 * 
	 *  Turns text representation of image into a .png file
	 *  	
	 */	
	
	public static void decoder(String base64Image, String albumName, String pathFile, int num)
	{
		File directory = new File(albumName);
		if(!directory.exists())
		{
			directory.mkdirs();
		}
		try(FileOutputStream imageOutFile = new FileOutputStream(pathFile, false))
		{
			// Converting a Base64 String into Image byte array
			byte[] imageByteArray = Base64.getDecoder().decode(base64Image);
			imageOutFile.write(imageByteArray);
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Image not found" + e);
		}
		catch(IOException ioe)
		{
			System.out.println("Exception while reading the Image " + ioe);
		}
	}
}