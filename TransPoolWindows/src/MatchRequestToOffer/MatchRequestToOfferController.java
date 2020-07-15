package MatchRequestToOffer;


import Source.ProxyTransPoolTrip;
import Source.TripRequest;
import app.AppController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

public class MatchRequestToOfferController {
    private AppController appController;
    private TripRequest selectedTripRequest;
    private ObservableList<ProxyTransPoolTrip> tripsList;
    private String resultsCount;

    @FXML
    private TextField filterTextField;

    @FXML
    private Button filterButton;

    @FXML
    private ListView<HBox> tripOffersListView;

    @FXML
    public void initialize() {
        setTextFieldAsNumeric(filterTextField);
    }

    @FXML
    public void searchMatchingTripOffersAction(ActionEvent event) {
        resultsCount = filterTextField.getText();

        if(resultsCount.isEmpty()) {
            promptError("Please fill in the filter text box with a number between 1 - 50");
            return;
        }

        tripsList = FXCollections.observableArrayList(appController.getEngine().GetAllTripsInRoute(selectedTripRequest.getFromStation(), selectedTripRequest.getToStation(), selectedTripRequest.getRequestedTimeOfDeparture()));

        for(ProxyTransPoolTrip trip : tripsList) {
            HBox tripDetailsBox = new HBox(50);

            tripDetailsBox.setFillHeight(true);
            tripDetailsBox.setPrefHeight(30);
            tripDetailsBox.setAlignment(Pos.BASELINE_CENTER);

            TripsButton selectTripButton = new TripsButton(trip);
            selectTripButton.setText("Select");
            selectTripButton.setOnAction(x -> {
                ProxyTransPoolTrip selectedTripOffer = selectTripButton.getTrip();
                selectedTripOffer.addHitchhiker(selectedTripRequest);
                Stage stage = (Stage) filterButton.getScene().getWindow();
                stage.close();
            });

            tripDetailsBox.getChildren().addAll(setLabelPropertiesForHBox(new Label(String.valueOf(trip.getSerialNumber())))
                    , setLabelPropertiesForHBox(new Label(trip.getOwner()))
                    , setLabelPropertiesForHBox(new Label(trip.getRouteAsString()))
                    , setLabelPropertiesForHBox(new Label(trip.getScheduling().getHourStart() + ":" + trip.getScheduling().getDayStart()))
                    , setLabelPropertiesForHBox(new Label(String.valueOf(trip.getPPK())))
                    , setLabelPropertiesForHBox(new Label(String.valueOf(trip.getCapacity())))
                    , selectTripButton);

            tripOffersListView.getItems().add(tripDetailsBox);
            tripDetailsBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }
    }

    private void setTextFieldAsNumeric(TextField tf) {
        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("\\d*"))
                tf.setText(newValue.replaceAll("[^\\d]", ""));
        });
    }

    private Label setLabelPropertiesForHBox(Label label) {
        HBox.setHgrow(label, Priority.ALWAYS);

        return label;
    }

    private void promptError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setSelectedTripRequest(TripRequest selectedTripRequest) {
        this.selectedTripRequest = selectedTripRequest;
    }

    public void setMainController(AppController appController) {
        this.appController = appController;
    }

    public AppController getAppController() {
        return appController;
    }

    protected class TripsButton extends Button {
        private ProxyTransPoolTrip trip;

        protected TripsButton(ProxyTransPoolTrip trip) {
            this.trip = trip;
        }

        protected ProxyTransPoolTrip getTrip() {
            return trip;
        }
    }

}
