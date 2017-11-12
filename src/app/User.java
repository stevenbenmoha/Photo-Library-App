package app;
public class User
{
	String username;
	String photoLibraryID;
	public User(String username, String ID)
	{
		this.username = username;
		PhotoLibrary userPhotoLibrary = new PhotoLibrary(ID);
	}
}
