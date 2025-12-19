package connectfour;

public class TournamentStats {
    private int wins = 0;
    private int losses = 0;

    public void recordWin() {
        wins++;
    }

    public void recordLoss() {
        losses++;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    @Override
    public String toString() {
        return "W: " + wins + ", L: " + losses;
    }
}
