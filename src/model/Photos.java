/**
 * @author Colin Ackerley, Steven Benmoha
 */
package model;
import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class Photos extends Application
{
	@Override
	/**
	 * @param stage representing the initial view
	 */
	public void start(Stage primaryStage)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/login.fxml"));
			VBox root = (VBox)loader.load();
			LoginController controller = loader.getController();
			controller.start(primaryStage);
			primaryStage.setResizable(true);
			primaryStage.setTitle("Photo Library");
			Scene scene = new Scene(root, 899, 601);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @param args default java arguments
	 */

	public static void main(String[] args)
	{
		launch(args);
	}
}