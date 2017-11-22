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
	 * @param s String representing the name of an album
	 * 
	 */
	public Album(String s)
	{
		this.albumName = s;
		albumPhotos = FXCollections.observableArrayList();
		realPhotos = FXCollections.observableArrayList();
	}
	
	/**
	 * @return
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

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
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