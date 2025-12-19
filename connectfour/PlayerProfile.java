package connectfour;

import java.io.Serializable;
import java.util.LinkedList;

public class PlayerProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int wins, draws, losses;
    private int aiWins, aiDraws, aiLosses;
    private LinkedList<Integer> lastResults;

    public PlayerProfile(String name) {
        this.name = name;
        this.lastResults = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void recordResult(boolean isAI, int result) {
        // result: +1 win, 0 draw, -1 loss
        if (result == 1) {
            wins++;
            if (isAI)
                aiWins++;
        } else if (result == 0) {
            draws++;
            if (isAI)
                aiDraws++;
        } else if (result == -1) {
            losses++;
            if (isAI)
                aiLosses++;
        }

        lastResults.add(result);
        if (lastResults.size() > 10)
            lastResults.removeFirst();
    }

    public int getWinCount() {
        return wins;
    }

    public void printProfile() {
        System.out.println("Player: " + name);
        System.out.println("W/D/L: " + wins + "/" + draws + "/" + losses);
        System.out.println("AI W/D/L: " + aiWins + "/" + aiDraws + "/" + aiLosses);
        System.out.print("Last 10: ");
        for (int r : lastResults) {
            System.out.print((r == 1 ? "W " : (r == 0 ? "D " : "L ")));
        }
        System.out.println();
    }
}
