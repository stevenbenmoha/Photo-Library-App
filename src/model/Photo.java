/**
 * @author Colin Ackerley, Steven Benmoha
 */
package model;
import java.io.Serializable;
import java.util.Calendar;
import javafx.scene.image.Image;

public class Photo implements Serializable
{
	public Photo()
	{
	}
	public String name;
	public String caption;
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

		/**
		 * @param tag representing a tag to be created
		 * @param val representing the value of the tag to be made
		 */

		public Tag(String tag, String val)
		{
			this.tag = tag;
			this.tagValue = val;
		}

		/**
		 * @return the current tag string
		 */

		public String getTag()
		{
			return tag;
		}

		/**
		 * @return the current tag value string
		 */


		public String getTagValue()
		{
			return tagValue;
		}
	}

	/**
	 * @return the current caption 
	 */
	public String getCaption()
	{
		return caption;
	}

	/**
	 * @param set the current caption as a string
	 */
	public void setCaption(String s)
	{
		this.caption = s;
	}
}