/**
 * @author Colin Ackereley, Steven Benmoha
 */
package model;
import java.io.Serializable;
import java.nio.file.Path;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
@SuppressWarnings("serial")
public class Album implements Comparable<Object>, Serializable
{
	public String albumName;
	public static ObservableList<String> albumPhotos = FXCollections.observableArrayList();
	
	public static ObservableList<Photo> realPhotos = FXCollections.observableArrayList();
	
	/**
	 * 
	 * @param String
	 *            representing the name of an album
	 * @return
	 */
	public Album(String s)
	{
		this.albumName = s;
		albumPhotos = FXCollections.observableArrayList();
		realPhotos = FXCollections.observableArrayList();
	}
	/**
	 * @return ObservableList representing the current album
	 */
	public ObservableList<String> getAlbum()
	{
		return albumPhotos;
	}
	/**
	 * @return String containing the name of the album
	 */
	public String getName()
	{
		return albumName;
	}
	/**
	 * @param albumName
	 *            String representing what to change the album name to
	 */
	public void setName(String albumName)
	{
		this.albumName = albumName;
	}
	/**
	 * @param Object
	 *            to compare an album with
	 */
	@Override
	public int compareTo(Object o)
	{
		String albumA = this.albumName;
		Album other = (Album)o;
		String albumB = other.albumName;
		return albumA.compareToIgnoreCase(albumB);
	}
}