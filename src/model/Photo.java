package model;
import java.io.Serializable;
import java.util.Calendar;

import javafx.scene.image.Image;
public class Photo implements Serializable
{
	public String name;
	public String caption;
	public String tagName;
	public String tagValue;
	public Calendar date;
	public Image image;
	public int ID;
	
	public Photo(Image image, int ID){
		
		this.image = image;
		this.ID = ID;
	}
	
		
	public class Tag
	{
		private String tag;
		private String tagValue;
		public Tag(String tag, String val)
		{
			this.tag = tag;
			this.tagValue = val;
		}
		public String getTag()
		{
			return tag;
		}
		public String getTagValue()
		{
			return tagValue;
		}
	}
	public String getCaption()
	{
		return caption;
	}
	public void setCaption(String s)
	{
		this.caption = s;
	}
}