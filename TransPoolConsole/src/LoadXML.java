import Exceptions.NoSuchStopException;
import Exceptions.TimeException;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoadXML extends Executable {
    private static final LoadXML instance = new LoadXML();

    private LoadXML() { }

    @Override
    public void Execute() {
        System.out.println("Please specify the full path to the TransPoolData xml (e.g \"C:\\Resources\\ex1-small.xml\")");
        try {
            engine.loadDataFromXml(UserInputUtils.getUserInput());
        } catch (FileNotFoundException e) {
            System.out.println("Could not find the file specified");
        }
    }

    public static Executable getInstance() {
        return instance;
    }
}
