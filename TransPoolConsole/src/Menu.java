import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

public class Menu extends MenuComponent {

    private List<MenuComponent> subMenus = new ArrayList();
    private int menuItemsCounter;

    public Menu(String name) {
        super(name);
    }

    @Override
    public void add(MenuComponent menuComponent) {
        this.subMenus.add(menuComponent);
    }

    @Override
    public void execute() { //displays menu
        System.out.println(getName());
        int i;
        for (i = 0; i < subMenus.size(); i++) {
            System.out.println(displayOption(i + 1, subMenus.get(i)));
        }

        // user input request and process
        boolean validInput = false;
        int selection = 0;
        do {
            System.out.println("Enter number 1 - " + i + " and press enter to make your selection");
            String userInput = getUserInput();

            try {
                selection = tryParseInt(userInput);
                validInput = isValidSelection(selection, i+1);
                if(!validInput) System.out.println("Your selection was out of range");
            } catch (NumberFormatException e) {
                System.out.println("Input is invalid. Please enter a single number whole positive number");
            }
        }while(!validInput);

        subMenus.get(selection - 1).execute();
    }

    private boolean isValidSelection(int number, int topLimit) {
        return number < topLimit && number > 0;
    }

    private String displayOption(int key, MenuComponent component) {
        return ("(" + key + ") " + component.getName());
    }

    public void processSelection(int selection) {
        MenuComponent chosenItem = subMenus.get(selection);
    }

    private String getUserInput() {
        Scanner in = new Scanner(System.in);
        return  in.next();
    }

    private int tryParseInt(String str) throws NumberFormatException {
            return Integer.parseInt(str);
    }
}