package model;
public class User implements Comparable<Object>
{
	public String username;
	public String photoLibraryID;
	public User(String username, String ID)
	{
		this.username = username;
		this.photoLibraryID = ID;
		PhotoLibrary userPhotoLibrary = new PhotoLibrary(ID);
	}
	public String getName()
	{
		return username;
	}
	public void setName(String username)
	{
		this.username = username;
	}
	public String getID()
	{
		return photoLibraryID;
	}
	public void setID(String photoLibraryID)
	{
		this.photoLibraryID = photoLibraryID;
	}
	public String toString()
	{
		return this.username + " - ID: " + this.photoLibraryID;
	}
	@Override
	public int compareTo(Object o)
	{
		String userA = this.username + this.photoLibraryID;
		User other = (User)o;
		String userB = other.username + other.photoLibraryID;
		return userA.compareToIgnoreCase(userB);
	}
}