package me.frep.vulcan.spigot.check.impl.movement.noslow;

import io.github.retrooper.packetevents.packetwrappers.play.in.blockdig.WrappedPacketInBlockDig;
import me.frep.vulcan.spigot.data.processor.VelocityProcessor;
import me.frep.vulcan.spigot.config.Config;
import me.frep.vulcan.spigot.util.MathUtil;
import me.frep.vulcan.spigot.util.ServerUtil;
import me.frep.vulcan.spigot.exempt.type.ExemptType;
import me.frep.vulcan.spigot.util.PlayerUtil;
import me.frep.vulcan.spigot.packet.Packet;
import me.frep.vulcan.spigot.data.PlayerData;
import me.frep.vulcan.spigot.check.api.CheckInfo;
import me.frep.vulcan.spigot.check.AbstractCheck;

@CheckInfo(name = "No Slow", type = 'E', complexType = "Air", description = "Moving too quickly in the air.")
public class NoSlowE extends AbstractCheck
{
    public NoSlowE(final PlayerData data) {
        super(data);
    }

    @Override
    public void handle(final Packet packet) {
        if (packet.isBlockDig()) {
            final WrappedPacketInBlockDig wrapper = this.data.getBlockDigWrapper();
            final boolean action = wrapper.getDigType() == WrappedPacketInBlockDig.PlayerDigType.RELEASE_USE_ITEM;
            final double deltaXZ = this.data.getPositionProcessor().getDeltaXZ();
            final double deltaY = this.data.getPositionProcessor().getDeltaY();
            final int airTicks = this.data.getPositionProcessor().getClientAirTicks();
            final boolean ice = this.data.getPositionProcessor().getSinceIceTicks() < 40;
            final boolean collidingVertically = this.data.getPositionProcessor().getSinceCollidingVerticallyTicks() < 30;
            final boolean slime = this.data.getPositionProcessor().getSinceNearSlimeTicks() < 30;
            final boolean soulSpeed = this.data.getPositionProcessor().getSinceSoulSpeedTicks() < 30;
            double maxSpeed = PlayerUtil.getBaseSpeed(this.data);
            if (action) maxSpeed = 4.0;
            final boolean exempt = this.isExempt(ExemptType.JOINED, ExemptType.ILLEGAL_BLOCK, ExemptType.RIPTIDE, ExemptType.SHULKER, ExemptType.CHORUS_FRUIT, ExemptType.GLIDING, ExemptType.FLIGHT, ExemptType.CREATIVE, ExemptType.VEHICLE, ExemptType.SHULKER_BOX, ExemptType.CHUNK, ExemptType.FISHING_ROD, ExemptType.DOLPHINS_GRACE, ExemptType.ENDER_PEARL, ExemptType.TELEPORT, ExemptType.WORLD_CHANGE, ExemptType.ELYTRA, ExemptType.FROZEN, ExemptType.DEATH, ExemptType.SLEEPING, ExemptType.BOAT, ExemptType.SPECTATOR, ExemptType.ATTRIBUTE_MODIFIER, ExemptType.ANVIL, ExemptType.FULLY_STUCK, ExemptType.PARTIALLY_STUCK, ExemptType.CANCELLED_MOVE, ExemptType.ENTITY_CRAM_FIX);
            final double difference = deltaXZ - maxSpeed;
            final boolean invalid = deltaXZ >= 4.0;  // Проверка на превышение 4.0
            if (invalid && !exempt) {
                this.fail("speed=" + deltaXZ + " max=" + maxSpeed + " diff=" + difference + " ticks=" + airTicks + " deltaY=" + deltaY);
            }
        }
    }
}
