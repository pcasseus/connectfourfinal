public class Match {
    public final String player1;
    public final String player2;

    public Match(String p1, String p2) {
        this.player1 = p1;
        this.player2 = p2;
    }

    @Override
    public String toString() {
        return player1 + " vs " + player2;
    }
}
