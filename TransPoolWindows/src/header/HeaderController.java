package header;

import MatchRequestToOffer.MatchRequestToOfferController;
import NewTripOffer.NewTripOfferController;
import NewTripRequest.NewTripRequestController;
import app.AppController;
import engine.Logic;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class HeaderController {

    @FXML private Button LoadXmlButton;
    @FXML private Button MatchButton;
    @FXML private Button NewTripOfferButton;
    @FXML private Button NewTripRequestButton;
    @FXML private ProgressBar taskProgressBar;
    @FXML private Label taskMessageLabel;
    @FXML private Label progressPercentLabel;
    private Logic logic;
    private AppController appController;
    private SimpleStringProperty selectedFileProperty;
    private SimpleBooleanProperty isFileSelected;

    @FXML
    private void initialize() {
        //selectedFileName.textProperty().bind(selectedFileProperty);
        MatchButton.disableProperty().bind(isFileSelected.not());
        NewTripRequestButton.disableProperty().bind(isFileSelected.not());
        NewTripOfferButton.disableProperty().bind(isFileSelected.not());
    }

    @FXML
    public void LoadXmlAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select words file");
        //fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(appController.getPrimaryStage());
        if (selectedFile == null) {
            return;
        }

        String absolutePath = selectedFile.getAbsolutePath();
        selectedFileProperty.set(absolutePath);
        isFileSelected.set(true);
        appController.getLogic().LoadFromXml();
    }

    @FXML
    public void NewTripRequestAction(ActionEvent event) {
        FXMLLoader fxmlLoader;

        try {
            fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource("/NewTripRequest/NewTripRequest.fxml");
            fxmlLoader.setLocation(url);
            AnchorPane newTripRequestAnchorPane = fxmlLoader.load(url.openStream());
            NewTripRequestController newTripRequestController = fxmlLoader.getController();
            newTripRequestController.setMainController(appController);

            Scene newTripRequestScene = new Scene(newTripRequestAnchorPane, 640, 400);
            newTripRequestScene.getStylesheets().add(getClass().getResource("/NewTripRequest/NewTripRequests.css").toExternalForm());

            Stage stage = new Stage();
            stage.setTitle("New trip request");
            stage.setScene(newTripRequestScene);
            stage.show();
            // Hide this current window (if this is what you want)
            //((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void NewTripOfferAction(ActionEvent event) {
        FXMLLoader fxmlLoader;

        try {
            fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource("/NewTripOffer/NewTripOffer.fxml");
            fxmlLoader.setLocation(url);
            AnchorPane newTripOfferAnchorPane = fxmlLoader.load(url.openStream());

            NewTripOfferController newTripOfferController = fxmlLoader.getController();
            newTripOfferController.setMainController(appController);

            Scene newTripRequestScene = new Scene(newTripOfferAnchorPane, 640, 400);
            Stage stage = new Stage();
            stage.setTitle("New trip request");
            stage.setScene(newTripRequestScene);
            stage.show();
            // Hide this current window (if this is what you want)
            //((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void matchRequestToOfferAction(ActionEvent event) {
        FXMLLoader fxmlLoader;

        try {
            fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource("/MatchRequestToOffer/matchRequestToOffer.fxml");
            fxmlLoader.setLocation(url);
            BorderPane matchRequestToOfferSplitPane = fxmlLoader.load(url.openStream());

            MatchRequestToOfferController matchRequestToOfferController = fxmlLoader.getController();
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

    public void bindTaskToUIComponents(Task<Boolean> aTask) {
        // task message
        taskMessageLabel.textProperty().bind(aTask.messageProperty());

        // task progress bar
        taskProgressBar.progressProperty().bind(aTask.progressProperty());

        // task percent label
        progressPercentLabel.textProperty().bind(
                Bindings.concat(
                        Bindings.format(
                                "%.0f",
                                Bindings.multiply(
                                        aTask.progressProperty(),
                                        100)),
                        " %"));

       /* // task cleanup upon finish
        aTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            onTaskFinished(Optional.ofNullable(onFinish));
        });*/
    }
 /*   public void onTaskFinished(Optional<Runnable> onFinish) {
        this.taskMessageLabel.textProperty().unbind();
        this.progressPercentLabel.textProperty().unbind();
        this.taskProgressBar.progressProperty().unbind();
        onFinish.ifPresent(Runnable::run);
    }*/
    public HeaderController() {
        selectedFileProperty = new SimpleStringProperty();
        isFileSelected = new SimpleBooleanProperty(false);
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
        logic.fileNameProperty().bind(selectedFileProperty);
    }

    public void setMainController(AppController appController) {
        this.appController = appController;
    }

}
