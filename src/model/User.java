/**
 * @author Colin Ackerley, Steven Benmoha
 */
package model;
import java.io.Serializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class User implements Comparable<Object>, Serializable
{
	public String username;
	public String photoLibraryID;
	public static ObservableList<Album> userPhotoLibrary = FXCollections.observableArrayList();
	/**
	 * 
	 * @param username representing the username of a usre
	 * @param ID representing the ID of the user
	 */
	public User(String username, String ID)
	{
		this.username = username;
		this.photoLibraryID = ID;
		userPhotoLibrary = FXCollections.observableArrayList();
	}
	/**
	 * @return the name of the current user
	 */
	public String getName()
	{
		return username;
	}
	/**
	 * @param username to change the name of the current user to 
	 */
	public void setName(String username)
	{
		this.username = username;
	}
	/**
	 * @return the ID of the current user
	 */
	public String getID()
	{
		return photoLibraryID;
	}
	/**
	 * @param photoLibraryID to set the user's ID to 
	 */
	public void setID(String photoLibraryID)
	{
		this.photoLibraryID = photoLibraryID;
	}
	/**
	 * @return String representation of the current user
	 */
	public String toString()
	{
		return this.username + " - ID: " + this.photoLibraryID;
	}
	@Override
	/**
	 * @param Object to compare against a user
	 */
	public int compareTo(Object o)
	{
		String userA = this.username + this.photoLibraryID;
		User other = (User)o;
		String userB = other.username + other.photoLibraryID;
		return userA.compareToIgnoreCase(userB);
	}
}