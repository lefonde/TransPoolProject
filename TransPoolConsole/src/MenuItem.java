public class MenuItem extends MenuComponent {

    private Executable command;
    private MenuComponent prevMenu;

    public MenuItem(String name, Executable command, MenuComponent prevMenu) {
        super(name);
        this.command = command;
        this.prevMenu = prevMenu;
    }

    @Override
    public void execute() {
        command.Execute();
        prevMenu.execute();
    }
}