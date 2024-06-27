package me.frep.vulcan.spigot.check.impl.movement.vclip;

import me.frep.vulcan.spigot.exempt.type.ExemptType;
import me.frep.vulcan.spigot.util.value.Values;
import me.frep.vulcan.spigot.packet.Packet;
import me.frep.vulcan.spigot.data.PlayerData;
import me.frep.vulcan.spigot.check.api.CheckInfo;
import me.frep.vulcan.spigot.check.AbstractCheck;

@CheckInfo(name = "VClip", type = 'A', complexType = "Clip", description = "Large vertical movement.")
public class VClipA extends AbstractCheck
{
    public VClipA(final PlayerData data) {
        super(data);
    }
    
    @Override
    public void handle(final Packet packet) {
        if (packet.isPosition() && !this.teleporting() && !this.fuckedPosition()) {
            if (this.data.getActionProcessor().getTicksExisted() < 30) {
                return;
            }
            final int airTicks = this.data.getPositionProcessor().getClientAirTicks();
            final double deltaY = this.data.getPositionProcessor().getDeltaY();
            if (airTicks < 3 && airTicks > 0) {
                final double expected = Values.JUMP_MOTION_DOWN.get(airTicks);
                final double difference = Math.abs(deltaY - expected);
                final boolean explosion = this.data.getActionProcessor().getSinceExplosionDamageTicks() < 50;
                final boolean damage = this.data.getActionProcessor().getSinceDamageTicks() < 20;
                final boolean exempt = this.isExempt(ExemptType.JOINED, ExemptType.TELEPORT, ExemptType.LIQUID, ExemptType.CLIMBABLE, ExemptType.GLIDING, ExemptType.RIPTIDE, ExemptType.COLLIDING_VERTICALLY, ExemptType.BOAT, ExemptType.TRAPDOOR, ExemptType.FLIGHT, ExemptType.CREATIVE, ExemptType.SLAB, ExemptType.FENCE, ExemptType.JUMP_BOOST, ExemptType.HONEY, ExemptType.FISHING_ROD, ExemptType.WEB, ExemptType.FULLY_STUCK, ExemptType.SHULKER_BOX, ExemptType.VEHICLE, ExemptType.BED, ExemptType.BLOCK_PLACE, ExemptType.SLEEPING, ExemptType.ANVIL, ExemptType.PISTON, ExemptType.TURTLE_EGG, ExemptType.DOOR, ExemptType.DEATH, ExemptType.PARTIALLY_STUCK, ExemptType.WALL, ExemptType.PLACED_WEB, ExemptType.ENDER_PEARL, ExemptType.BUKKIT_VELOCITY, ExemptType.LEVITATION, ExemptType.BUBBLE_COLUMN, ExemptType.FROZEN, ExemptType.SWEET_BERRIES, ExemptType.EMPTIED_BUCKET, ExemptType.SERVER_POSITION_FAST, ExemptType.FARMLAND, ExemptType.LENIENT_SCAFFOLDING, ExemptType.SEAGRASS, ExemptType.DRIPSTONE, ExemptType.WATERLOGGED, ExemptType.CHEST, ExemptType.SOUL_SAND, ExemptType.POWDER_SNOW);
                if (!exempt && deltaY < 0.0 && difference > 0.75) {
                    this.fail("deltaY=" + deltaY + " ticks=" + airTicks);
                }
            }
        }
    }
}
