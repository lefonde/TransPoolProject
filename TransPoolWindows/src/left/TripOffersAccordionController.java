package left;

import Source.ProxyTransPoolTrip;
import app.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import java.util.List;


public class TripOffersAccordionController {
    private AppController appController;
    @FXML
    private Accordion tripOffersAccordion;
    private List<ProxyTransPoolTrip> transPoolTrips;
    private static int counter = 0;

    public void loadPlannedTripsFromXml() {
        transPoolTrips = appController.getEngine().getPlannedTrips();
        for (ProxyTransPoolTrip t : transPoolTrips) {
            addPlannedTrip(t);
        }
    }

    public void addPlannedTrip(ProxyTransPoolTrip transPoolTrip) {
        TitledPane titledPane= new TitledPane("Trip number----GetTripNumber()", createGridPane(transPoolTrip));
        tripOffersAccordion.getPanes().add(titledPane);
    }

    public GridPane createGridPane(ProxyTransPoolTrip transPoolTrip){

        Label tripOwner=new Label(transPoolTrip.getOwner());
        Label capacity=new Label(String.valueOf(transPoolTrip.getCapacity()));
        Label  PPK=new Label(String.valueOf(transPoolTrip.getPPK()));
        Label route=new Label(transPoolTrip.getRouteAsString());
        Label  day=new Label(String.valueOf(transPoolTrip.getScheduling().getDayStart()));
        Label  arrival=new Label(getAppController().getEngine().calculateArrivalTime(transPoolTrip));
        Label departure=new Label(transPoolTrip.getScheduling().getHourStart() + ":"+ transPoolTrip.getScheduling().getMinuteStart());
        Label serialNumber=new Label(String.valueOf(++counter));

        Label tripOwnerLabel=new Label("Trip owner: ");
        Label capacityLabel=new Label("Capacity of the trip: " );
        Label  PPKLabel=new Label("Price per KM: ");
        Label routeLabel=new Label("Trip Route:");
        Label  dayLabel=new Label("Day of departure: ");
        Label  arrivalLabel=new Label("Arrival time:");
        Label departureLabel=new Label("Departure time: ");
        Label serialNumberLabel=new Label("Trip serial number:");

        GridPane tripOfferGridPane=new GridPane();
        return getGridPane(tripOwner, capacity, PPK, route, day, arrival, departure, serialNumber, tripOfferGridPane, tripOwnerLabel, capacityLabel,
                PPKLabel, routeLabel, dayLabel, arrivalLabel, departureLabel, serialNumberLabel);

    }

    static GridPane getGridPane(Label tripOwner, Label capacity, Label PPK, Label route, Label day, Label arrival, Label departure, Label serialNumber, GridPane tripOfferGridPane,
                                Label tripOwnerLabel, Label capacityLabel,Label  PPKLabel,Label routeLabel, Label  dayLabel, Label  arrivalLabel, Label departureLabel, Label serialNumberLabel) {

// build Labels on column 0 in the GridPain
        tripOfferGridPane.add(tripOwnerLabel,0,0);
        tripOfferGridPane.add(capacityLabel,0,1);
        tripOfferGridPane.add(PPKLabel,0,2);
        tripOfferGridPane.add(routeLabel,0,3);
        tripOfferGridPane.add(dayLabel,0,4);
        tripOfferGridPane.add(arrivalLabel,0,5);
        tripOfferGridPane.add(departureLabel,0,6);
        tripOfferGridPane.add(serialNumberLabel,0,7);

        // insert the data to the Labels on column 1 in the GridPain
        tripOfferGridPane.add(tripOwner,1,0);
        tripOfferGridPane.add(capacity,1,1);
        tripOfferGridPane.add(PPK,1,2);
        tripOfferGridPane.add(route,1,3);
        tripOfferGridPane.add(day,1,4);
        tripOfferGridPane.add(arrival,1,5);
        tripOfferGridPane.add(departure,1,6);
        tripOfferGridPane.add(serialNumber,1,7);


        tripOfferGridPane.setGridLinesVisible(true);
        return tripOfferGridPane;
    }

    public void setMainController(AppController appController) {
        this.appController = appController;
    }

    public AppController getAppController() {
        return appController;
    }
}
