package model;
import javafx.collections.ObservableList;
public class PhotoLibrary
{
	private ObservableList<Album> userLibrary;
	String photoLibraryID;
	public PhotoLibrary(String ID)
	{
		this.photoLibraryID = ID;
	}
	ObservableList<Album> getphotoLibrary()
	{
		return userLibrary;
	}
	public String toString()
	{
		return photoLibraryID;
	}
}