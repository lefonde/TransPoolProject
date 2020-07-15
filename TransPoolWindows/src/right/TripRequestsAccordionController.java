package right;

import MatchRequestToOffer.MatchRequestToOfferController;
import Source.ProxyTransPoolTrip;
import Source.TripRequest;
import app.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;


public class TripRequestsAccordionController {
    private AppController appController;
    private TripRequest currentTripRequest;
    @FXML
    private Accordion TripRequestsAccordion;
    private List<TripRequest> transPoolTripRequests;
    private static int counter = 0;


    public void addNewTripRequest(TripRequest tripRequest)
    {
        TitledPane titledPane= new TitledPane("Trip number----GetTripNumber()", createGridPane(tripRequest));
        TripRequestsAccordion.getPanes().add(titledPane);
    }
    public GridPane createGridPane(TripRequest tripRequest){

        currentTripRequest = tripRequest;

        Label tripRequestApplicant = new Label(tripRequest.getNameOfApplicant());
        Label fromStation = new Label((tripRequest.getFromStation()));
        Label toStation = new Label(tripRequest.getToStation());
        Label serialNumber = new Label(String.valueOf(tripRequest.getSerialNumber()));
        Label time = new Label(tripRequest.getRequestedTimeOfDeparture().getHourStart()
                + ":"
                + tripRequest.getRequestedTimeOfDeparture().getMinuteStart());
        Label day = new Label(String.valueOf(tripRequest.getRequestedTimeOfDeparture().getDayStart()));

        Label tripRequestApplicantLabel = new Label("Name: ");
        Label fromStationLabel = new Label("From station: ");
        Label toStationLabel = new Label("To station: ");
        Label serialNumberLabel = new Label("Trip serial number:");
        Label timeLabel = new Label("Time:");
        Label dayLabel = new Label("Day:");

        return getGridPane(tripRequestApplicant, fromStation, toStation, serialNumber, time, day,
                tripRequestApplicantLabel, fromStationLabel, toStationLabel, serialNumberLabel, timeLabel, dayLabel);

    }

    private GridPane getGridPane(Label tripRequestApplicant, Label fromStation, Label toStation, Label serialNumber, Label time, Label day, Label tripRequestApplicantLabel, Label fromStationLabel, Label toStationLabel, Label serialNumberLabel, Label timeLabel, Label dayLabel) {

        GridPane tripRequestGridPane = new GridPane();
        // build Labels on column 0 in the GridPain
        tripRequestGridPane.add(serialNumberLabel,0,0);
        tripRequestGridPane.add(tripRequestApplicantLabel,0,1);
        tripRequestGridPane.add(fromStationLabel,0,2);
        tripRequestGridPane.add(toStationLabel,0,3);
        tripRequestGridPane.add(timeLabel,0,4);
        tripRequestGridPane.add(dayLabel,0,5);

        Button matchButton = new Button("Match");
        matchButton.onActionProperty().setValue(this::matchRequestToOfferAction);

        tripRequestGridPane.add(matchButton, 0, 6);


        // insert the data to the Labels on column 1 in the GridPain
        tripRequestGridPane.add(serialNumber,1,0);
        tripRequestGridPane.add(tripRequestApplicant,1,1);
        tripRequestGridPane.add(fromStation,1,2);
        tripRequestGridPane.add(toStation,1,3);
        tripRequestGridPane.add(time,1,4);
        tripRequestGridPane.add(day,1,5);


        tripRequestGridPane.setGridLinesVisible(true);
        return tripRequestGridPane;
    }

    public void matchRequestToOfferAction(ActionEvent event) {
        FXMLLoader fxmlLoader;

        try {
            fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource("/MatchRequestToOffer/matchRequestToOffer.fxml");
            fxmlLoader.setLocation(url);
            BorderPane matchRequestToOfferSplitPane = fxmlLoader.load(url.openStream());

            MatchRequestToOfferController matchRequestToOfferController = fxmlLoader.getController();
            matchRequestToOfferController.setSelectedTripRequest(currentTripRequest);
            matchRequestToOfferController.setMainController(appController);

            Scene matchRequestToOfferScene = new Scene(matchRequestToOfferSplitPane, 1000, 600);
            Stage stage = new Stage();
            stage.setTitle("Match request to offer");
            stage.setScene(matchRequestToOfferScene);
            stage.setMaximized(true);
            stage.show();
            // Hide this current window (if this is what you want)
            //((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMainController(AppController appController) {
        this.appController = appController;
    }

    public AppController getAppController() {
        return appController;
    }
}
