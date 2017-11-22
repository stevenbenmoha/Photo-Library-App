package model;
import java.io.Serializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * @author Steven Benmoha, Colin Ackerley
 *
 */
public class PhotoLibrary implements Comparable<Object>, Serializable
{
	String photoLibraryID;
	/**
	 * @param ID
	 * 
	 * Photolibrary id number
	 */
	public PhotoLibrary(String ID)
	{
		this.photoLibraryID = ID;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
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