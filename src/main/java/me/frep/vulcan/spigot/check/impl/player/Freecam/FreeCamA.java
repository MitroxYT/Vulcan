package me.frep.vulcan.spigot.check.impl.player.Freecam;

import io.github.retrooper.packetevents.packetwrappers.play.in.flying.WrappedPacketInFlying;
import me.frep.vulcan.spigot.check.AbstractCheck;
import me.frep.vulcan.spigot.check.api.CheckInfo;
import me.frep.vulcan.spigot.data.PlayerData;
import me.frep.vulcan.spigot.exempt.type.ExemptType;
import me.frep.vulcan.spigot.packet.Packet;
@CheckInfo(name = "FreeCam", type = 'A', complexType = "Interact", experimental = false, description = "Invalid interact.")
public class FreeCamA extends AbstractCheck {
    private int streak;
    public FreeCamA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(final Packet packet) {
        if (packet.isFlying()) {
            final WrappedPacketInFlying wrapper = this.data.getFlyingWrapper();
            if (wrapper.isPosition() || this.data.getPlayer().isInsideVehicle()) {
                this.streak = 0;
                return;
            }
            final boolean exempt = this.isExempt(ExemptType.JOINED);
            if (++this.streak > 10 && !exempt) {
                this.fail("streak=" + this.streak);
            }
        } else if (packet.isSteerVehicle()) {
            this.streak = 0;
        }
    }
}
