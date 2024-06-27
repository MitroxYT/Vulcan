package me.frep.vulcan.spigot.check.impl.movement.vclip;

import me.frep.vulcan.spigot.exempt.type.ExemptType;
import me.frep.vulcan.spigot.util.value.Values;
import me.frep.vulcan.spigot.packet.Packet;
import me.frep.vulcan.spigot.data.PlayerData;
import me.frep.vulcan.spigot.check.api.CheckInfo;
import me.frep.vulcan.spigot.check.AbstractCheck;

@CheckInfo(name = "VClip", type = 'B', complexType = "Clip", description = "Large vertical movement.")
public class VClipB extends AbstractCheck
{
    public VClipB(final PlayerData data) {
        super(data);
    }

    @Override
    public void handle(final Packet packet) {
        if (packet.isPosition() && !this.teleporting()) {
            final double deltaY = this.data.getPositionProcessor().getDeltaY();
            final boolean exempt = this.isExempt(ExemptType.DEATH, ExemptType.JOINED, ExemptType.TELEPORT, ExemptType.WORLD_CHANGE, ExemptType.FALL_DAMAGE, ExemptType.HIGH_FLY_SPEED);
            if (Math.abs(deltaY) > 3.0 && !exempt)
                if (this.increaseBuffer() > this.MAX_BUFFER)
                    this.fail();
                else
                    this.decayBuffer();
        }
    }
}
