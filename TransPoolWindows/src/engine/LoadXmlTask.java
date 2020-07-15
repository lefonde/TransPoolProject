package engine;

import Generated.TransPool;
import Source.Engine;
import Source.TransPoolProxy;
import app.Utils;
import javafx.application.Platform;
import javafx.concurrent.Task;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;


public class LoadXmlTask extends Task<Boolean> {
    private String fileName;
    private final static String JAXB_XML_GAME_PACKAGE_NAME = "generated";

    private final int SLEEP_TIME = 0;
    private Consumer<TransPoolProxy> transPoolConsumer;
    private boolean isTaskFinished=false;
    private Engine engine;

    public LoadXmlTask(String fileName, Engine engine, Consumer<TransPoolProxy> transPoolConsumer) {

        this.fileName = fileName;
        this.engine = engine;
        this.transPoolConsumer = transPoolConsumer;
    }

    @Override
    protected Boolean call() {
        File initialFile = new File(fileName);

        try {
            updateMessage("Fetching file...");
            BufferedReader reader = Files.newBufferedReader(
                    Paths.get(fileName),
                    StandardCharsets.UTF_8);


            updateProgress(0,1);

            Utils.sleepForAWhile(1000);
            updateMessage("File uploaded");

            updateProgress(1,1);
            // update total lines in UI

            Utils.sleepForAWhile(SLEEP_TIME);

            engine.loadDataFromXml(fileName);
            /*InputStream inputStream = new FileInputStream(initialFile);
            TransPoolProxy transPool = deserializeFrom(inputStream);*/
           // System.out.println("first path depart from station: " + transPool.getMapDescriptor().getPaths().getPath().get(0).getFrom());
            Platform.runLater(()-> transPoolConsumer.accept(engine.getData()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        isTaskFinished=true;

        return Boolean.TRUE;
    }


    public boolean isTaskFinished() {
        return isTaskFinished;
    }

    private static TransPool deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (TransPool) u.unmarshal(in);
    }
}
