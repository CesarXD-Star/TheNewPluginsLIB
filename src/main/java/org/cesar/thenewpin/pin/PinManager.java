package org.cesar.thenewpin.pin;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public class PinManager implements Listener {

    private final Map<Player, StringBuilder> playerPins = new HashMap<>();
    private final Map<Player, Boolean> loggedIn = new HashMap<>();

    public void startPin(Player player) {
        playerPins.put(player, new StringBuilder());
        loggedIn.put(player, false);
    }

    public void addNumber(Player player, int number) {
        StringBuilder pin = playerPins.get(player);
        if (pin != null && pin.length() < org.cesar.thenewpin.pin.Main.getInstance().getConfig().getInt("max-length")) {
            pin.append(number);
        }
    }

    public void removeLast(Player player) {
        StringBuilder pin = playerPins.get(player);
        if (pin != null && pin.length() > 0) {
            pin.deleteCharAt(pin.length() - 1);
        }
    }

    public String getPin(Player player) {
        return playerPins.getOrDefault(player, new StringBuilder()).toString();
    }

    public boolean isPinValid(Player player) {
        int length = getPin(player).length();
        return length >= org.cesar.thenewpin.pin.Main.getInstance().getConfig().getInt("min-length");
    }

    public void clearPin(Player player) {
        playerPins.remove(player);
    }

    public void setLoggedIn(Player player, boolean value) {
        loggedIn.put(player, value);
    }

    public boolean isLoggedIn(Player player) {
        return loggedIn.getOrDefault(player, false);
    }
}
