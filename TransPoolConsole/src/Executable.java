public abstract class Executable {

    protected Engine engine;
    protected static boolean isInit = false;

    public abstract void  Execute();

    public void loadEngine(Engine engine) {
        this.engine = engine;
    }
}
