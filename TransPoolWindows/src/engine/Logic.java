package engine;

import Generated.TransPool;
import Source.Engine;
import Source.TransPoolProxy;
import app.AppController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Logic {
    private SimpleStringProperty fileName;
    private Task<Boolean> currentRunningTask;
    private AppController controller;
    //private SimpleListProperty<TransPoolTrip> plannedTripsListProperty;
    //private SimpleListProperty<TripRequest> TripRequestsListProperty;
    private ObjectProperty<TransPool> transPoolObjectProperty;
    private boolean isTaskFinished = false;
    private Engine engine;
   // private TransPoolProxy transPool;


    public Logic(AppController controller, Engine engine) {
        this.fileName = new SimpleStringProperty();
        this.controller = controller;
        this.engine = engine;
        //   this.plannedTripsListProperty = new SimpleListProperty<>();
        //   this.TripRequestsListProperty = new SimpleListProperty<>();
    }

    public SimpleStringProperty fileNameProperty() {
        return this.fileName;
    }

    public void LoadFromXml() {
        Consumer<TransPoolProxy> transPoolConsumer = tp -> {
            //this.transPool = tp;
            //totalWordsDelegate.accept(tw);
            //System.out.println("second path depart from : " + transPool.getMapDescriptor().getPaths().getPath().get(1).getFrom());
            controller.getLeftController().loadPlannedTripsFromXml();
            controller.getMapController().generateMap();

        };

        currentRunningTask = new LoadXmlTask(fileName.get(), engine, transPoolConsumer);
        controller.getHeaderController().bindTaskToUIComponents(currentRunningTask);
        new Thread(currentRunningTask).start();

    }

    /*public String calculateArrivalTime(TransPoolTrip trip) {
        double totalTripTime=calculateTripLength(trip);
        int departure= trip.getScheduling().getHourStart();
        int minutes = (int) (totalTripTime %60);
        int hour= (int) (totalTripTime/60 +departure);
        if (minutes<10)
            return (hour +":0"+minutes);
        else
            return (hour +":"+minutes);
    }*/

    /*private double calculateTripLength(TransPoolTrip trip) {
        double totalTripTimeInMinutes = 0;
        String[] stopsString = trip.getRoute().getPath().replaceAll(" ", "").split(",");
        List<Path> pathsArray= transPool.getMapDescriptor().getPaths().getPath();
        return 0;
    }*/
 /*   public double calculateTripLength (TransPoolTrip trip) {
        double totalTripTimeInMinutes = 0;
        String[] stopsString = trip.getRoute().getPath().replaceAll(" ", "").split(",");
        Path path;

       for(int i = 0 ; i < stopsString.length - 1 ; i++) {
            path = transPool.getMapDescriptor().getPaths().getThePath(stopsString[i], stopsString[i+1]);
            totalTripTimeInMinutes+= (((double)path.getLength() /  (double)path.getSpeedLimit())*60);
        }
        return totalTripTimeInMinutes;
    }*/
    /*public Path getThePath(String fromStopName, String toStopName) {
        Path result = null;
        List<Path> paths = transPool.getMapDescriptor().getPaths().getPath();

        try {
            List<Path> fiteredList= paths.stream()
                    .filter(x -> x.getFrom().equalsIgnoreCase(fromStopName)
                            && x.getTo().equalsIgnoreCase(toStopName))
                    .collect(Collectors.toList());
            result = fiteredList.isEmpty() ? null : fiteredList.get(0);
        }
        catch (Exception e){
            return result;
        }

        return result;
    }*/
}

