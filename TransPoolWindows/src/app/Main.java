package app;

import Source.Engine;
import com.fxgraph.graph.Graph;
import com.fxgraph.graph.Model;
import com.fxgraph.graph.PannableCanvas;
import engine.Logic;
import header.HeaderController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import left.TripOffersAccordionController;
import middle.graph.component.coordinate.CoordinateNode;
import middle.graph.component.coordinate.CoordinatesManager;
import middle.graph.component.details.StationDetailsDTO;
import middle.graph.component.road.ArrowedEdge;
import middle.graph.component.station.StationManager;
import middle.graph.component.station.StationNode;
import middle.graph.layout.MapGridLayout;
import middle.graph.map.MapController;
import right.TripRequestsAccordionController;
import sun.plugin.javascript.navig.Anchor;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {


    public static void main(String[] args) {
        Thread.currentThread().setName("main");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

/*        Graph graphMap = new Graph();
        createMap(graphMap);*/

        // load header component and controller from fxml
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("/middle/graph/map/map.fxml");
        fxmlLoader.setLocation(url);
        ScrollPane middleComponent = fxmlLoader.load(url.openStream());
        middleComponent.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        MapController mapController = fxmlLoader.getController();

        // load header component and controller from fxml
        fxmlLoader = new FXMLLoader();
        url = getClass().getResource("/header/header.fxml");
        fxmlLoader.setLocation(url);
        AnchorPane headerComponent = fxmlLoader.load(url.openStream());
        headerComponent.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        HeaderController headerController = fxmlLoader.getController();


        // load left component and controller from fxml
        fxmlLoader = new FXMLLoader();
        url = getClass().getResource("/left/tripOffersAccordion.fxml");
        fxmlLoader.setLocation(url);
        AnchorPane leftComponent = fxmlLoader.load(url.openStream());
        leftComponent.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        TripOffersAccordionController tripOffersController = fxmlLoader.getController();


        // load right component and controller from fxml
        fxmlLoader = new FXMLLoader();
        url = getClass().getResource("/right/tripRequestsAccordion.fxml");
        fxmlLoader.setLocation(url);
        AnchorPane rightComponent = fxmlLoader.load(url.openStream());
        rightComponent.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        TripRequestsAccordionController tripRequestsController = fxmlLoader.getController();

        // load master app and controller from fxml
        fxmlLoader = new FXMLLoader();
        url = getClass().getResource("app.fxml");
        fxmlLoader.setLocation(url);
        BorderPane root = fxmlLoader.load(url.openStream());
        AppController appController = fxmlLoader.getController();


        Engine engine = new Engine();
        Logic logic = new Logic(appController, engine);
        appController.setPrimaryStage(primaryStage);
        appController.setLogic(logic);
        appController.setEngine(engine);
        headerController.setLogic(logic);


        // add sub components to master app placeholders
        root.setTop(headerComponent);
        root.setLeft(leftComponent);
        root.setRight(rightComponent);
        root.setCenter(middleComponent);
        root.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        // root.setCenter(bodyComponent);

        // connect between controllers
        //  appController.setBodyComponentController(showTripsController);
        appController.setCenterComponentController(mapController);
        appController.setHeaderComponentController(headerController);
        appController.setLeftComponentController(tripOffersController);
        appController.setRightComponentController(tripRequestsController);


/*        ScrollBar scrollBar = new ScrollBar();
        scrollBar.setOrientation(Orientation.HORIZONTAL);


        Scene scene = new Scene(root, 1000, 600);
        ScrollPane scrollPane = (ScrollPane) scene.lookup("#scrollpaneContainer");
        PannableCanvas canvas = graphMap.getCanvas();
        canvas.getChildren().add(scrollBar);

        scrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {
            //updateMap(newValue);
        });
        scrollPane.setContent(canvas);
        */

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        //scrollBar

/*        Platform.runLater(() -> {
            graphMap.getUseViewportGestures().set(false);
            graphMap.getUseNodeGestures().set(false);
        });*/
    }

    private void updateMap(Number newValue) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("new value");
        //alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText(newValue.toString());
        alert.showAndWait();
    }

    private void createMap(Graph graph) {

        final Model model = graph.getModel();
        graph.beginUpdate();

        StationManager sm = createStations(model);
        CoordinatesManager cm = createCoordinates(model);
        createEdges(model, cm);

        graph.endUpdate();

        graph.layout(new MapGridLayout(cm, sm));
    }

    private StationManager createStations(Model model) {
        StationManager sm = new StationManager(StationNode::new);

        StationNode station = sm.getOrCreate(2, 2);
        station.setName("This is a test for long string");

        station.setDetailsSupplier(() -> {
            List<String> trips = new ArrayList<>();
            trips.add("Mosh");
            trips.add("Menash");
            return new StationDetailsDTO(trips);
        });
        model.addCell(station);

        station = sm.getOrCreate(5, 5);
        station.setName("B");
        station.setDetailsSupplier(() -> {
            List<String> trips = new ArrayList<>();
            return new StationDetailsDTO(trips);
        });
        model.addCell(station);

        station = sm.getOrCreate(7, 9);
        station.setName("C");
        station.setDetailsSupplier(() -> {
            List<String> trips = new ArrayList<>();
            trips.add("Mosh");
            trips.add("Menash");
            trips.add("Tikva");
            trips.add("Mazal");
            return new StationDetailsDTO(trips);
        });
        model.addCell(station);

        station = sm.getOrCreate(4, 6);
        station.setName("D");
        station.setDetailsSupplier(() -> {
            List<String> trips = new ArrayList<>();
            trips.add("Mazal");
            return new StationDetailsDTO(trips);
        });
        model.addCell(station);

        return sm;
    }

    private CoordinatesManager createCoordinates(Model model) {

        CoordinatesManager cm = new CoordinatesManager(CoordinateNode::new);

        for (int i=0; i<10; i++) {
            for (int j = 0; j < 10; j++) {
                model.addCell(cm.getOrCreate(i+1, j+1));
            }
        }

        return cm;
    }

    private void createEdges(Model model, CoordinatesManager cm) {
        ArrowedEdge e13 = new ArrowedEdge(cm.getOrCreate(2,2), cm.getOrCreate(7,9));
        e13.textProperty().set("L: 7 ; FC: 4");
        model.addEdge(e13); // 1-3

        ArrowedEdge e34 = new ArrowedEdge(cm.getOrCreate(7,9), cm.getOrCreate(4,6));
        e34.textProperty().set("L: 12 ; FC: 14");
        model.addEdge(e34); // 3-4

        ArrowedEdge e23 = new ArrowedEdge(cm.getOrCreate(5,5), cm.getOrCreate(7,9));
        e23.textProperty().set("L: 4 ; FC: 10");
        model.addEdge(e23); // 2-3

        Platform.runLater(() -> {
            e13.getLine().getStyleClass().add("line1");
            e13.getText().getStyleClass().add("edge-text");

            e34.getLine().getStyleClass().add("line2");
            e34.getText().getStyleClass().add("edge-text");

            e23.getLine().getStyleClass().add("line3");
            e23.getText().getStyleClass().add("edge-text");

            //moveAllEdgesToTheFront(graph);
        });

    }

    private void moveAllEdgesToTheFront(Graph graph) {

        List<Node> onlyEdges = new ArrayList<>();

        // finds all edge nodes and remove them from the beginning of list
        ObservableList<Node> nodes = graph.getCanvas().getChildren();
        while (nodes.get(0).getClass().getSimpleName().equals("EdgeGraphic")) {
            onlyEdges.add(nodes.remove(0));
        }

        // adds them as last ones
        nodes.addAll(onlyEdges);
    }

    private StationManager createStops(Model model) {
        StationManager sm = new StationManager(StationNode::new);


        StationNode station = sm.getOrCreate(2, 2);
        station.setName("This is a test for long string");

        station.setDetailsSupplier(() -> {
            List<String> trips = new ArrayList<>();
            trips.add("Mosh");
            trips.add("Menash");
            return new StationDetailsDTO(trips);
        });
        model.addCell(station);

        return sm;
    }

}
