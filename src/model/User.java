package model;

public class User implements Comparable<Object> 
{
	public String username;
	public int photoLibraryID;
	public User(String username, int ID)
	{
		this.username = username;
		this.photoLibraryID = ID;
		//PhotoLibrary userPhotoLibrary = new PhotoLibrary(ID);
	}
	
	public String getName() {
		return username;
	}
	
	public void setName(String username)
	{
		this.username = username;
	}
	
	public int getID() {
		return photoLibraryID;
	}
	
	public void setID(int photoLibraryID)
	{
		this.photoLibraryID = photoLibraryID;
	}
	
	
	public String toString()
	{
		return this.username + " - ID: " + this.photoLibraryID;
	}
	

	@Override
	public int compareTo(Object o) {
	
		String userA = this.username + this.photoLibraryID;
		User other = (User) o;
		String userB = other.username + other.photoLibraryID;
		return userA.compareToIgnoreCase(userB);
		
	}
	
	
}