package org.cesar.thenewpin.pin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PinMenu implements Listener {

    private static final String PAPER_PREFIX = "§aTu PIN: ";

    public static void openMenu(Player player) {
        Main plugin = Main.getInstance();
        PinManager manager = plugin.getPinManager();
        manager.startPin(player);

        Inventory inv = Bukkit.createInventory(null, 5*9, plugin.getConfig().getString("panel-title"));

        // Números 1-9
        int[] slots = {19, 20, 21, 28, 29, 30, 37, 38, 39};
        for (int i = 1; i <= 9; i++) {
            ItemStack num = new ItemStack(Material.GREEN_CONCRETE);
            ItemMeta meta = num.getItemMeta();
            meta.setDisplayName("§e" + i);
            num.setItemMeta(meta);
            inv.setItem(slots[i-1], num);
        }

        // Borrar
        ItemStack delete = new ItemStack(Material.RED_CONCRETE);
        ItemMeta deleteMeta = delete.getItemMeta();
        deleteMeta.setDisplayName("§cBorrar");
        delete.setItemMeta(deleteMeta);
        inv.setItem(40, delete);

        // Confirmar
        ItemStack confirm = new ItemStack(Material.LIME_CONCRETE);
        ItemMeta confirmMeta = confirm.getItemMeta();
        confirmMeta.setDisplayName("§aConfirmar");
        confirm.setItemMeta(confirmMeta);
        inv.setItem(41, confirm);

        // Papel que muestra el PIN
        ItemStack paper = new ItemStack(Material.PAPER);
        ItemMeta paperMeta = paper.getItemMeta();
        paperMeta.setDisplayName(PAPER_PREFIX);
        paper.setItemMeta(paperMeta);
        inv.setItem(4, paper);

        player.openInventory(inv);

        if (!plugin.getPlayerData().hasPin(player.getUniqueId())) {
            player.sendMessage("§eNo tienes PIN registrado. ¡Crea uno!");
        } else {
            player.sendMessage("§eIntroduce tu PIN para iniciar sesión.");
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player player)) return;

        if (!e.getView().getTitle().equals(Main.getInstance().getConfig().getString("panel-title"))) return;

        e.setCancelled(true);
        ItemStack clicked = e.getCurrentItem();
        if (clicked == null || clicked.getType() == Material.AIR) return;

        PinManager manager = Main.getInstance().getPinManager();
        String name = clicked.getItemMeta().getDisplayName();
        Main plugin = Main.getInstance();

        if (name.equals("§cBorrar")) {
            manager.removeLast(player);
        } else if (name.equals("§aConfirmar")) {
            if (!manager.isPinValid(player)) {
                player.sendMessage("§cEl PIN debe tener entre " +
                        plugin.getConfig().getInt("min-length") + " y " +
                        plugin.getConfig().getInt("max-length") + " números.");
                return;
            }

            String enteredPin = manager.getPin(player);

            if (!plugin.getPlayerData().hasPin(player.getUniqueId())) {
                // Registro
                plugin.getPlayerData().setPin(player.getUniqueId(), enteredPin);
                manager.setLoggedIn(player, true);
                player.sendMessage("§aPIN registrado correctamente.");
            } else {
                // Login
                String actualPin = plugin.getPlayerData().getPin(player.getUniqueId());
                if (enteredPin.equals(actualPin)) {
                    manager.setLoggedIn(player, true);
                    player.sendMessage("§aLogin exitoso.");
                } else {
                    player.sendMessage("§cPIN incorrecto.");
                    manager.clearPin(player);
                    player.closeInventory();
                    return;
                }
            }

            manager.clearPin(player);
            player.closeInventory();
        } else if (name.matches("§e[1-9]")) {
            int num = Integer.parseInt(name.replace("§e", ""));
            manager.addNumber(player, num);
        }

        // Actualizar papel con el PIN
        Inventory inv = e.getInventory();
        ItemStack paper = inv.getItem(4);
        ItemMeta meta = paper.getItemMeta();
        meta.setDisplayName(PAPER_PREFIX + manager.getPin(player));
        paper.setItemMeta(meta);
        inv.setItem(4, paper);
    }
}
