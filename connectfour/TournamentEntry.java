package connectfour;

public class TournamentEntry implements Comparable<TournamentEntry> {
    private final String name;
    private int wins;

    public TournamentEntry(String name) {
        this.name = name;
        this.wins = 0;
    }

    public String getName() {
        return name;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    @Override
    public int compareTo(TournamentEntry other) {
        if (this.wins != other.wins) {
            return Integer.compare(other.wins, this.wins);
        }
        return this.name.compareTo(other.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TournamentEntry other) {
            return this.name.equals(other.name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name + " (" + wins + " wins)";
    }
}
