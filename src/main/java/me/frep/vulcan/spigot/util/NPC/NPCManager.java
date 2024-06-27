/*package me.frep.vulcan.spigot.util.NPC;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import io.github.retrooper.packetevents.PacketEvents;

import io.github.retrooper.packetevents.packetwrappers.play.out.namedentityspawn.WrappedPacketOutNamedEntitySpawn;
import io.github.retrooper.packetevents.packetwrappers.play.out.playerinfo.WrappedPacketOutPlayerInfo;

import io.github.retrooper.packetevents.utils.player.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.UUID;

public class NPCManager {

    public void createNPC(Player player, Location location, String name, String skinTexture, String skinSignature) {
        UUID npcUUID = UUID.randomUUID();
        GameProfile profile = new GameProfile(npcUUID, name);
        profile.getProperties().put("textures", new Property("textures", skinTexture, skinSignature));

        // Create and send Player Info Packet
        PlayerInfoData playerInfoData = new PlayerInfoData(profile, 0, GameMode.SURVIVAL, name);
        WrappedPacketOutPlayerInfo playerInfoPacket = new WrappedPacketOutPlayerInfo(WrappedPacketOutPlayerInfo.PlayerInfoAction.ADD_PLAYER, Collections.singletonList(playerInfoData));
        PacketEvents.get().getPlayerManager().sendPacket(player, playerInfoPacket);

        // Create and send Spawn Player Packet
        WrappedPacketOutNamedEntitySpawn spawnPlayerPacket = new WrappedPacketOutNamedEntitySpawn(npcUUID, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), profile);
        PacketEvents.get().getPlayerManager().sendPacket(player, spawnPlayerPacket);

        // Teleport the NPC to the desired location
        WrappedPacketOutPlayerInfo positionPacket = new WrappedPacketOutPlayerInfo(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), Collections.emptySet());
        PacketEvents.get().getPlayerManager().sendPacket(player, positionPacket);
    }
}*/
