package me.frep.vulcan;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import me.frep.vulcan.spigot.Vulcan;
import me.frep.vulcan.spigot.data.PlayerData;
import me.frep.vulcan.spigot.data.processor.PositionProcessor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.UUID;

public class PacketManagercan {
    int blockeddm;
    int flaggg;

    public static void blockdamage(Player p) {
        UUID uuid = p.getUniqueId();
        blockitemuse.put(uuid,1);
    }
    public static void blockitemuse(Player p) {
        UUID uuid = p.getUniqueId();
        killaurausers.put(uuid,1);
    }
    public static void Flag(Player p) {
        UUID uuid = p.getUniqueId();
        flag.put(uuid,1);
    }

    private int flagtiks;
    public PacketManagercan(Plugin plugin) {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, PacketType.Play.Client.USE_ITEM, PacketType.Play.Client.USE_ENTITY,PacketType.Play.Client.BLOCK_PLACE,PacketType.Play.Client.FLYING) {
            public void onPacketReceiving(PacketEvent e) {
                UUID playerId = e.getPlayer().getUniqueId();
                Player player = e.getPlayer();
                final PlayerData data = Vulcan.INSTANCE.getPlayerDataManager().getPlayerData(player);
                if(e.getPacketType() == PacketType.Play.Client.FLYING) {
                    if (data.getPositionProcessor().flagdata) {
                        e.setCancelled(true);
                        if (++flaggg >= 38) {
                            flaggg=0;
                            data.getPositionProcessor().reset();
                        }
                    }
                }
                if (e.getPacketType() == PacketType.Play.Client.ENTITY_ACTION) {
                    if (data.getPositionProcessor().flagdata) {
                        PacketContainer packetContainer = e.getPacket();
                        EnumWrappers.PlayerAction action = packetContainer.getPlayerActions().read(0);
                        if (action == EnumWrappers.PlayerAction.START_FALL_FLYING)  {
                            e.setCancelled(true);
                            e.setCancelled(true);
                        }
                    }
                }
                /*if (e.getPacketType() == PacketType.Play.Client.USE_ITEM) {
                    if (blockitemuse.containsKey(playerId)) {
                        e.setCancelled(true);
                        e.setCancelled(true);
                        if(++tiks >= 3) {
                            tiks=0;
                            blockitemuse.remove(playerId);
                        }
                    }
                }
                if (e.getPacketType() == PacketType.Play.Client.USE_ENTITY) {

                    if (blockitemuse.containsKey(playerId)) {
                        e.setCancelled(true);
                        e.setCancelled(true);
                        if(++tiks >= 3) {
                            tiks=0;
                            blockitemuse.remove(playerId);
                        }
                    }
                }
                if (e.getPacketType() == PacketType.Play.Client.USE_ITEM) {
                    if (noslowusers.containsKey(playerId)) {
                        e.setCancelled(true);

                        e.setCancelled(true);
                        System.out.println("ggg");
                        noslowusers.remove(playerId);
                    }
                }*/
            }
        });
    }
    private int ckickaura;
    public static HashMap<UUID, Integer> killaurausers = new HashMap<>();
    public static HashMap<UUID, Integer> noslowusers = new HashMap<>();
    public static HashMap<UUID, Integer> interact = new HashMap<>();
    public static HashMap<UUID, Integer> flag = new HashMap<>();
    public static HashMap<UUID, Integer> blockitemuse = new HashMap<>();
    private int tiks;

}



