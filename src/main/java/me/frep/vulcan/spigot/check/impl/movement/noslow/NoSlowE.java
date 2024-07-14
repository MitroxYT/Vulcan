package me.frep.vulcan.spigot.check.impl.movement.noslow;

import io.github.retrooper.packetevents.packetwrappers.play.in.blockdig.WrappedPacketInBlockDig;
import io.github.retrooper.packetevents.packetwrappers.play.out.helditemslot.WrappedPacketOutHeldItemSlot;
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
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

import static me.frep.vulcan.spigot.util.PlayerUtil.sendPacket;

@CheckInfo(name = "No Slow", type = 'E', complexType = "Noslow", description = "Moving too quickly while using item.")
public class NoSlowE extends AbstractCheck
{
    public NoSlowE(final PlayerData data) {
        super(data);
    }
    private int usingtiks;
    @Override
    public void handle(final Packet packet) {
        if (packet.isPosition() && !this.teleporting() && !this.fuckedPosition()) {
            if (this.data.getActionProcessor().getJumpBoostAmplifier() > 50) {
                return;
            }
            final boolean stuck = this.data.getPositionProcessor().isFullyStuck() || this.data.getPositionProcessor().isPartiallyStuck();
            final double deltaXZ = this.data.getPositionProcessor().getDeltaXZ();
            final double deltaY = this.data.getPositionProcessor().getDeltaY();
            final int groundTicks = this.data.getPositionProcessor().getClientGroundTicks();
            final int sinceCollidingVerticallyTicks = this.data.getPositionProcessor().getSinceCollidingVerticallyTicks();
            final int sinceIceTicks = this.data.getPositionProcessor().getSinceIceTicks();
            final int sinceSlimeTicks = this.data.getPositionProcessor().getSinceNearSlimeTicks();
            final int sinceVelocityTicks = this.data.getVelocityProcessor().getTransactionFlyingTicks();
            final int sinceTrapdoorTicks = this.data.getPositionProcessor().getSinceTrapdoorTicks();
            final int sinceNearFenceTicks = this.data.getPositionProcessor().getSinceNearFenceTicks();
            final int sinceNearBedTicks = this.data.getPositionProcessor().getSinceNearBedTicks();
            final boolean wasCollidingVertically = sinceCollidingVerticallyTicks < 45;
            final boolean wasOnIce = sinceIceTicks < 60;
            final boolean wasNearBed = sinceNearBedTicks < 30;
            final boolean nearStair = this.data.getPositionProcessor().isNearStair();
            final boolean wasOnSlime = sinceSlimeTicks < 30;
            final boolean wasNearTrapdoor = sinceTrapdoorTicks < 40;
            final boolean depthStrider = this.isExempt(ExemptType.DEPTH_STRIDER);
            final boolean nearSlab = this.data.getPositionProcessor().isNearSlab();
            final boolean wasNearFence = sinceNearFenceTicks < 20;
            final boolean soulSpeed = this.data.getPositionProcessor().getSinceSoulSpeedTicks() < 30;
            final boolean wasNearSlime = this.data.getPositionProcessor().getSinceNearSlimeTicks() < 30;
            double maxSpeed = PlayerUtil.getBaseGroundSpeed(this.data);
            switch (groundTicks) {
                case 0: {
                    maxSpeed += 0.323;
                    if (wasCollidingVertically && !stuck) {
                        maxSpeed += 0.076;
                    }
                    if (wasOnIce) {
                        maxSpeed += 0.08;
                    }
                    if (nearStair) {
                        maxSpeed += 0.35;
                    }
                    if (wasCollidingVertically && wasOnIce) {
                        maxSpeed += 0.35;
                    }
                    if (wasOnSlime) {
                        maxSpeed += 0.05;
                    }
                    if (wasNearTrapdoor && wasOnIce) {
                        maxSpeed += 0.6;
                    }
                    if (nearSlab) {
                        maxSpeed += 0.04;
                    }
                    if (sinceIceTicks == 0 && nearSlab) {
                        maxSpeed += 0.1;
                    }
                    if (wasNearTrapdoor) {
                        maxSpeed += 0.1;
                    }
                    if (wasNearFence) {
                        maxSpeed += 0.1;
                    }
                    if (deltaY == 0.41999998688697815) {
                        maxSpeed += 0.03;
                        break;
                    }
                    break;
                }
                case 1: {
                    maxSpeed += 0.025;
                    if (wasCollidingVertically && !stuck) {
                        maxSpeed += 0.0728;
                    }
                    if (wasOnIce) {
                        maxSpeed += 0.1;
                    }
                    if (nearStair) {
                        maxSpeed += 0.35;
                    }
                    if (wasCollidingVertically && wasOnIce) {
                        maxSpeed += 0.45;
                    }
                    if (wasOnSlime) {
                        maxSpeed += 0.05;
                    }
                    if (wasNearTrapdoor && wasOnIce) {
                        maxSpeed += 0.55;
                    }
                    if (nearSlab) {
                        maxSpeed += 0.15;
                    }
                    if (wasNearTrapdoor) {
                        maxSpeed += 0.1;
                    }
                    if (wasNearFence) {
                        maxSpeed += 0.1;
                        break;
                    }
                    break;
                }
                case 2: {
                    maxSpeed += 0.134;
                    if (wasCollidingVertically && !stuck) {
                        maxSpeed += 0.06694;
                    }
                    if (wasOnIce) {
                        maxSpeed += 0.08;
                    }
                    if (wasNearBed) {
                        maxSpeed += 0.04;
                    }
                    if (nearStair) {
                        maxSpeed += 0.35;
                    }
                    if (wasCollidingVertically && wasOnIce) {
                        maxSpeed += 0.4;
                    }
                    if (wasNearTrapdoor && wasOnIce) {
                        maxSpeed += 0.55;
                    }
                    if (nearSlab) {
                        maxSpeed += 0.15;
                    }
                    if (wasNearTrapdoor) {
                        maxSpeed += 0.1;
                    }
                    if (sinceIceTicks == 0 && nearSlab) {
                        maxSpeed += 0.005;
                    }
                    if (wasNearFence) {
                        maxSpeed += 0.1;
                        break;
                    }
                    break;
                }
                case 3: {
                    maxSpeed += 0.0693;
                    if (wasCollidingVertically && !stuck) {
                        maxSpeed += 0.036;
                    }
                    if (wasOnIce) {
                        maxSpeed += 0.045;
                    }
                    if (nearStair) {
                        maxSpeed += 0.35;
                    }
                    if (wasNearTrapdoor && wasOnIce) {
                        maxSpeed += 0.35;
                    }
                    if (wasCollidingVertically && wasOnIce) {
                        maxSpeed += 0.35;
                    }
                    if (nearSlab) {
                        maxSpeed += 0.04;
                    }
                    if (wasNearBed) {
                        maxSpeed += 0.04;
                    }
                    if (wasNearFence) {
                        maxSpeed += 0.1;
                    }
                    if (wasNearTrapdoor) {
                        maxSpeed += 0.1;
                        break;
                    }
                    break;
                }
                case 4: {
                    maxSpeed += 0.035;
                    if (wasCollidingVertically && !stuck) {
                        maxSpeed += 0.0211;
                    }
                    if (wasOnIce) {
                        maxSpeed += 0.03;
                    }
                    if (nearStair) {
                        maxSpeed += 0.35;
                    }
                    if (wasNearTrapdoor) {
                        maxSpeed += 0.1;
                    }
                    if (wasNearTrapdoor && wasOnIce) {
                        maxSpeed += 0.35;
                    }
                    if (wasCollidingVertically && wasOnIce) {
                        maxSpeed += 0.35;
                    }
                    if (nearSlab) {
                        maxSpeed += 0.04;
                        break;
                    }
                    break;
                }
                case 5: {
                    maxSpeed += 0.0205;
                    if (wasCollidingVertically && !stuck) {
                        maxSpeed += 0.0231;
                    }
                    if (wasOnIce) {
                        maxSpeed += 0.03;
                    }
                    if (nearStair) {
                        maxSpeed += 0.35;
                    }
                    if (wasNearTrapdoor && wasOnIce) {
                        maxSpeed += 0.35;
                    }
                    if (nearSlab) {
                        maxSpeed += 0.04;
                        break;
                    }
                    break;
                }
                case 6: {
                    maxSpeed += 0.009;
                    if (wasCollidingVertically && !stuck) {
                        maxSpeed += 0.0027;
                    }
                    if (wasOnIce) {
                        maxSpeed += 0.02;
                    }
                    if (wasNearTrapdoor && wasOnIce) {
                        maxSpeed += 0.35;
                    }
                    if (nearSlab) {
                        maxSpeed += 0.04;
                        break;
                    }
                    break;
                }
                case 7: {
                    maxSpeed += 0.005;
                    if (wasOnIce) {
                        maxSpeed += 0.02;
                    }
                    if (wasNearTrapdoor && wasOnIce) {
                        maxSpeed += 0.1;
                        break;
                    }
                    break;
                }
                case 8: {
                    maxSpeed += 0.003;
                    if (wasOnIce) {
                        maxSpeed += 0.02;
                    }
                    if (sinceIceTicks == 0 && nearSlab) {
                        maxSpeed += 0.02;
                        break;
                    }
                    break;
                }
                case 9: {
                    maxSpeed += 0.0025;
                    if (wasOnIce) {
                        maxSpeed += 0.02;
                        break;
                    }
                    break;
                }
            }
            if (sinceIceTicks == 0) {
                if (groundTicks > 2 && groundTicks < 9) {
                    maxSpeed += 0.09;
                } else if (groundTicks >= 9 && groundTicks < 15) {
                    maxSpeed += 0.09;
                } else if (groundTicks >= 15) {
                    maxSpeed += 0.09;
                }
            }
            if (this.isExempt(ExemptType.FARMLAND)) {
                maxSpeed += 0.05;
            }
            if (sinceIceTicks < 30 && groundTicks >= 5 && groundTicks <= 25 && wasCollidingVertically) {
                maxSpeed += 0.15;
            }
            if (this.isExempt(ExemptType.CANCELLED_PLACE)) {
                maxSpeed += 0.08;
            }
            if (sinceIceTicks < 30 && groundTicks > 8) {
                maxSpeed += 0.15;
            }
            if (depthStrider) {
                maxSpeed += 0.1;
            }
            if (ServerUtil.isHigherThan1_16() && soulSpeed && this.data.getActionProcessor().getSpeedAmplifier() > 3) {
                maxSpeed += 1.25;
            }
            if (ServerUtil.isHigherThan1_16() && soulSpeed) {
                maxSpeed += 0.5;
            }
            if (wasOnIce && wasNearSlime) {
                maxSpeed += 0.35;
            }
            if (wasNearSlime && wasCollidingVertically) {
                maxSpeed += 0.1;
            }
            final VelocityProcessor.VelocitySnapshot snapshot = this.data.getVelocityProcessor().getSnapshot();
            double velocityXZ = this.data.getVelocityProcessor().getVelocityXZ();
            if (snapshot != null) {
                final double velocityX = snapshot.getVelocity().getX();
                final double velocityZ = snapshot.getVelocity().getZ();
                velocityXZ = MathUtil.hypot(velocityX, velocityZ);
            }
            switch (sinceVelocityTicks) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7: {
                    maxSpeed += velocityXZ + 0.05;
                    break;
                }
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15: {
                    maxSpeed += velocityXZ * 0.75;
                    break;
                }
                case 16:
                case 17:
                case 18:
                case 19:
                case 20: {
                    maxSpeed += velocityXZ * 0.6;
                    break;
                }
                case 21:
                case 22:
                case 23:
                case 24:
                case 25: {
                    maxSpeed += velocityXZ * 0.5;
                    break;
                }
                case 26:
                case 27:
                case 28:
                case 29:
                case 30: {
                    maxSpeed += velocityXZ * 0.35;
                    break;
                }
                case 31:
                case 32:
                case 33:
                case 34:
                case 35: {
                    maxSpeed += velocityXZ * 0.25;
                    break;
                }
                case 36:
                case 37:
                case 38:
                case 39:
                case 40: {
                    maxSpeed += velocityXZ * 0.1;
                    break;
                }
            }
            if (this.data.getPositionProcessor().getSinceAroundSlimeTicks() < 25) {
                maxSpeed += 1.5;
            }
            if (ServerUtil.isHigherThan1_9() && Config.ENTITY_COLLISION_FIX && this.data.getPositionProcessor().getSinceEntityCollisionTicks() < 20) {
                maxSpeed += 0.1;
            }
            if (this.data.getActionProcessor().getSinceIcePlaceTicks() < 35) {
                maxSpeed += 0.2;
            }
            if (this.isExempt(ExemptType.PISTON)) {
                maxSpeed += 0.325;
            }
            if (this.data.getPositionProcessor().getSinceAroundSlabTicks() < 5) {
                maxSpeed += 0.025;
            }
            if (this.isExempt(ExemptType.BUKKIT_VELOCITY)) {
                maxSpeed += velocityXZ * 2.0;
            }
            if (this.isExempt(ExemptType.SPEED_RAN_OUT)) {
                ++maxSpeed;
            }
            if (this.isExempt(ExemptType.SNOW)) {
                maxSpeed += 0.05;
            }
            if (this.data.getActionProcessor().getSinceRavagerDamageTicks() < 30) {
                maxSpeed += 2.0;
            }
            if (this.data.getActionProcessor().getSinceCrystalDamageTicks() < 5) {
                ++maxSpeed;
            } else if (this.data.getActionProcessor().getSinceCrystalDamageTicks() > 5 && this.data.getActionProcessor().getSinceCrystalDamageTicks() < 25) {
                maxSpeed += 0.75;
            }
            if (this.data.getPositionProcessor().isNearPressurePlate()) {
                maxSpeed += 0.08;
            }
            if (this.data.getPlayer().isHandRaised()) {
                maxSpeed -= 0.09;
            } else if (this.data.getPlayer().isHandRaised() && this.data.getActionProcessor().getSpeedAmplifier() >= 1) {
                maxSpeed -= 0.7;
            }
            final double difference = deltaXZ - maxSpeed;
            final boolean exempt = this.isExempt(ExemptType.FLIGHT, ExemptType.JOINED, ExemptType.CREATIVE, ExemptType.SHULKER, ExemptType.SHULKER_BOX, ExemptType.GLIDING, ExemptType.DOLPHINS_GRACE, ExemptType.ATTRIBUTE_MODIFIER, ExemptType.TELEPORT, ExemptType.ILLEGAL_BLOCK, ExemptType.GLIDING, ExemptType.CHUNK, ExemptType.RIPTIDE, ExemptType.VEHICLE, ExemptType.BOAT, ExemptType.FISHING_ROD, /*ExemptType.ELYTRA,*/ ExemptType.ENTITY_CRAM_FIX, ExemptType.DEATH, ExemptType.SLEEPING, ExemptType.ENDER_PEARL, ExemptType.FROZEN, ExemptType.CHORUS_FRUIT, ExemptType.SPECTATOR, ExemptType.WORLD_CHANGE, /*ExemptType.ATTRIBUTE_MODIFIER,*/ ExemptType.ANVIL, ExemptType.CANCELLED_MOVE);
            final boolean slowedbyuseitem = usingtiks >= 2;
            final boolean invalid = difference > Config.Noslow;
            if (invalid && !exempt) {
                if (this.increaseBuffer() > this.MAX_BUFFER || (difference > 0.5 && difference < 100.0 && !this.isExempt(ExemptType.SERVER_POSITION, ExemptType.CHORUS_FRUIT))) {
                    this.fail("speed=" + deltaXZ + " max=" + maxSpeed + " diff=" + difference + " ticks=" + groundTicks + " deltaY=" + deltaY);
                    if (Config.NOSLOWSWAPSLOT) {
                        for(int i =0; i<15; i++) {
                            final int random = ThreadLocalRandom.current().nextInt(8);
                            sendPacket(this.data.getPlayer(), new WrappedPacketOutHeldItemSlot(random));
                            final int random1 = ThreadLocalRandom.current().nextInt(8);
                            sendPacket(this.data.getPlayer(), new WrappedPacketOutHeldItemSlot(random1));
                        }

                    }
                }
            } else {
                this.decayBuffer();
            }
        }
    }
}
