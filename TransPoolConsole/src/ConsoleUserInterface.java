public class ConsoleUserInterface {

    private static final ConsoleUserInterface instance = new ConsoleUserInterface();
    private boolean isInitiated = false;
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
        printAllTripOffers.loadEngine(engine);

        printSplashScreen();
        Menu mainMenu = new Menu("Main Menu");
        mainMenu.add(new MenuItem("Make a new trip", newTripRequest));
        mainMenu.add(new Menu(("Show current status of all trip offers")));
        mainMenu.add(new MenuItem("Show current status of all trip requests", printAllTripOffers));
        mainMenu.add(new MenuItem("Match trip offer to a trip request", printAllTripOffers));
        mainMenu.add(new MenuItem("Exit program", printAllTripOffers));
        mainMenu.execute();
    }

    private static void printSplashScreen() {
        System.out.println(StringConstants.SPLASH_SCREEN);
    }

}
