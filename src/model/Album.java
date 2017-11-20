package model;
import java.io.Serializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
public class Album implements Comparable<Object>, Serializable
{
	public String albumName;
	public static ObservableList<Image> albumPhotos = FXCollections.observableArrayList();
	public Album(String s)
	{
		this.albumName = s;
		albumPhotos = FXCollections.observableArrayList();
	}
	public ObservableList<Image> getAlbum()
	{
		return albumPhotos;
	}
	public String toString()
	{
		return albumName;
	}
	public String getName()
	{
		return albumName;
	}
	public void setName(String albumName)
	{
		this.albumName = this.albumName;
	}
	@Override
	public int compareTo(Object o)
	{
		String albumA = this.albumName;
		Album other = (Album)o;
		String albumB = other.albumName;
		return albumA.compareToIgnoreCase(albumB);
	}
}