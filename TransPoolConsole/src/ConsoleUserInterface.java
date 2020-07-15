import Source.Engine;

public class ConsoleUserInterface {

    private static final ConsoleUserInterface instance = new ConsoleUserInterface();
    private Engine engine;

    private ConsoleUserInterface() {
        this.engine = new Engine();
    }

    public static ConsoleUserInterface getInstance() {
        return instance;
    }

    public void Run() {
        Executable printAllTripOffers = PrintAllTripOffers.getInstance();
        Executable newTripRequest = NewTripRequest.getInstance();
        Executable matchRequestToOffer = MatchRequestToOffer.getInstance();
        Executable loadXML = LoadXML.getInstance();
        Executable printAllTripRequests = PrintAllTripRequest.getInstance();

        printAllTripOffers.loadEngine(engine);
        newTripRequest.loadEngine(engine);
        matchRequestToOffer.loadEngine(engine);
        loadXML.loadEngine(engine);
        printAllTripRequests.loadEngine(engine);

        printSplashScreen();
        Menu mainMenu = new Menu("Main Menu");
        mainMenu.add(new MenuItem("Load XML",loadXML, mainMenu));
        mainMenu.add(new MenuItem("Make a new trip", newTripRequest ,mainMenu));
        mainMenu.add(new MenuItem("Show current status of all trip offers", printAllTripOffers, mainMenu));
        mainMenu.add(new MenuItem("Show current status of all trip requests", printAllTripRequests, mainMenu));
        mainMenu.add(new MenuItem("Match trip offer to a trip request", matchRequestToOffer, mainMenu));
        mainMenu.add(new MenuItem("Exit program", printAllTripOffers, mainMenu));
        mainMenu.execute();

        System.out.println("GoodBye!");
    }

    private static void printSplashScreen() {
        System.out.println(StringConstants.SPLASH_SCREEN);
    }

}
