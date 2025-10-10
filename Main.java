package connectfour;

/**
 * Simple launcher containing the program entry point. Instantiates a
 * {@link Game} and starts the interactive loop.
 */
public class Main {
    /**
     * Application entry point.
     *
     * @param args ignored command-line arguments
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }
}
