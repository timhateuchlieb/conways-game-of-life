public class Main {
    public static void main(String[] args) throws InterruptedException {
        TerminalUtils.hideCursor();
        TerminalUtils.registerCursorShutdownHook();

        GameOfLife game = new GameOfLife(0.2, 500);
        game.run();
    }
}
