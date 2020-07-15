package middle.graph.map;

import Exceptions.NoSuchStopException;
import Generated.Path;
import Generated.Stop;
import Source.Engine;
import Source.ProxyTransPoolTrip;
import app.AppController;
import com.fxgraph.graph.Graph;
import com.fxgraph.graph.Model;
import com.fxgraph.graph.PannableCanvas;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import middle.graph.component.coordinate.CoordinateNode;
import middle.graph.component.coordinate.CoordinatesManager;
import middle.graph.component.details.StationDetailsDTO;
import middle.graph.component.road.ArrowedEdge;
import middle.graph.component.station.StationManager;
import middle.graph.component.station.StationNode;
import middle.graph.layout.MapGridLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MapController {

    private AppController appController;
    private Model model;
    private StationManager stationManager;
    private CoordinatesManager coordinatesManager;

    public void createMap(Graph graph) {

        model = graph.getModel();
        graph.beginUpdate();

        stationManager = createStations(model);
        coordinatesManager = createCoordinates(model);
        createPaths();
        updateTrips();

        graph.endUpdate();

        graph.layout(new MapGridLayout(coordinatesManager, stationManager));
    }

    private StationManager createStations(Model model) {
        StationManager sm = new StationManager(StationNode::new);

        List<Stop> stops = appController.getEngine().getStops();

        for(Stop stop : stops) {
            StationNode station = sm.getOrCreate(stop.getX(), stop.getY());
            station.setName(stop.getName());
            model.addCell(station);
        }

        return sm;
    }

    public void generateMap() {
        Graph graphMap = new Graph();
        createMap(graphMap);

        PannableCanvas canvas = graphMap.getCanvas();
        ScrollPane scrollPane = (ScrollPane) appController.getPrimaryStage().getScene().lookup("#scrollpaneContainer");
        scrollPane.setContent(canvas);

        Platform.runLater(() -> {
            graphMap.getUseViewportGestures().set(false);
            graphMap.getUseNodeGestures().set(false);
        });
    }

    private CoordinatesManager createCoordinates(Model model) {

        CoordinatesManager cm = new CoordinatesManager(CoordinateNode::new);

        for (int i = 0 ; i < appController.getEngine().getMapLength() ; i++) {
            for (int j = 0 ; j < appController.getEngine().getMapWidth() ; j++) {
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

    private void updateRoutes() {


    }

    private void updateTrips() {
        Engine engine = appController.getEngine();
        List<ProxyTransPoolTrip> trips = engine.getPlannedTrips();

        for(ProxyTransPoolTrip trip : trips) {
            try {
                ArrayList<Stop> stops = engine.getStopsListFromRoute(trip.getRouteAsString());

                Stop prevStop = null;
                for(Stop currStop : stops) {

                    if(prevStop == null) {
                        StationNode station = stationManager.getOrCreate(currStop.getX(), currStop.getY());
                        station.setDetailsSupplier(() -> {
                            List<String> tripsDetails = new ArrayList<>();
                            tripsDetails.add(trip.getOwner() + "'s trip");
                            return new StationDetailsDTO(tripsDetails);
                        });
                        prevStop = currStop;
                        continue;
                    }

                    ArrowedEdge pathEdge = new ArrowedEdge(coordinatesManager.getOrCreate(prevStop.getX(), prevStop.getY())
                            , coordinatesManager.getOrCreate(currStop.getX(), currStop.getY()));

                    model.addEdge(pathEdge);
                    Platform.runLater(() -> {
                        pathEdge.getLine().getStyleClass().add("trip");
                        pathEdge.getText().getStyleClass().add(trip.getOwner());
                        //moveAllEdgesToTheFront(graph);
                    });

                }
            } catch (NoSuchStopException e) {
                e.printStackTrace();
            }
        }
    }

    private void createPaths() {
        Engine engine = appController.getEngine();
        List<Path> paths = engine.getPaths();

        for(Path path : paths) {
            try {
                Stop fromStop =  engine.getStop(path.getFrom());
                Stop toStop = engine.getStop(path.getTo());
                ArrowedEdge pathEdge = new ArrowedEdge(coordinatesManager.getOrCreate(fromStop.getX(),fromStop.getY()), coordinatesManager.getOrCreate(toStop.getX(),toStop.getY()));
                model.addEdge(pathEdge);
                Platform.runLater(() -> {
                    pathEdge.getLine().getStyleClass().add("path");
                    //moveAllEdgesToTheFront(graph);
                });
            } catch (NoSuchStopException e) {
                e.printStackTrace();
            }
        }

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

    public void setMainController(AppController appController) {
        this.appController = appController;
    }

    public AppController getAppController() {
        return appController;
    }
}
