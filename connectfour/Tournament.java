package connectfour;

import java.util.*;

public class Tournament {
    private final String id;
    private final List<String> players;
    private final Queue<Match> matchQueue = new LinkedList<>();
    private final Map<String, TournamentStats> stats = new HashMap<>();
    private final AVLTree<TournamentEntry> standings = new AVLTree<>();

    public Tournament(String id, List<String> players) {
        this.id = id;
        this.players = players;
        for (String player : players) {
            stats.put(player, new TournamentStats());
            standings.insert(new TournamentEntry(player));
        }
    }

    public void scheduleMatches() {
        for (int i = 0; i < players.size(); i++) {
            for (int j = i + 1; j < players.size(); j++) {
                matchQueue.add(new Match(players.get(i), players.get(j)));
            }
        }
    }

    public boolean hasNextMatch() {
        return !matchQueue.isEmpty();
    }

    public Match getNextMatch() {
        return matchQueue.poll();
    }

    public void recordMatchResult(String winner, String loser) {
        TournamentStats winnerStats = stats.get(winner);
        TournamentStats loserStats = stats.get(loser);

        TournamentEntry oldEntry = new TournamentEntry(winner);
        oldEntry.setWins(winnerStats.getWins());
        standings.remove(oldEntry);

        winnerStats.recordWin();
        loserStats.recordLoss();

        TournamentEntry newEntry = new TournamentEntry(winner);
        newEntry.setWins(winnerStats.getWins());
        standings.insert(newEntry);
    }

    public void printStandings() {
        System.out.println("Tournament Standings (by Wins DESC, Name ASC):");
        List<TournamentEntry> ordered = standings.inOrder();

        int rank = 1;
        for (TournamentEntry entry : ordered) {
            System.out.printf("%d. %s (%d wins)%n", rank++, entry.getName(), entry.getWins());
        }
    }

    public String getId() {
        return id;
    }
}