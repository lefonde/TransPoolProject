public class MenuItem extends MenuComponent {

    private Executable command;

    public MenuItem(String name, Executable command) {
        super(name);
        this.command = command;
    }

    @Override
    public void execute() { //run command
        command.Execute();
    }
}