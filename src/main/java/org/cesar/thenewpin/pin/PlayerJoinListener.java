package org.cesar.thenewpin.pin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Abrir menú al entrar, 1 tick después para asegurar carga del jugador
        player.getServer().getScheduler().runTaskLater(Main.getInstance(), () -> {
            PinMenu.openMenu(player);
        }, 1L);
    }
}
