package me.frep.vulcan.spigot.check.impl.combat.killaura;

import com.google.common.collect.Lists;
import io.github.retrooper.packetevents.packetwrappers.play.in.useentity.WrappedPacketInUseEntity;
import me.frep.vulcan.spigot.check.AbstractCheck;
import me.frep.vulcan.spigot.check.api.CheckInfo;
import me.frep.vulcan.spigot.data.PlayerData;
import me.frep.vulcan.spigot.packet.Packet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@CheckInfo(name = "Kill Aura", type = 'M', complexType = "Pattern", description = "Generic rotation and combat analysis heuristic.")
public class KillAuraM extends AbstractCheck {
    private final List<Long> samples;
    private long lastAttack;
    private int cancel;
    public static HashMap<UUID, Integer> killaurauser = new HashMap<>();
    private final Map<UUID, AttackData> playerAttacks = new ConcurrentHashMap<>();

    public KillAuraM(final PlayerData data) {
        super(data);
        this.samples = Lists.newArrayList();
    }

    @Override
    public void handle(final Packet packet) {
        if (packet.isUseEntity()) {
            final WrappedPacketInUseEntity wrapper = this.data.getUseEntityWrapper();
            Player player = data.getPlayer();

            if (wrapper.getAction() == WrappedPacketInUseEntity.EntityUseAction.ATTACK) {
                UUID playerId = player.getUniqueId();
                playerAttacks.putIfAbsent(playerId, new AttackData());
                AttackData attackData = playerAttacks.get(playerId);

                attackData.incrementAttack();

                if (attackData.getAttackCount() > 16) {
                    Entity target = wrapper.getEntity();
                    if (target != null && target.getType() == EntityType.PLAYER) {
                        if (this.increaseBuffer() > this.MAX_BUFFER) {
                            this.fail("range=" + attackData.attackCount);
                            attackData.resetAttackCount(); // Reset attack count after detection
                        }
                    } else {
                        this.decayBuffer();
                    }
                }
            }
        }
    }

    private static class AttackData {
        private static final long INTERVAL = 15000;
        private long lastReset;
        private int attackCount;

        public AttackData() {
            this.lastReset = System.currentTimeMillis();
            this.attackCount = 0;
        }

        public synchronized void incrementAttack() {
            long now = System.currentTimeMillis();
            if (now - lastReset > INTERVAL) {
                attackCount = 0;
                lastReset = now;
            }
            attackCount++;
        }

        public synchronized int getAttackCount() {
            return attackCount;
        }

        public synchronized void resetAttackCount() {
            this.attackCount = 0;
        }
    }
}
