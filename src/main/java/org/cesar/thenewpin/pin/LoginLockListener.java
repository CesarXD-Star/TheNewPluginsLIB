package org.cesar.thenewpin.pin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class LoginLockListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (!Main.getInstance().getPinManager().isLoggedIn(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!Main.getInstance().getPinManager().isLoggedIn(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (!Main.getInstance().getPinManager().isLoggedIn(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (!Main.getInstance().getPinManager().isLoggedIn(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof org.bukkit.entity.Player player) {
            if (!Main.getInstance().getPinManager().isLoggedIn(player)) {
                e.setCancelled(true);
            }
        }
    }
}
