package ModesOfAES;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
	private static AES A;
	public static void main(String[] args) 
	{
		launch(args);
	}
	public static void SetAes(AES B)
	{
		A=B;
	}
	public static AES GetAES()
	{
		return A;
	}
@Override
	public void start(Stage primaryStage) throws Exception 
	{
		try
		{
		Parent root=FXMLLoader.load(getClass().getResource("/ModesOfAES/AES.fxml"));
		Scene scene=new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		}
		catch(Exception e)
		{
			System.out.print(e);
		}
	
	}
}
