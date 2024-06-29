package me.frep.vulcan.spigot.check.impl.combat.killaura;

import com.google.common.collect.Lists;
import io.github.retrooper.packetevents.packetwrappers.play.in.useentity.WrappedPacketInUseEntity;
import me.frep.vulcan.spigot.check.AbstractCheck;
import me.frep.vulcan.spigot.check.api.CheckInfo;
import me.frep.vulcan.spigot.data.PlayerData;
import me.frep.vulcan.spigot.packet.Packet;
import me.frep.vulcan.spigot.util.ServerUtil;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@CheckInfo(name = "Kill Aura", type = 'U', complexType = "Pattern", description = "Attaking from using item")
public class KillAuraU extends AbstractCheck {
    private long lastAttack;
    private boolean use;
    private int cancel;
    public static HashMap<UUID, Integer> killaurauser = new HashMap<>();


    public KillAuraU(final PlayerData data) {
        super(data);
    }

    @Override
    public void handle(final Packet packet) {
        if (packet.isUseEntity()) {
            final WrappedPacketInUseEntity wrapper = this.data.getUseEntityWrapper();
            Player player = data.getPlayer();

            if (wrapper.getAction() == WrappedPacketInUseEntity.EntityUseAction.ATTACK) {
                if (ServerUtil.isHigherThan1_16() && player.isHandRaised()) this.fail();

                if (ServerUtil.isLowerThan1_13() && player.isBlocking())  this.fail();

            }
        }
    }
}
