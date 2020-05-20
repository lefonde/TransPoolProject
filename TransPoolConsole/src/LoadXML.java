import Exceptions.NoSuchStopException;
import Exceptions.TimeException;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoadXML extends Executable {
    private static final LoadXML instance = new LoadXML();

    private LoadXML() { }

    @Override
    public void Execute() {
        String userInput;

        do {
            System.out.println("Please specify the full path to the TransPoolData xml (e.g \"C:\\Resources\\ex1-small.xml\") or enter 'e' to escape");
            userInput = UserInputUtils.getUserInput();
            if(userInput.equalsIgnoreCase("e")) return;
        }while(!userInput.endsWith(".xml"));

        try {
            engine.loadDataFromXml(userInput);
        } catch (FileNotFoundException e) {
            System.out.println("Could not find the file specified");
        }
        System.out.println("data loaded successfully!");
    }

    public static Executable getInstance() {
        return instance;
    }
}
