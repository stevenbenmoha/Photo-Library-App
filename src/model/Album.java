package model;
import java.io.Serializable;

// import javafx.collections.ObservableList;
public class Album implements Comparable<Object>, Serializable
{
	public String albumName;
	// private ObservableList<Photo> album;
	public Album(String s)
	{
		this.albumName = s;
	}
	/* ObservableList<Photo> getAlbum()
	{
		return album;
	}
	*/ 
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