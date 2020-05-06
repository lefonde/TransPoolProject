public abstract class Executable {

    protected Engine engine;

    public abstract void  Execute();

    public void loadEngine(Engine engine) {
        this.engine = engine;
    }
}
