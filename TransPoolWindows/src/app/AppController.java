package app;

import Source.Engine;
import engine.Logic;
import header.HeaderController;
import javafx.stage.Stage;
import left.TripOffersAccordionController;
import middle.graph.map.MapController;
import right.TripRequestsAccordionController;

public class AppController {

    private MapController mapController;
    private HeaderController headerComponentController;
    private TripOffersAccordionController tripOffersController;
    private TripRequestsAccordionController tripRequestsController;
    private Logic logic;
    private Engine engine;
    private Stage primaryStage;

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Logic getLogic() {
        return logic;
    }

    public Engine getEngine() { return engine; }

    public void setCenterComponentController(MapController mapController) {
        this.mapController = mapController;
        mapController.setMainController(this);
    }

    public void setHeaderComponentController(HeaderController headerComponentController) {
        this.headerComponentController = headerComponentController;
        headerComponentController.setMainController(this);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    public void setEngine(Engine engine) { this.engine = engine; }

    public void setLeftComponentController(TripOffersAccordionController tripOffersController) {
        this.tripOffersController = tripOffersController;
        tripOffersController.setMainController(this);
    }

    public void setRightComponentController(TripRequestsAccordionController tripRequestsController) {
        this.tripRequestsController = tripRequestsController;
        tripRequestsController.setMainController(this);
    }

    public MapController getMapController() { return mapController; }

    public TripOffersAccordionController getLeftController() {
        return tripOffersController;
    }

    public HeaderController getHeaderController() {
        return headerComponentController;
    }

    public TripRequestsAccordionController getRightController() { return tripRequestsController; }
}
