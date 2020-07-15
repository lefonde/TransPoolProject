package NewTripOffer;

import Exceptions.NoSuchPathException;
import Exceptions.NoSuchStopException;
import Exceptions.TimeException;
import Source.ProxyTransPoolTrip;
import app.AppController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class NewTripOfferController {

    private AppController appController;
    private String name;
    private String hour;
    private String minute;
    private String day;
    private String ppk;
    private String recurrence;
    private String seatsAvailable;
    private String route;

    @FXML
    private TextField nameTextField;

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
    private TextField ppkTextField;

    @FXML
    private RadioButton noneRadioButton;

    @FXML
    private RadioButton dailyRadioButton;

    @FXML
    private RadioButton biDailyRadioButton;

    @FXML
    private RadioButton weeklyRadioButton;

    @FXML
    private RadioButton monthlyRadioButton;

    @FXML
    private ToggleGroup recurrenceToggleGroup;

    @FXML
    private TextField availableSeatsTextField;

    @FXML
    private TextField routeTextField;

    @FXML
    public void initialize() {
        setTextFieldAsNumeric(availableSeatsTextField);
        setTextFieldAsNumeric(minuteTextField);
        setTextFieldAsNumeric(hourTextField);
        setTextFieldAsNumeric(dayTextField);
        setTextFieldAsNumeric(ppkTextField);
    }

    @FXML
    void CancelNewTripOfferAction(ActionEvent event) {

    }

    @FXML
    void SaveNewTripOfferAction(ActionEvent event) {
        name = nameTextField.getText();
        hour = hourTextField.getText();
        minute = minuteTextField.getText();
        day = dayTextField.getText();
        ppk = ppkTextField.getText();
        recurrence = ((RadioButton) recurrenceToggleGroup.getSelectedToggle()).getText();
        seatsAvailable = availableSeatsTextField.getText();
        route = routeTextField.getText();

        try {
            ProxyTransPoolTrip transPoolTrip = appController.getEngine().CreateNewTripOffer(name, Integer.parseInt(hour), Integer.parseInt(minute), Integer.parseInt(day), Integer.parseInt(ppk), recurrence, Integer.parseInt(seatsAvailable), route);
            appController.getLeftController().addPlannedTrip(transPoolTrip);
            Stage stage = (Stage) CancelButton.getScene().getWindow();
            stage.close();

        } catch (NoSuchStopException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        } catch (NoSuchPathException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            //alert.setHeaderText("Look, an Information Dialog");
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

    private void setTextFieldAsNumeric(TextField tf) {
        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("\\d*"))
                tf.setText(newValue.replaceAll("[^\\d]", ""));
        });
    }

    public void setMainController(AppController appController) {
        this.appController = appController;
    }

    public AppController getAppController() {
        return appController;
    }

}
