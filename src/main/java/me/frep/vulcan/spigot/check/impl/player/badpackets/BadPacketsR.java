package me.frep.vulcan.spigot.check.impl.player.badpackets;

import me.frep.vulcan.spigot.exempt.type.ExemptType;
import me.frep.vulcan.spigot.packet.Packet;
import me.frep.vulcan.spigot.data.PlayerData;
import me.frep.vulcan.spigot.check.api.CheckInfo;
import me.frep.vulcan.spigot.check.AbstractCheck;

@CheckInfo(name = "Bad Packets", type = 'R', complexType = "Post HeldItemSlot", description = "Post HeldItemSlot packets.")
public class BadPacketsR extends AbstractCheck {
    private long lastFlying;
    private boolean sent;
    private long lastSlotChange;
    private boolean usedPearl;

    public BadPacketsR(final PlayerData data) {
        super(data);
        this.lastFlying = 0L;
        this.sent = false;
        this.lastSlotChange = 0L;
        this.usedPearl = false;
    }

    @Override
    public void handle(final Packet packet) {
        if (packet.isFlying()) {
            final long delay = System.currentTimeMillis() - this.lastFlying;
            final boolean exempt = this.isExempt(ExemptType.CREATIVE, ExemptType.SPECTATOR);
            if (this.sent && !exempt) {
                if (delay > 40L && delay < 100L) {
                    if (this.increaseBuffer() > this.MAX_BUFFER) {
                        this.fail("delay=" + delay);
                    }
                } else {
                    this.decayBuffer();
                }
                this.sent = false;
            }
            this.lastFlying = System.currentTimeMillis();
        } else if (packet.isHeldItemSlot()) {
            final long delay = System.currentTimeMillis() - this.lastFlying;
            if (delay < 10L) {
                this.sent = true;
            }
            this.lastSlotChange = System.currentTimeMillis();
        } else if (packet.isBlockPlace() &&  this.data.getPlayer().getItemInHand().getType().toString().contains("ENDER_PEARL")) {
            final long delay = System.currentTimeMillis() - this.lastSlotChange;
            if (delay < 10L) {
                this.usedPearl = true;
            }
        }

        if (this.usedPearl && !packet.isHeldItemSlot() && !this.isExempt(ExemptType.CREATIVE, ExemptType.SPECTATOR)) {
            final long delay = System.currentTimeMillis() - this.lastSlotChange;
            if (delay < 10L) {
                //if (this.increaseBuffer() > this.MAX_BUFFER) {
                    this.fail("Fast use peal, delay=" + delay);
                }
            //} else {
                //this.decayBuffer();
            //}
            this.usedPearl = false;
        }
    }
}
