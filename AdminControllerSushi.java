import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

import Model.Availability;
import Model.Customer;
import Model.Employee;
import Model.Person;
import Model.Rating;
import Model.ReservationInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminControllerSushi {

    private Connection connection = ConnectionFactory.getMYSQLConnection();
    private OperationSushi operation = new OperationSushi(connection);

    @FXML
    private Label titleLabel;
    @FXML
    private GridPane mainGridPane;
    @FXML
    private Button restaurantButton;
    @FXML
    private Button reservationsButton;

    @FXML
    private Button customersButton;
    @FXML
    private Button employeesButton;
    @FXML
    private Button ratingButton;
    @FXML
    private Button archiveButton;

    @FXML
    private GridPane contentPane;

    // all the images for the buttons
    private ImageView restaurantIMV;
    private ImageView reservationsIMV;
    private ImageView customersIMV;
    private ImageView employeesIMV;
    private ImageView ratingIMV;
    private ImageView archiveIMV;


    // all the images for selected buttons
    private ImageView reservationsSelectedIMV;
    private ImageView customersSelectedIMV;
    private ImageView employeesSelectedIMV;
    private ImageView ratingSelectedIMV;
    private ImageView archiveSelectedIMV;

    @FXML
    void initialize() {
        getAllImageViewsForButtons();
        configureWidthHeightForImageViews();
        configureButtons();
        contentPane.setAlignment(Pos.CENTER);


        //show all customers and all employees
        titleLabel.setText("All Customers and All Employees");
        TableView table = new TableView();

        TableColumn fnCol = new TableColumn("First Name");
        fnCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn lnCol = new TableColumn("Last Name");
        lnCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn emailCol = new TableColumn("email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        table.getColumns().addAll(fnCol, lnCol, emailCol);

        ObservableList<Person> data = FXCollections.observableArrayList(operation.getAllCustomersAndEmployees());
        table.setItems(data);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        contentPane.getChildren().add(table);
    }

    private void configureWidthHeightForImageViews() {
        //Restaurant
        restaurantIMV.setFitWidth(51);
        restaurantIMV.setFitHeight(41);

        //reservations
        reservationsIMV.setFitWidth(63);
        reservationsIMV.setFitHeight(84);
        reservationsSelectedIMV.setFitWidth(63);
        reservationsSelectedIMV.setFitHeight(84);

        //customers
        customersIMV.setFitWidth(53);
        customersIMV.setFitHeight(65);
        customersSelectedIMV.setFitWidth(53);
        customersSelectedIMV.setFitHeight(65);

        // employees
        employeesIMV.setFitWidth(53);
        employeesIMV.setFitHeight(53);
        employeesSelectedIMV.setFitWidth(53);
        employeesSelectedIMV.setFitHeight(53);

        // rating
        ratingIMV.setFitWidth(32);
        ratingIMV.setFitHeight(69);
        ratingSelectedIMV.setFitWidth(32);
        ratingSelectedIMV.setFitHeight(69);

        // archive
        archiveIMV.setFitWidth(37);
        archiveIMV.setFitHeight(51);
        archiveSelectedIMV.setFitWidth(37);
        archiveSelectedIMV.setFitHeight(51);

    }

    private void configureButtons() {
        //Restaurant button
        restaurantButton.setGraphic(restaurantIMV);
        restaurantButton.setStyle("-fx-background-color: transparent");

        //reservations button
        reservationsButton.setGraphic(reservationsIMV);
        reservationsButton.setStyle("-fx-background-color: transparent");

        //customers button
        customersButton.setGraphic(customersIMV);
        customersButton.setStyle("-fx-background-color: transparent");

        //employees button
        employeesButton.setGraphic(employeesIMV);
        employeesButton.setStyle("-fx-background-color: transparent");


        //rating button
        ratingButton.setGraphic(ratingIMV);
        ratingButton.setStyle("-fx-background-color: transparent");


        //archive button
        archiveButton.setGraphic(archiveIMV);
        archiveButton.setStyle("-fx-background-color: transparent");

    }

    private void getAllImageViewsForButtons() {
        restaurantIMV = new ImageView(new Image(getClass().getResourceAsStream("Graphics/sushipng.png")));
        reservationsIMV = new ImageView(new Image(getClass().getResourceAsStream("Graphics/a_reservations.png")));
        customersIMV = new ImageView(new Image(getClass().getResourceAsStream("Graphics/a_customers.png")));
        employeesIMV = new ImageView(new Image(getClass().getResourceAsStream("Graphics/a_employees.png")));
        ratingIMV = new ImageView(new Image(getClass().getResourceAsStream("Graphics/a_rating.png")));
        archiveIMV = new ImageView(new Image(getClass().getResourceAsStream("Graphics/a_archive.png")));


        reservationsSelectedIMV = new ImageView(new Image(getClass().getResourceAsStream("Graphics/a_reservations_selected.png")));
        customersSelectedIMV = new ImageView(new Image(getClass().getResourceAsStream("Graphics/a_customers_selected.png")));
        employeesSelectedIMV = new ImageView(new Image(getClass().getResourceAsStream("Graphics/a_employees_selected.png")));
        ratingSelectedIMV = new ImageView(new Image(getClass().getResourceAsStream("Graphics/a_rating_selected.png")));
        archiveSelectedIMV = new ImageView(new Image(getClass().getResourceAsStream("Graphics/a_archive_selected.png")));


    }

    @FXML
    private void goHomeScreen(ActionEvent event) {

        //get reference to WelcomeScreen stage
        Stage stage = (Stage) mainGridPane.getScene().getWindow();

        //load up WelcomeScene FXML document
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("WelcomeSushiScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 1024, 720);
        stage.setTitle("Restaurant Reservation System");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void reservationsButtonClicked(ActionEvent event) {
        configureButtons();
        reservationsButton.setGraphic(reservationsSelectedIMV);

        //Clear the content of contentPane
        contentPane.getChildren().clear();

        //Set title
        titleLabel.setText("Reservations");

        // Make box for weekly availability and reservations
        VBox box = new VBox();
        box.setSpacing(10);
        box.setAlignment(Pos.TOP_CENTER);
        box.setPrefWidth(900);

        // box for availability
        VBox abox = new VBox();
        box.setSpacing(10);
        box.setAlignment(Pos.TOP_CENTER);
        box.setPrefWidth(900);

        // box for reservations
        VBox resbox = new VBox();
        box.setSpacing(10);
        box.setAlignment(Pos.TOP_CENTER);
        box.setPrefWidth(900);

        //set up for weekly table availability
        Text aHeader = new Text("Tables still available");
        aHeader.setFont(new Font("System", 24));

        TableView aTable = new TableView();
        TableColumn aDateCol = new TableColumn("Date");
        aDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn tablesCol = new TableColumn("Tables Available");
        tablesCol.setCellValueFactory(new PropertyValueFactory<>("tablesAvailable"));

        //add table and title to availability box
        abox.getChildren().addAll(aHeader, aTable);
        abox.setSpacing(5);

        //set width for all cols
        aDateCol.setMinWidth(100);
        tablesCol.setMinWidth(100);

        aTable.getColumns().addAll(aDateCol, tablesCol);
        //Populate data to the table view
        operation.callDates();
        ObservableList<Availability> adata = FXCollections.observableArrayList(operation.getWeeklyAvailability());
        aTable.setItems(adata);
        aTable.setMaxHeight(235);
        aTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //set up for reservations
        Text resHeader = new Text("All Reservations");
        resHeader.setFont(new Font("System", 24));

        TableView resTable = new TableView();
        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn tidCol = new TableColumn("tID");
        tidCol.setCellValueFactory(new PropertyValueFactory<>("tID"));
        TableColumn partySizeCol = new TableColumn("Party Size");
        partySizeCol.setCellValueFactory(new PropertyValueFactory<>("partySize"));
        TableColumn seatsCol = new TableColumn("Seats");
        seatsCol.setCellValueFactory(new PropertyValueFactory<>("seats"));
        TableColumn dateCol = new TableColumn("Reservation Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("reservationDate"));

        //set width for all cols
        firstNameCol.setMinWidth(100);
        lastNameCol.setMinWidth(100);
        tidCol.setMinWidth(100);
        partySizeCol.setMinWidth(100);
        seatsCol.setMinWidth(100);
        dateCol.setMinWidth(300);

        //add table and title to availability box
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(resTable);
        scrollPane.setFitToWidth(true);
        resbox.getChildren().addAll(resHeader, scrollPane);
        resbox.setSpacing(5);

        resTable.getColumns().addAll(firstNameCol, lastNameCol, tidCol, partySizeCol, seatsCol, dateCol);
        //Populate data to the table view
        ObservableList<ReservationInfo> resdata = FXCollections.observableArrayList(operation.getAllReservations());
        resTable.setItems(resdata);
        resTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //add table view to content pane
        box.getChildren().addAll(abox, resbox);
        contentPane.getChildren().add(box);
    }

    @FXML
    private void customersButtonClicked(ActionEvent event) {
        configureButtons();
        
        customersButton.setGraphic(customersSelectedIMV);

        //Clear old content
        contentPane.getChildren().clear();

        titleLabel.setText("Customer Information");

        ScrollPane scrollPane = new ScrollPane();

        contentPane.getChildren().add(scrollPane);

        VBox box = new VBox();
        box.setSpacing(10);
        box.setAlignment(Pos.TOP_CENTER);
        box.setPrefWidth(900);

        //set up for ALL CUSTOMERS
        Text cHeader = new Text("All Customers");
        cHeader.setFont(new Font("System", 24));
        
        
        ObservableList<Customer> data = FXCollections.observableArrayList(operation.getAllCustomers());
        TableView cTable = new TableView();

        TableColumn cfnCol = new TableColumn("First Name");
        cfnCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn clnCol = new TableColumn("Last Name");
        clnCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn cEmailCol = new TableColumn("Email");
        cEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn cUpdatedAtCol = new TableColumn("Updated At");
        cUpdatedAtCol.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));
        TableColumn cDiscountCol = new TableColumn("Discount");
        cDiscountCol.setCellValueFactory(new PropertyValueFactory<>("discount"));

        cTable.getColumns().addAll(cfnCol, clnCol, cEmailCol, cUpdatedAtCol, cDiscountCol);
        cTable.setItems(data);
        
        cTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        box.getChildren().addAll(cHeader, cTable);
        scrollPane.setContent(box);
    }

    @FXML
    private void employeesButtonClicked(ActionEvent event) {
        configureButtons();
        employeesButton.setGraphic(employeesSelectedIMV);

        //Clear old content
        contentPane.getChildren().clear();

        titleLabel.setText("Employees");

        // 2 horizontal boxes for All Employees and Employees are Customers
        HBox hbox = new HBox();
        hbox.setSpacing(5);

        //2 vboxes for each hbox
        VBox leftBox = new VBox();
        VBox rightBox = new VBox();


        // Set up All employees
        ObservableList<Employee> data = FXCollections.observableArrayList(operation.getAllEmployees());
        TableView table = new TableView();

        //Add title for all employees
        Text leftTitle = new Text("All Employees");
        leftTitle.setFont(new Font("System",24));

        //add left table and title to left box
        leftBox.getChildren().addAll(leftTitle, table);
        leftBox.setSpacing(5);

        TableColumn efnCol = new TableColumn("First Name");
        efnCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn elnCol = new TableColumn("Last Name");
        elnCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn epositionCol = new TableColumn("Position");
        epositionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        TableColumn eemailCol = new TableColumn("Email");
        eemailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn elastworkedCol = new TableColumn("Last Worked");
        elastworkedCol.setCellValueFactory(new PropertyValueFactory<>("lastWorked"));

        table.getColumns().addAll(efnCol, elnCol, epositionCol, eemailCol, elastworkedCol);
        table.setItems(data);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Set up for employees and also customers
        ObservableList<Employee> rightData = FXCollections.observableArrayList(operation.getEmployeesWhoAreCustomers());
        TableView rightTable = new TableView();

        //Add title for all employees
        Text rightTitle = new Text("Employees Are Customers");
        rightTitle.setFont(new Font("System",24));

        TableColumn RfnCol = new TableColumn("First Name");
        RfnCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn RlnCol = new TableColumn("Last Name");
        RlnCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn RpositionCol = new TableColumn("Position");
        RpositionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        TableColumn RemailCol = new TableColumn("Email");
        RemailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn RlastworkedCol = new TableColumn("Last Worked");
        RlastworkedCol.setCellValueFactory(new PropertyValueFactory<>("lastWorked"));

        rightTable.getColumns().addAll(RfnCol, RlnCol, RpositionCol, RemailCol, RlastworkedCol);
        rightTable.setItems(rightData);

        //add right table to the right box
        rightBox.getChildren().addAll(rightTitle, rightTable);
        rightBox.setSpacing(5);

        hbox.getChildren().addAll(leftBox, rightBox);
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER);

        // add hbox to content pane
        contentPane.getChildren().add(hbox);
    }

    @FXML
    private void ratingButtonClicked(ActionEvent event) throws SQLException {
        configureButtons();
        ratingButton.setGraphic(ratingSelectedIMV);

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
        Label ratingTitle = new Label("Average Rating: ");
        ratingTitle.setFont(new Font("System", 24));

        // 5 stars
        String yellowStarURL = "Graphics/StarYellow.png";
        String blankStarURL = "Graphics/StarBlank.png";

        ImageView starBlank1 = new ImageView(new Image(getClass().getResourceAsStream("Graphics/StarBlank.png")));
        ImageView starBlank2 = new ImageView(new Image(getClass().getResourceAsStream("Graphics/StarBlank.png")));
        ImageView starBlank3 = new ImageView(new Image(getClass().getResourceAsStream("Graphics/StarBlank.png")));
        ImageView starBlank4 = new ImageView(new Image(getClass().getResourceAsStream("Graphics/StarBlank.png")));
        ImageView starBlank5 = new ImageView(new Image(getClass().getResourceAsStream("Graphics/StarBlank.png")));

        Button star1 = new Button(); star1.setGraphic(starBlank1); star1.setStyle("-fx-background-color: transparent");
        Button star2 = new Button(); star2.setGraphic(starBlank2); star2.setStyle("-fx-background-color: transparent");
        Button star3 = new Button(); star3.setGraphic(starBlank3); star3.setStyle("-fx-background-color: transparent");
        Button star4 = new Button(); star4.setGraphic(starBlank4); star4.setStyle("-fx-background-color: transparent");
        Button star5 = new Button(); star5.setGraphic(starBlank5); star5.setStyle("-fx-background-color: transparent");

        //get average rating
        double avgRating = 0;
        try {
            avgRating = operation.getAverageRating();
        } catch (SQLException e) {
            e.printStackTrace();
            ratingTitle.setText("Error! Can't get average rating");
        }

        Button[] buttonlist = new Button[5];
        buttonlist[0] = star1;
        buttonlist[1] = star2;
        buttonlist[2] = star3;
        buttonlist[3] = star4;
        buttonlist[4] = star5;

        for (int i = 0; i < Math.floor(avgRating); i++) {
            ImageView image = new ImageView(new Image(getClass().getResourceAsStream(yellowStarURL)));
            buttonlist[i].setGraphic(image);
        }

        // feedback box
        VBox feedBackBox = new VBox();
        feedBackBox.setAlignment(Pos.CENTER);
        feedBackBox.setSpacing(20);

        //Feedback title
        Label feedBackLabel = new Label("Feedback:");
        feedBackLabel.setFont(new Font("System", 24));

        //get feedback string
        String feedback = "";
        ArrayList<Rating> list = operation.getRatingsAndFeedbacks();

        for (Rating r : list) {
            feedback += r.getFeedback() + "\n\n";
        }

        Text feedbackText = new Text(feedback);
        feedbackText.setFont(new Font("System", 14));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(feedbackText);

        //Add children nodes to appropriate boxes
        ratingBox.getChildren().addAll(ratingTitle, star1, star2, star3, star4, star5);
        feedBackBox.getChildren().addAll(feedBackLabel, scrollPane);

        //main box
        VBox mainBox = new VBox();
        mainBox.setSpacing(40);
        mainBox.getChildren().addAll(ratingBox, feedBackBox);

        contentPane.getChildren().add(mainBox);
    }

    @FXML
    private void archiveButtonClicked(ActionEvent event) {
        configureButtons();
        archiveButton.setGraphic(archiveSelectedIMV);

        //clear old content
        contentPane.getChildren().clear();

        // ask for archive date
        titleLabel.setText("Archiving");

        //create text and textfield
        VBox mainBox = new VBox();
        mainBox.setSpacing(20);
        mainBox.setAlignment(Pos.CENTER);

        HBox box = new HBox();
        contentPane.getChildren().add(mainBox);

        box.setSpacing(5);
        box.setAlignment(Pos.CENTER);

        Label dateLabel = new Label("Cut Off Date:");
        dateLabel.setFont(new Font("System", 24));

        TextField dateTF = new TextField();
        dateTF.setPromptText("YYYY-MM-DD");

        Label errorLabel = new Label();
        errorLabel.setTextFill(Paint.valueOf("#f5515f"));
        errorLabel.setFont(new Font("System", 14));

        Button confirmButton = new Button("Confirm");
        confirmButton.setStyle("-fx-background-color: #e63347;" +
                "-fx-background-radius: 7;" +
                "-fx-text-fill: white");
        confirmButton.setPrefSize(130, 40);

        confirmButton.setOnAction(e-> {
            if (dateTF.getText() != null) {
                String cutoffDate = dateTF.getText().trim();
                if (cutoffDate.length() > 10) {
                    errorLabel.setText("Too long");
                    return;
                } else if (!isDate(cutoffDate)) {
                    errorLabel.setText("Wrong date format");
                    return;
                }

                boolean success = operation.archive(cutoffDate);
                if (success) {
                    //set up content
                    contentPane.getChildren().clear();

                    TableView table = new TableView();
                    TableColumn fnCol = new TableColumn("First Name");
                    fnCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
                    TableColumn lnCol = new TableColumn("Last Name");
                    lnCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
                    TableColumn emailCol = new TableColumn("Email");
                    emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
                    TableColumn lvCol = new TableColumn("Updated At");
                    lvCol.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));
                    TableColumn discountCol = new TableColumn("Discount");
                    discountCol.setCellValueFactory(new PropertyValueFactory<>("discount"));

                    table.getColumns().addAll(fnCol, lnCol, emailCol, lvCol, discountCol);
                    ObservableList<Customer> data = FXCollections.observableArrayList(operation.getArchivedCustomers());
                    table.setItems(data);

                    contentPane.getChildren().add(table);

                    titleLabel.setText("Archived Customers");

                } else {
                    titleLabel.setText("Can't archive");
                    return;
                }

            } else {
                errorLabel.setText("Please enter a date");
                return;
            }
        });

        box.getChildren().addAll(dateLabel, dateTF, errorLabel);
        mainBox.getChildren().addAll(box, confirmButton);
    }

    /**
     * Check if a given string is in a date format yyyy-mm-dd
     * @author Vishwas
     * @return true if a string is in format yyyy-mm-dd, false otherwise
     */
    static boolean isDate(String s) {
        return s.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})");
    }
}
