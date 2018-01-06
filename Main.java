import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

/**@author Vishwas*/
/**This is the main Page for our project. When a Customer enters he is greeted by a 
 * Welcome Page with a Join-In button. Which gives him access to choose from 6 different
 * Restaurants.When the user clicks Join-In button, the page is transfered to RestuarentsHomepage.fxml */	
    @Override
    public void start(Stage MainPageJoinIn) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("UltimateWelcome.fxml"));
        MainPageJoinIn.setTitle("Welcome To Booking System! Join In! ");
        MainPageJoinIn.setScene(new Scene(root, 1024, 720));
        MainPageJoinIn.setResizable(false);
        MainPageJoinIn.show();
        
        
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		      launch(args);
	//**Launch operation is inherited from the class Applications. This operation launches our project.*/	      
	}
}