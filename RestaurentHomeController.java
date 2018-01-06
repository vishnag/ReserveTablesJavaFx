import javafx.application.Platform;
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
public class RestaurentHomeController {
	
	
/**@author Vishwas*/
	@FXML
    private Button indianButton;
	
	@FXML
	private Button vapinoButton;
	
	@FXML
	private Button geoButton;
	
	@FXML
	private Button chipButton;
	
	@FXML
	private Button frenchButton;
	
	@FXML
	private Button sushiButton;
	
	@FXML
	private Button exitButton;
	
	@FXML
	private void transitionToExitScene(){
	
		    Platform.exit();
		    System.exit(0);
		}
		
	
	
	@FXML
	private void transitionToIndianWelcomeScene() {
		//get reference to WelcomeScreen stage
        Stage stage = (Stage) indianButton.getScene().getWindow();

        //load up AdminScene FXML document
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("WelcomeIndianScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 1024, 720);
        stage.setTitle("RestaurentHomepageIndian");
        stage.setScene(scene);
        stage.show();
	}
	
	@FXML
	private void transitionToVapinoWelcomeScene() {
		//get reference to WelcomeScreen stage
        Stage stage = (Stage) vapinoButton.getScene().getWindow();

        //load up AdminScene FXML document
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("WelcomeVapinoScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 1024, 720);
        stage.setTitle("RestaurentHomepageVapino");
        stage.setScene(scene);
        stage.show();
	}
	
	@FXML
	private void transitionToGeoWelcomeScene() {
		//get reference to WelcomeScreen stage
        Stage stage = (Stage) geoButton.getScene().getWindow();

        //load up AdminScene FXML document
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("WelcomeGeoScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 1024, 720);
        stage.setTitle("RestaurentHomepageGeoDarno");
        stage.setScene(scene);
        stage.show();
	}
	
	@FXML
	private void transitionToChipWelcomeScene() {
		//get reference to WelcomeScreen stage
        Stage stage = (Stage) chipButton.getScene().getWindow();

        //load up AdminScene FXML document
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("WelcomeChipScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 1024, 720);
        stage.setTitle("RestaurentHomepageChip");
        stage.setScene(scene);
        stage.show();
	}
	
	@FXML
	private void transitionToFrenchWelcomeScene() {
		//get reference to WelcomeScreen stage
        Stage stage = (Stage) frenchButton.getScene().getWindow();

        //load up AdminScene FXML document
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("WelcomeFrenchScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 1024, 720);
        stage.setTitle("RestaurentHomepageFrench");
        stage.setScene(scene);
        stage.show();
	}
	
	@FXML
	private void transitionToSushiWelcomeScene() {
		//get reference to WelcomeScreen stage
        Stage stage = (Stage) sushiButton.getScene().getWindow();

        //load up AdminScene FXML document
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("WelcomeSushiScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 1024, 720);
        stage.setTitle("RestaurentHomepageSushi");
        stage.setScene(scene);
        stage.show();
	}
/**@author Vishwas*/
	
}	
