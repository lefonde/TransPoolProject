package NewTripRequest;

import Exceptions.NoSuchStopException;
import Exceptions.TimeException;
import Source.TransPoolProxy;
import Source.TripRequest;
import app.AppController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.function.Consumer;

public class NewTripRequestController {
    private AppController appController;
    private String name;
    private String departureStop;
    private String destinationStop;
    private String requestedHour;
    private String requestedMinute;
    private String requestedDay;


    @FXML
    private TextField nameTextField;

    @FXML
    private TextField departureStopTextField;

    @FXML
    private TextField destinationStopTextField;

    @FXML
    private Button SaveButton;

    @FXML
    private Button CancelButton;

    @FXML
    private TextField hourTextField;

    @FXML
    private TextField dayTextField;

    @FXML
    private TextField minuteTextField;

    @FXML
    public void CancelNewTripRequestAction(ActionEvent event) {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        setUpValidation(nameTextField);
        setUpValidation(departureStopTextField);
        setUpValidation(destinationStopTextField);
    }

    @FXML
    public void SaveNewTripRequestAction(ActionEvent event) {
        name = nameTextField.getText();
        departureStop = departureStopTextField.getText();
        destinationStop = destinationStopTextField.getText();
        requestedHour = hourTextField.getText();
        requestedMinute = minuteTextField.getText();
        requestedDay = dayTextField.getText();

        if(name.isEmpty()) {
            promptError("Please fill in your name");
            return;
        }

        if(departureStop.isEmpty()) {
            promptError("Please fill in your departure stop");
            return;
        }

        if(destinationStop.isEmpty()) {
            promptError("Please fill in your destination stop");
            return;
        }

        if(requestedHour.isEmpty()) {
            promptError("Please fill the hour field");
            return;
        }

        if(name.isEmpty()) {
            promptError("Please fill the minute field");
            return;
        }

        if(name.isEmpty()) {
            promptError("Please fill the day field");
            return;
        }

        try {
            TripRequest newTripRequest = appController.getEngine().CreateNewTripRequest(name, departureStop, destinationStop, false, Integer.parseInt(requestedHour), Integer.parseInt(requestedMinute), Integer.parseInt(requestedDay));
            appController.getRightController().addNewTripRequest(newTripRequest);
            Stage stage = (Stage) CancelButton.getScene().getWindow();
            stage.close();

        } catch (NoSuchStopException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        } catch (TimeException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            //alert.setHeaderText("Look, an Information Dialog");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void setUpValidation(final TextField tf) {
        tf.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                validate(tf);
            }

        });

        validate(tf);
    }

    private void validate(TextField tf) {
        ObservableList<String> styleClass = tf.getStyleClass();
        if (tf.getText().trim().length() == 0) {
            if (! styleClass.contains("error")) {
                styleClass.add("error");
            }
        } else {
            // remove all occurrences:
            styleClass.removeAll(Collections.singleton("error"));
        }
    }

    private void promptError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setMainController(AppController appController) {
        this.appController = appController;
    }

    public AppController getAppController() {
        return appController;
    }
}
