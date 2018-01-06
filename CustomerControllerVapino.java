import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

import Model.Customer;
import Model.Item;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Created by Vishwas on 11/19/17.
 */
public class CustomerControllerVapino {

    private Connection connection = ConnectionFactory.getMYSQLConnection();
    private OperationVapino operation = new OperationVapino(connection);
    private int currentCustomerID;

    public void setCurrentCustomerID(int value) {
        this.currentCustomerID = value;
    }

    @FXML
    private Label titleLabel;
    @FXML
    private GridPane mainGridPane;
    @FXML
    private Button restaurantButton;
    @FXML
    private Button reserveButton;

    @FXML
    private Button foodMenuButton;
    @FXML
    private Button drinkMenuButton;

    @FXML
    private Button reservationsButton;
    @FXML
    private Button orderButton;
    @FXML
    private Button rateButton;
    @FXML
    private Button profileButton;

    @FXML
    private GridPane contentPane;

    // all the images for the buttons
    private ImageView restaurantIMV;
    private ImageView reserveIMV;
    private ImageView foodMenuIMV;
    private ImageView drinkMenuIMV;
    private ImageView reservationsIMV;
    private ImageView rateIMV;
    private ImageView profileIMV;

    // all the images for selected buttons
    private ImageView reserveSelectedIMV;
    private ImageView foodMenuSelectedIMV;
    private ImageView drinkMenuSelectedIMV;
    private ImageView reservationsSelectedIMV;
    private ImageView rateSelectedIMV;
    private ImageView profileSelectedIMV;

    @FXML
    void initialize() {
        getAllImageViewsForButtons();
        configureWidthHeightForImageViews();
        configureButtons();
        contentPane.setAlignment(Pos.CENTER);
    }

    private void configureWidthHeightForImageViews() {
        //Restaurant
        restaurantIMV.setFitWidth(51);
        restaurantIMV.setFitHeight(41);

        //Reserve
        reserveIMV.setFitWidth(39);
        reserveIMV.setFitHeight(73);
        reserveSelectedIMV.setFitWidth(39);
        reserveSelectedIMV.setFitHeight(73);

        //Food menu
        foodMenuIMV.setFitWidth(54);
        foodMenuIMV.setFitHeight(59);
        foodMenuSelectedIMV.setFitWidth(54);
        foodMenuSelectedIMV.setFitHeight(59);

        // drink menu
        drinkMenuIMV.setFitWidth(56);
        drinkMenuIMV.setFitHeight(59);
        drinkMenuSelectedIMV.setFitWidth(56);
        drinkMenuSelectedIMV.setFitHeight(59);

        // reservations
        reservationsIMV.setFitWidth(63);
        reservationsIMV.setFitHeight(55);
        reservationsSelectedIMV.setFitWidth(63);
        reservationsSelectedIMV.setFitHeight(55);

        // rate
        rateIMV.setFitWidth(32);
        rateIMV.setFitHeight(60);
        rateSelectedIMV.setFitWidth(32);
        rateSelectedIMV.setFitHeight(60);

        //profile
        profileIMV.setFitWidth(32);
        profileIMV.setFitHeight(58);
        profileSelectedIMV.setFitWidth(32);
        profileSelectedIMV.setFitHeight(58);
    }

    private void configureButtons() {
        //Restaurant button
        restaurantButton.setGraphic(restaurantIMV);
        restaurantButton.setStyle("-fx-background-color: transparent");

        //Reserve button
        reserveButton.setGraphic(reserveIMV);
        reserveButton.setStyle("-fx-background-color: transparent");

        //Foodmenu button
        foodMenuButton.setGraphic(foodMenuIMV);
        foodMenuButton.setStyle("-fx-background-color: transparent");

        //drinkmenu button
        drinkMenuButton.setGraphic(drinkMenuIMV);
        drinkMenuButton.setStyle("-fx-background-color: transparent");

        //reservations button
        reservationsButton.setGraphic(reservationsIMV);
        reservationsButton.setStyle("-fx-background-color: transparent");

        //rate button
        rateButton.setGraphic(rateIMV);
        rateButton.setStyle("-fx-background-color: transparent");

        //profile button
        profileButton.setGraphic(profileIMV);
        profileButton.setStyle("-fx-background-color: transparent");
    }

    private void getAllImageViewsForButtons() {
        restaurantIMV = new ImageView(new Image(getClass().getResourceAsStream("Graphics/Restaurant.png")));
        reserveIMV = new ImageView(new Image(getClass().getResourceAsStream("Graphics/c_Reserve.png")));
        foodMenuIMV = new ImageView(new Image(getClass().getResourceAsStream("Graphics/c_FoodMenu.png")));
        drinkMenuIMV = new ImageView(new Image(getClass().getResourceAsStream("Graphics/c_DrinkMenu.png")));
        reservationsIMV = new ImageView(new Image(getClass().getResourceAsStream("Graphics/c_Reservations.png")));
        rateIMV = new ImageView(new Image(getClass().getResourceAsStream("Graphics/c_Rate.png")));
        profileIMV = new ImageView(new Image(getClass().getResourceAsStream("Graphics/c_Profile.png")));

        reserveSelectedIMV = new ImageView(new Image(getClass().getResourceAsStream("Graphics/c_Reserve_Selected.png")));
        foodMenuSelectedIMV = new ImageView(new Image(getClass().getResourceAsStream("Graphics/c_FoodMenu_Selected.png")));
        drinkMenuSelectedIMV = new ImageView(new Image(getClass().getResourceAsStream("Graphics/c_DrinkMenu_Selected.png")));
        reservationsSelectedIMV = new ImageView(new Image(getClass().getResourceAsStream("Graphics/c_Reservations_Selected.png")));
        rateSelectedIMV = new ImageView(new Image(getClass().getResourceAsStream("Graphics/c_Rate_Selected.png")));
        profileSelectedIMV = new ImageView(new Image(getClass().getResourceAsStream("Graphics/c_Profile_Selected.png")));
    }

    @FXML
    private void goHomeScreen(ActionEvent event) {
        //get reference to WelcomeScreen stage
        Stage stage = (Stage) mainGridPane.getScene().getWindow();

        //load up WelcomeScene FXML document
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("WelcomeVapinoScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 1024, 720);
        stage.setTitle("Restaurant Reservation System");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void reserveButtonClicked(ActionEvent event) {
        configureButtons();
        reserveButton.setGraphic(reserveSelectedIMV);

        //Clear the content of contentPane
        contentPane.getChildren().clear();

        //Set title
        titleLabel.setText("Make A Reservation");

        // 2 vboxes and 1 hbox
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        HBox hbox3 = new HBox();
        HBox hbox4 = new HBox();
        HBox hbox5 = new HBox();
        HBox hbox6 = new HBox();
        HBox hbox7 = new HBox();

        //4 labels for table, date, party size
        Label tableLabel = new Label("Table:");
        Label dateLabel = new Label("Date:");
        Label partySizeLabel =  new Label("Party Size: ");
        Label firstNameLabel = new Label("First Name: ");
        Label lastNameLabel = new Label("Last Name: ");
        Label emailLabel = new Label("Email: ");

        //set font for 3 labels
        tableLabel.setFont(new Font("System", 24));
        dateLabel.setFont(new Font("System", 24));
        partySizeLabel.setFont(new Font("System", 24));
        firstNameLabel.setFont(new Font("System", 24));
        lastNameLabel.setFont(new Font("System", 24));
        emailLabel.setFont(new Font("System", 24));

        //4 textfields for data
        TextField tableTextField = new TextField();
        tableTextField.setPromptText("0-14");
        TextField dateTextField = new TextField();
        dateTextField.setPromptText("YYYY-MM-DD");
        TextField partySizeTextField = new TextField();
        partySizeTextField.setPromptText("0-10");
        TextField firstnameTextField = new TextField();
        firstnameTextField.setPromptText("First Name");
        TextField lastnameTextField = new TextField();
        lastnameTextField.setPromptText("Last Name");
        TextField emailTextField = new TextField();
        emailTextField.setPromptText("Email");

        hbox1.getChildren().addAll(tableLabel, tableTextField);
        hbox1.setAlignment(Pos.CENTER);
        hbox1.setSpacing(105);

        hbox2.getChildren().addAll(dateLabel, dateTextField);
        hbox2.setAlignment(Pos.CENTER);
        hbox2.setSpacing(115);

        hbox3.getChildren().addAll(partySizeLabel, partySizeTextField);
        hbox3.setAlignment(Pos.CENTER);
        hbox3.setSpacing(50);

        hbox4.getChildren().addAll(firstNameLabel, firstnameTextField);
        hbox4.setAlignment(Pos.CENTER);
        hbox4.setSpacing(40);

        hbox5.getChildren().addAll(lastNameLabel, lastnameTextField);
        hbox5.setAlignment(Pos.CENTER);
        hbox5.setSpacing(40);

        hbox6.getChildren().addAll(emailLabel, emailTextField);
        hbox6.setAlignment(Pos.CENTER);
        hbox6.setSpacing(100);

        //Confirm button
        Button confirmButton = new Button("Confirm");
        confirmButton.setStyle("-fx-background-color: #e63347;" +
                "-fx-background-radius: 7;" +
                "-fx-text-fill: white");
        confirmButton.setPrefSize(130, 40);
        confirmButton.setOnAction(e-> {
            if (tableTextField.getText() != null && dateTextField.getText() != null
                    && partySizeTextField.getText() != null && firstnameTextField.getText() != null && lastnameTextField.getText() != null
                    && emailTextField.getText() != null) {
                //Get all the data from text fields
                int tableID = Integer.parseInt(tableTextField.getText().trim());
                int partySize = Integer.parseInt(partySizeTextField.getText().trim());
                String firstname = firstnameTextField.getText().trim();
                String lastname = lastnameTextField.getText().trim();
                String email = emailTextField.getText().trim();
                Date date = Date.valueOf(dateTextField.getText().trim());

                Customer customer = new Customer();
                customer.setFirstName(firstname);
                customer.setLastName(lastname);
                customer.setEmail(email);

                boolean success = operation.reserveTable(partySize, date, tableID, customer);
                contentPane.getChildren().clear();
                if (success) {
                    titleLabel.setText("Your table has been reserved!");
                } else {
                    titleLabel.setText("Error! Can't reserve a table");
                }
            } else {
                titleLabel.setText("Empty Fields. Please Enter Info");
            }
        });

        hbox7.getChildren().add(confirmButton);
        hbox7.setAlignment(Pos.CENTER);
        hbox7.setPadding(new Insets(50, 0, 0, 0));
        hbox7.setSpacing(15);

        VBox box = new VBox();
        box.setSpacing(20);
        box.setPadding(new Insets(30, 0, 0, 0));
        box.setAlignment(Pos.TOP_CENTER);
        box.getChildren().addAll(hbox1, hbox2, hbox3, hbox4, hbox5, hbox6, hbox7);

        //add all to contentpane
        contentPane.getChildren().add(box);
    }

    @FXML
    private void foodMenuButtonClicked(ActionEvent event) {
        configureButtons();
        foodMenuButton.setGraphic(foodMenuSelectedIMV);

        //Clear old content
        contentPane.getChildren().clear();

        //Set up food menu details
        ImageView foodMenuDetails = new ImageView(new Image(getClass().getResourceAsStream("Graphics/FoodMenuDetails.png")));
        foodMenuDetails.setFitWidth(774/1.5);
        foodMenuDetails.setFitHeight(934/1.5);
        VBox box = new VBox();
        box.getChildren().add(foodMenuDetails);
        box.setAlignment(Pos.TOP_CENTER);

        contentPane.getChildren().add(box);
        titleLabel.setText("Food Menu");
        
        
    }

    @FXML
    private void drinkMenuButtonClicked(ActionEvent event) {
        configureButtons();
        drinkMenuButton.setGraphic(drinkMenuSelectedIMV);

        //Clear old content
        contentPane.getChildren().clear();

        //Set up drink menu details
        ImageView drinkMenuDetails = new ImageView(new Image(getClass().getResourceAsStream("Graphics/DrinkMenuDetails.png")));
        drinkMenuDetails.setFitWidth(774/1.5);
        drinkMenuDetails.setFitHeight(648/1.5);
        VBox box = new VBox();
        box.getChildren().add(drinkMenuDetails);
        box.setAlignment(Pos.TOP_CENTER);

        contentPane.getChildren().add(box);
        titleLabel.setText("Drink Menu");
    }

    @FXML
    private void reservationsButtonClicked(ActionEvent event) {
        configureButtons();
        reservationsButton.setGraphic(reservationsSelectedIMV);

        //Clear the content of contentPane
        contentPane.getChildren().clear();

        //Set title
        titleLabel.setText("Update A Reservation");

        // 2 vboxes and 1 hbox
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        HBox hbox3 = new HBox();
        HBox hbox4 = new HBox();
        HBox hbox5 = new HBox();

        //3 labels for table, date, party size
        Label tableLabel = new Label("Table:");
        Label dateLabel = new Label("Date:");
        Label partySizeLabel =  new Label("Party Size: ");
        Label emailLabel = new Label("Email:");

        //set font for 3 labels
        Font newFont = new Font("System", 24);
        tableLabel.setFont(newFont);
        dateLabel.setFont(newFont);
        partySizeLabel.setFont(newFont);
        emailLabel.setFont(newFont);

        //3 textfields for data
        TextField tableTextField = new TextField();
        tableTextField.setPromptText("0-14");
        TextField dateTextField = new TextField();
        dateTextField.setPromptText("YYYY-MM-DD");
        TextField partySizeTextField = new TextField();
        partySizeTextField.setPromptText("0-10");
        TextField emailTextField = new TextField();
        emailTextField.setPromptText("Email");

        hbox1.getChildren().addAll(tableLabel, tableTextField);
        hbox1.setAlignment(Pos.CENTER);
        hbox1.setSpacing(75);

        hbox2.getChildren().addAll(dateLabel, dateTextField);
        hbox2.setAlignment(Pos.CENTER);
        hbox2.setSpacing(85);

        hbox3.getChildren().addAll(partySizeLabel, partySizeTextField);
        hbox3.setAlignment(Pos.CENTER);
        hbox3.setSpacing(20);

        hbox4.getChildren().addAll(emailLabel, emailTextField);
        hbox4.setAlignment(Pos.CENTER);
        hbox4.setSpacing(80);

        // update button
        Button updateButton = new Button("Update");
        updateButton.setStyle("-fx-background-color: #e63347;" +
                "-fx-background-radius: 7;" +
                "-fx-text-fill: white");
        updateButton.setPrefSize(130, 40);
        updateButton.setOnAction(e-> {
            if (tableTextField.getText() != null && emailTextField.getText() != null
                    && partySizeTextField.getText() != null && dateTextField.getText() != null) {
                String tableid = tableTextField.getText().trim();
                String email = emailTextField.getText().trim();
                int partySize = Integer.parseInt(partySizeTextField.getText().trim());
                Date date = Date.valueOf(dateTextField.getText().trim());

                boolean success = false;
                try {
                    success = operation.changeReservation(email, date, partySize);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    titleLabel.setText("Something wrong!");
                }

                contentPane.getChildren().clear();
                if (success) {
                    titleLabel.setText("Your reservation has been updated!");
                } else {
                    titleLabel.setText("Something's wrong!");
                }
            } else {
                titleLabel.setText("Information missing. Do it again!");
            }
        });

        //Cancel button
        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #e6734b;" +
                "-fx-background-radius: 7;" +
                "-fx-text-fill: white");
        cancelButton.setPrefSize(130, 40);
        cancelButton.setOnAction(e-> {

            if (tableTextField.getText() != null && emailTextField.getText() != null
                    && partySizeTextField.getText() != null && dateTextField.getText() != null) {
                String tableid = tableTextField.getText().trim();
                String email = emailTextField.getText().trim();
                int tableSize = Integer.parseInt(partySizeTextField.getText().trim());
                Date date = Date.valueOf(dateTextField.getText().trim());

                operation.cancelReservation(email, date);

                //Success? do this
                contentPane.getChildren().clear();
                titleLabel.setText("Your reservation has been canceled!");
            } else {
                titleLabel.setText("Something wrong!");
            }
        });

        hbox5.getChildren().addAll(cancelButton, updateButton);
        hbox5.setAlignment(Pos.CENTER);
        hbox5.setPadding(new Insets(50, 0, 0, 0));
        hbox5.setSpacing(15);

        VBox box = new VBox();
        box.setSpacing(20);
        box.setPadding(new Insets(30, 0, 0, 0));
        box.setAlignment(Pos.TOP_CENTER);
        box.getChildren().addAll(hbox1, hbox2, hbox3, hbox4, hbox5);

        //add all to contentpane
        contentPane.getChildren().add(box);
    }

    @FXML
    private void rateButtonClicked(ActionEvent event) {
        configureButtons();
        rateButton.setGraphic(rateSelectedIMV);

        //clear old content
        contentPane.getChildren().clear();

        // set up
        titleLabel.setText("Rating and Feedback");

        // rating box
        HBox ratingBox = new HBox();
        ratingBox.setAlignment(Pos.CENTER);
        ratingBox.setSpacing(20);
        ratingBox.setPadding(new Insets(10, 0, 10, 0));

        //Rating label
        Label ratingTitle = new Label("Rating: ");
        ratingTitle.setFont(new Font("System", 24));

        // 5 stars
        String yellowStarURL = "Graphics/StarYellow.png";
        String blankStarURL = "Graphics/StarBlank.png";

        ImageView starBlank1 = new ImageView(new Image(getClass().getResourceAsStream("Graphics/StarBlank.png")));
        ImageView starBlank2 = new ImageView(new Image(getClass().getResourceAsStream("Graphics/StarBlank.png")));
        ImageView starBlank3 = new ImageView(new Image(getClass().getResourceAsStream("Graphics/StarBlank.png")));
        ImageView starBlank4 = new ImageView(new Image(getClass().getResourceAsStream("Graphics/StarBlank.png")));
        ImageView starBlank5 = new ImageView(new Image(getClass().getResourceAsStream("Graphics/StarBlank.png")));

        ToggleButton star1 = new ToggleButton(); star1.setGraphic(starBlank1); star1.setStyle("-fx-background-color: transparent");
        ToggleButton star2 = new ToggleButton(); star2.setGraphic(starBlank2); star2.setStyle("-fx-background-color: transparent");
        ToggleButton star3 = new ToggleButton(); star3.setGraphic(starBlank3); star3.setStyle("-fx-background-color: transparent");
        ToggleButton star4 = new ToggleButton(); star4.setGraphic(starBlank4); star4.setStyle("-fx-background-color: transparent");
        ToggleButton star5 = new ToggleButton(); star5.setGraphic(starBlank5); star5.setStyle("-fx-background-color: transparent");

        star1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (star1.isSelected()) {
                    star1.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(yellowStarURL))));
                } else {
                    star1.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(blankStarURL))));
                }
            }
        });

        star2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (star2.isSelected()) {
                    star2.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(yellowStarURL))));
                } else
                    star2.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(blankStarURL))));
            }
        });

        star3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (star3.isSelected()) {
                    star3.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(yellowStarURL))));
                } else
                    star3.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(blankStarURL))));
            }
        });

        star4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (star4.isSelected()) {
                    star4.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(yellowStarURL))));
                } else
                    star4.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(blankStarURL))));
            }
        });

        star5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (star5.isSelected()) {
                    star5.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(yellowStarURL))));
                } else
                    star5.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(blankStarURL))));
            }
        });

        // feedback box
        VBox feedBackBox = new VBox();
        feedBackBox.setAlignment(Pos.CENTER);
        feedBackBox.setSpacing(20);

        //Feedback title
        Label feedBackLabel = new Label("Feedback: ");
        feedBackLabel.setFont(new Font("System", 24));

        //Textarea
        TextArea textArea = new TextArea();
        textArea.setMaxWidth(680/1.5);

        //Confirm button
        Button confirmButton = new Button("Confirm");
        confirmButton.setStyle("-fx-background-color: #e63347;" +
                "-fx-background-radius: 7;" +
                "-fx-text-fill: white");
        confirmButton.setPrefSize(130, 40);
        confirmButton.setOnAction(e-> {
            int stars = 0;
            String feedback = null;
            int cID = 1;
            if (textArea.getText() != null) {
                //get the stars
                stars += (star1.isSelected()) ? 1 : 0;
                stars += (star2.isSelected()) ? 1 : 0;
                stars += (star3.isSelected()) ? 1 : 0;
                stars += (star4.isSelected()) ? 1 : 0;
                stars += (star5.isSelected()) ? 1 : 0;

                feedback = textArea.getText().trim();

                Customer customer = operation.getACustomer(currentCustomerID);
//                customer.setFirstName("Jon");
//                customer.setLastName("Nguyen");
//                customer.setEmail("jon@abc.com");
                boolean success = false;
                try {
                    success = operation.rate(stars, feedback, customer);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    contentPane.getChildren().clear();
                    titleLabel.setText("Rating fails.");
                }

                contentPane.getChildren().clear();

                if (success) {
                    titleLabel.setText("Feedback Received");

                } else {
                    titleLabel.setText("Can't Rate. Error!");
                }
                return;
            }
            contentPane.getChildren().clear();
            titleLabel.setText("Feedback Fails!");
        });

        //Add children nodes to appropriate boxes
        ratingBox.getChildren().addAll(ratingTitle, star1, star2, star3, star4, star5);
        feedBackBox.getChildren().addAll(feedBackLabel, textArea, confirmButton);

        //main box
        VBox mainBox = new VBox();
        mainBox.setSpacing(40);
        mainBox.getChildren().addAll(ratingBox, feedBackBox);

        contentPane.getChildren().add(mainBox);
    }

    @FXML
    private void profileButtonClicked(ActionEvent event) {
        configureButtons();
        profileButton.setGraphic(profileSelectedIMV);

        //clear old content
        contentPane.getChildren().clear();

        //set up receipt details
        HBox box = new HBox();

        VBox vbox1 = new VBox();
        VBox vBox2 = new VBox();

        // 3 labels for email, first name, last name
        Label emailLabel = new Label("Email:");
        Label firstNameLabel = new Label("First Name:");
        Label lastNameLabel = new Label("Last Name:");

        emailLabel.setFont(new Font("System", 24/1.5));
        firstNameLabel.setFont(new Font("System", 24/1.5));
        lastNameLabel.setFont(new Font("System", 24/1.5));


        //add 3 labels for vbox1
        vbox1.getChildren().addAll(emailLabel, firstNameLabel, lastNameLabel);
        vbox1.setSpacing(30);
        vbox1.setPadding(new Insets(40, 15, 0, 0));

        //get data from current user
        Customer customer = operation.getACustomer(currentCustomerID);
        String email, firstName, lastName;
        if (customer != null) {
            //get data for user profile
            email = customer.getEmail();
            firstName = customer.getFirstName();
            lastName = customer.getLastName();
        } else {
            email = "";
            firstName = "";
            lastName = "";
        }

        //3 labels for data
        Label userEmail = new Label(email);
        Label userFirstName = new Label(firstName);
        Label userLastName = new Label(lastName);

        userEmail.setFont(new Font("System", 24));
        userFirstName.setFont(new Font("System", 24));
        userLastName.setFont(new Font("System", 24));

        // add 3 labels to the vbox2
        vBox2.getChildren().addAll(userEmail, userFirstName, userLastName);
        vBox2.setSpacing(20);
        vBox2.setPadding(new Insets(35, 0, 15, 0));

        //add vboxes to hbox
        box.getChildren().addAll(vbox1, vBox2);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(40);

        contentPane.getChildren().add(box);
        titleLabel.setText("Profile");
    }
    
    @FXML
    private void orderButtonClicked(ActionEvent event) {
    	//This function is called when the order button is clicked on the customer screen
    	contentPane.getChildren().clear();
    	Operation operation = new Operation(connection);
    	ArrayList<Item> arraylist =operation.getMenu("food");
    	int count=0,i=105;
    	VBox box = new VBox();
        box.setSpacing(15);
        box.setPadding(new Insets(10, 10, 10, 10));
        box.setAlignment(Pos.BASELINE_LEFT);
    	for(Item arrayelement : arraylist) {
    		System.out.println("test count" + count++);
    		
    		HBox hbox1 = new HBox();
    		
    		Label itemLabel = new Label(arrayelement.getItemName());
//    		Label priceLabel = new Label(String.valueOf(arrayelement.getPrice()));
    		Font newFont = new Font("System", 10);
    	    itemLabel.alignmentProperty();
    		itemLabel.setFont(newFont);
//    		priceLabel.setFont(newFont);
    		TextField quantTextField = new TextField();
    		quantTextField.setPromptText("Quantity");
    		quantTextField.setAlignment(Pos.BASELINE_RIGHT);
            hbox1.getChildren().addAll(itemLabel,quantTextField);
            
            hbox1.setAlignment(Pos.BASELINE_RIGHT);
            hbox1.setSpacing(50);
            box.getChildren().add(hbox1);
            
    	}
    	Button confirmButton = new Button("Confirm");
        confirmButton.setStyle("-fx-background-color: #e63347;" +
                "-fx-background-radius: 7;" +
                "-fx-text-fill: white");
        confirmButton.setPrefSize(130, 40);
        
        //box.getChildren().add(confirmButton);
    	contentPane.getChildren().addAll(box,confirmButton);
    }
    
    @FXML
    private void deliverButtonClicked(ActionEvent event){
    	//This function is called when the deliver button is clicked
    	//clear old content
        contentPane.getChildren().clear();
        
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(50, 0, 0, 0));
        Label feedBackLabel = new Label("Address: ");
        feedBackLabel.setFont(new Font("System", 24));

        //Textarea
        TextArea textArea = new TextArea();
        textArea.setMaxWidth(680/1.5);

        //Confirm button
        Button confirmButton = new Button("Confirm");
        confirmButton.setStyle("-fx-background-color: #e63347;" +
                "-fx-background-radius: 7;" +
                "-fx-text-fill: white");
        confirmButton.setPrefSize(130, 40);
        
        box.getChildren().addAll(feedBackLabel, textArea, confirmButton);
        
        
        contentPane.getChildren().add(box);
        
    	
    }
/**@author Vishwas */
}

