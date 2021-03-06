import java.util.Scanner;

public class UserInputUtils {
    // if function returns -1 escape
    public static int PromptUserInputFromRange(int bottom, int top) {
        boolean validInput = false;
        int selection = -1;

        do {
            System.out.println(String.format("Enter number %d - %d and press enter to make your selection or 'e' to escape " , bottom, top));
            String userInput = getUserInput();

            if(userInput.equalsIgnoreCase("e")) break;
            try {
                selection = tryParseInt(userInput);
                validInput = isSelectionInRange(selection, bottom, top);
                if(!validInput) System.out.println("Your selection was out of range");
            } catch (NumberFormatException e) {
                System.out.println("Input is invalid. Please enter a single whole positive number or 'e' to escape");
            }
        }while(!validInput);

        return selection;
    }

    private static boolean isSelectionInRange(int number,int bottomLimit, int topLimit) {
        return number <= topLimit && number >= bottomLimit;
    }

    public static String getUserInput() {
        Scanner in = new Scanner(System.in);
        return  in.next();
    }

    private static int tryParseInt(String str) throws NumberFormatException {
        return Integer.parseInt(str);
    }
}
