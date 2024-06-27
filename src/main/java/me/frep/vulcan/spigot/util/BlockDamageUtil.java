package me.frep.vulcan.spigot.util;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.UUID;

public class BlockDamageUtil implements Listener {
    private int blockedatack;
    public static HashMap<UUID, Integer> killaura = new HashMap<>();
    @EventHandler
    public void ondamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            UUID uuid = player.getUniqueId();
            if(ServerUtil.isHigherThan1_12()) {
                if (killaura.containsKey(uuid))  event.setCancelled(true);
            }
        }
    }
}
