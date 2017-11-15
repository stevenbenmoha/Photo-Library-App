package model;
import javafx.collections.ObservableList;
public class Album
{
	String albumName;
	private ObservableList<Photo> album;
	public Album(String s)
	{
		this.albumName = s;
	}
	ObservableList<Photo> getAlbum()
	{
		return album;
	}
	public String toString()
	{
		return albumName;
		
	}
}