package me.frep.vulcan.spigot.bukkit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.UUID;

public class BlockMove implements Listener {
    private int tiks;
    public static HashMap<UUID, Integer> Flag = new HashMap<>();

    @EventHandler
    public void OnMove(EntityDamageByEntityEvent e) {

        Player pl = (Player) e.getDamager();
        UUID damegerUUID = pl.getUniqueId();

        if (!(pl instanceof Player)) return;

        else if (Flag.containsKey(damegerUUID)) {
            e.setDamage(1);
            if (++tiks >= 7) {
                Flag.remove(damegerUUID);
                tiks = 0;
            }
        }
    }
}

