package me.frep.vulcan.spigot.check.impl.player.inventory;

import io.github.retrooper.packetevents.packetwrappers.play.in.pong.WrappedPacketInPong;
import me.frep.vulcan.spigot.Vulcan;
import me.frep.vulcan.spigot.exempt.type.ExemptType;
import io.github.retrooper.packetevents.packetwrappers.play.in.windowclick.WrappedPacketInWindowClick;
import me.frep.vulcan.spigot.packet.Packet;
import me.frep.vulcan.spigot.data.PlayerData;
import me.frep.vulcan.spigot.check.api.CheckInfo;
import me.frep.vulcan.spigot.check.AbstractCheck;
import org.bukkit.Bukkit;

@CheckInfo(name = "Inventory", type = 'A', complexType = "Sprint", description = "Sprinting while clicking in inventory.")
public class InventoryA extends AbstractCheck
{
    public InventoryA(final PlayerData data) {
        super(data);
    }
    
    @Override
    public void handle(final Packet packet) {
        if (packet.isWindowClick()) {
            final WrappedPacketInWindowClick wrapper = new WrappedPacketInWindowClick(packet.getRawPacket());
            final int sprintingTicks = this.data.getActionProcessor().getSprintingTicks();
            final double deltaXZ = this.data.getPositionProcessor().getDeltaXZ();
            final boolean exempt = this.isExempt(ExemptType.VEHICLE, ExemptType.TELEPORT, ExemptType.WORLD_CHANGE, ExemptType.SERVER_POSITION, ExemptType.GLIDING, ExemptType.FIREWORK);
            final boolean invalid = sprintingTicks > 5 && deltaXZ > 0.25;
            if (invalid && !exempt && wrapper.getWindowId() == 0) {
            //    if (this.increaseBuffer() > this.MAX_BUFFER) {
                    this.fail("ticks=" + sprintingTicks);
                    //data.getPositionProcessor().Setbackandblock();
                    //data.getPositionProcessor().Setbackandblock();
                        //Bukkit.getScheduler().runTask(Vulcan.INSTANCE.getPlugin(), () -> this.data.getPlayer().closeInventory());

                }
            //}
            //else {
                //this.decayBuffer();
            //}
        }
    }
}
