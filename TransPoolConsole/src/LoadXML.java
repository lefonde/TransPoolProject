import Exceptions.NoSuchStopException;
import Exceptions.TimeException;

import java.util.Scanner;

public class LoadXML extends Executable {
    private static final LoadXML instance = new LoadXML();

    private LoadXML() { }

    @Override
    public void Execute() {
        System.out.println("Please specify the full path to the TransPoolData xml (e.g \"C:Resources/ex1-small.xml)\"");
        engine.loadDataFromXml(UserInputUtils.getUserInput());
    }

    public static Executable getInstance() {
        return instance;
    }
}
