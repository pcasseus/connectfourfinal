package connectfour;

import java.io.*;
import java.util.*;

public class PlayerManager {
    private static final String SAVE_FILE = "players.dat";

    private Map<String, PlayerProfile> players;
    private PlayerProfile currentPlayer;

    public PlayerManager() {
        load();
    }

    public void register(String name) {
        if (players.containsKey(name)) {
            System.out.println("Player already exists.");
            return;
        }
        PlayerProfile profile = new PlayerProfile(name);
        players.put(name, profile);
        save();
        System.out.println("Registered new player: " + name);
    }

    public void login(String name) {
        PlayerProfile profile = players.get(name);
        if (profile == null) {
            System.out.println("No such player.");
            return;
        }
        currentPlayer = profile;
        System.out.println("Logged in as: " + name);
    }

    public void logout() {
        currentPlayer = null;
        System.out.println("Logged out.");
    }

    public void whoami() {
        System.out.println("Current user: " + (currentPlayer == null ? "(none)" : currentPlayer.getName()));
    }

    public void profile(String name) {
        PlayerProfile p = players.get(name);
        if (p == null) {
            System.out.println("No such player.");
        } else {
            p.printProfile();
        }
    }

    public void leaderboard(int topN) {
        List<PlayerProfile> sorted = new ArrayList<>(players.values());
        sorted.sort(Comparator.comparingInt(PlayerProfile::getWinCount).reversed()
                .thenComparing(PlayerProfile::getName));
        System.out.println("Top " + topN + " players:");
        for (int i = 0; i < Math.min(topN, sorted.size()); i++) {
            PlayerProfile p = sorted.get(i);
            System.out.printf("%d. %s - %d wins\n", i + 1, p.getName(), p.getWinCount());
        }
    }

    public PlayerProfile getCurrentPlayer() {
        return currentPlayer;
    }

    public void save() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            out.writeObject(players);
        } catch (IOException e) {
            System.err.println("Failed to save player data.");
        }
    }

    public void load() {
        File file = new File(SAVE_FILE);
        if (!file.exists()) {
            players = new HashMap<>();
            return;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            players = (Map<String, PlayerProfile>) in.readObject();
        } catch (Exception e) {
            System.err.println("Failed to load player data.");
            players = new HashMap<>();
        }
    }
}
