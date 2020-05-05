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

        int selection = UserInputUtils.PromptUserInputFromRange(1, i);

        subMenus.get(selection - 1).execute();
    }

    private String displayOption(int key, MenuComponent component) {
        return ("(" + key + ") " + component.getName());
    }


}