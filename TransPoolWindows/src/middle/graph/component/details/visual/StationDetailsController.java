package middle.graph.component.details.visual;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import middle.graph.component.details.StationDetailsDTO;

public class StationDetailsController {

    private final String STATION_NAME_FORMAT = "%s ( %s ; %s)";

    @FXML private Label stationNameLabel;
    @FXML private Label visitingTripsLabel;

    public void setData(StationDetailsDTO data) {
        stationNameLabel.setText(String.format(STATION_NAME_FORMAT, data.getName(), String.valueOf(data.getX()), String.valueOf(data.getY())));
        visitingTripsLabel.setText(data.getDrives().toString());
    }

}
