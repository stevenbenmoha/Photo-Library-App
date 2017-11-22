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
	public String tag;
	public String tagValue;


	public Image image;
	public int ID;
	public String binary;
	
	public Photo(Image image, int ID, String binary){
		
		this.image = image;
		this.ID = ID;
		this.binary = binary;
	}
	
		/**
		 * @return the current tag string
		 * 
		 * Gets the tag name
		 */

		public String getTag()
		{
			return tag;
		}

		/**
		 * @return the current tag value string
		 * 
		 * Gets the tag value
		 */


		public String getTagValue()
		{
			return tagValue;
		}
		

		/**
		 * @param s
		 * 
		 * Sets the tag name
		 */
		public void setTagName(String s)
		{
			this.tag = s;
		}
		

		/**
		 * @param s
		 * 
		 * Sets the tag value
		 */
		public void setTagValue(String s)
		{
			this.tagValue = s;
		}
		
	/**
	 * @return the current caption 
	 * 
	 * Gets the current caption
	 */
	public String getCaption()
	{
		return caption;
	}

	/**
	 * @param s String set the current caption as a string
	 */
	public void setCaption(String s)
	{
		this.caption = s;
	}
	
	
}