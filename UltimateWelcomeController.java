import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import com.mysql.jdbc.Connection;

import java.io.IOException;
public class UltimateWelcomeController {
	
/**@author Vishwas*/

	@FXML
    private Button joinButton;
	
	
	
	@FXML
	private void transitionToWelcomeScene() {
		//get reference to WelcomeScreen stage
        Stage stage = (Stage) joinButton.getScene().getWindow();

        //load up AdminScene FXML document
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("RestaurentsHomepage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 1024, 720);
        stage.setTitle("RestaurentHomepage");
        stage.setScene(scene);
        stage.show();
	}

/**@author Vishwas*/	
}
