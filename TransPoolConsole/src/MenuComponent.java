public abstract class MenuComponent {

    private String name;

    public String getName() {
        return name;
    }

    public MenuComponent(String name) {
        this.name = name;
    }

    public void add(MenuComponent component) {
        throw new UnsupportedOperationException();
    }

    public abstract void execute();
}