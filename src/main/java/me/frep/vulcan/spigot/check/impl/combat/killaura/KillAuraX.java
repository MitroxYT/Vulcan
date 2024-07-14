package me.frep.vulcan.spigot.check.impl.combat.killaura;

import me.frep.vulcan.spigot.exempt.type.ExemptType;
import io.github.retrooper.packetevents.packetwrappers.play.in.useentity.WrappedPacketInUseEntity;
import me.frep.vulcan.spigot.packet.Packet;
import me.frep.vulcan.spigot.data.PlayerData;
import me.frep.vulcan.spigot.check.api.CheckInfo;
import me.frep.vulcan.spigot.check.AbstractCheck;
import ru.nik.Events.PacketBlocker;

@CheckInfo(name = "Kill Aura", type = 'X', complexType = "Multi Aura", experimental = false, description = "Combat Aim Heyristic Analyz")
public class KillAuraX extends AbstractCheck
{
    private int lastEntityId;
    private int ticks;

    public KillAuraX(final PlayerData data) {
        super(data);
    }

    @Override
    public void handle(final Packet packet) {
        if (packet.isRotation() && this.hitTicks() < 3) {
            final float deltaYaw = this.data.getRotationProcessor().getDeltaYaw();
            final boolean invalid = deltaYaw > 0.0f && (deltaYaw % 0.25 == 0.0 || deltaYaw % 0.1 == 0.0);
            if (invalid) {
                if (this.increaseBuffer() > this.MAX_BUFFER) {
                    this.fail("YawAnalyz=" + deltaYaw);
                    if (!data.getPlayer().hasPermission("vulcan.bypass.cancel.kax")) {
                        PacketBlocker.blockdamage(data.getPlayer());
                    }
                }
            } else {
                this.decayBuffer();
            }
        }
    }
}
