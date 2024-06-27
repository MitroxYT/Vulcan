/*package me.frep.vulcan.spigot.check.impl;

import com.google.common.collect.Lists;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import io.github.retrooper.packetevents.packetwrappers.play.in.useentity.WrappedPacketInUseEntity;
import me.frep.vulcan.spigot.Vulcan;
import me.frep.vulcan.spigot.check.AbstractCheck;
import me.frep.vulcan.spigot.check.api.CheckInfo;
import me.frep.vulcan.spigot.data.PlayerData;
import me.frep.vulcan.spigot.packet.Packet;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
@CheckInfo(name = "Kill Aura", type = '2', complexType = "Pattern", description = "Cancel damage from detected for killura")
public class CancelPacketManager extends AbstractCheck {
    private final List<Long> samples;
    private long lastAttack;
    private int canceldamage;
    public static HashMap<UUID, Integer> killaurausers = new HashMap<>();
    public CancelPacketManager(final PlayerData data) {
        super(data);
        this.samples = Lists.newArrayList();
    }
    @Override
    public void handle(final Packet packet) {
        UUID plid = data.getPlayer().getUniqueId();
        if (packet.isUseEntity()) {
            final WrappedPacketInUseEntity wrapper = this.data.getUseEntityWrapper();
            final WrappedPacketInUseEntity event = (WrappedPacketInUseEntity) packet.getRawPacket().getRawNMSPacket();
            if(killaurausers.containsKey(plid)) {
                if(++canceldamage >= 5) killaurausers.remove(plid);
                event.setTarget(null);
            }
        }
    }
}
*/