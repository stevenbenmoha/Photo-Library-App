package model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class PhotoLibrary
{
	String photoLibraryID;
	public PhotoLibrary(String ID)
	{
		this.photoLibraryID = ID;
	}
	public String toString()
	{
		return photoLibraryID;
	}
}