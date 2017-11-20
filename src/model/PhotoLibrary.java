package model;
import java.io.Serializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class PhotoLibrary implements Comparable<Object>, Serializable
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
	@Override
	public int compareTo(Object o)
	{
		String photoLibraryA = this.photoLibraryID;
		PhotoLibrary other = (PhotoLibrary)o;
		String photoLibraryB = other.photoLibraryID;
		return photoLibraryA.compareToIgnoreCase(photoLibraryB);
	}
}