package me.dreamdevs.hologramsextension.versions.v1_18_R2;

import me.dreamdevs.hologramsextension.api.HologramHook;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityLiving;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Hologram implements HologramHook {

	@Override
	public void showHologram(Player player, Location location, String text) {
		CraftWorld craftWorld = (CraftWorld) player.getLocation().getWorld();

		EntityArmorStand entityArmorStand = new EntityArmorStand(craftWorld.getHandle(), location.getX()+0.5, location.getY()+1, location.getZ()+0.5);

		entityArmorStand.getBukkitEntity().setGravity(false);
		entityArmorStand.getBukkitEntity().setInvulnerable(true);
		((CraftArmorStand)entityArmorStand.getBukkitEntity()).setMarker(true);
		((CraftArmorStand)entityArmorStand.getBukkitEntity()).setInvisible(true);
		entityArmorStand.getBukkitEntity().setCustomNameVisible(true);
		entityArmorStand.getBukkitEntity().setCustomName(text);

		PacketPlayOutSpawnEntityLiving packetPlayOutSpawnEntityLiving = new PacketPlayOutSpawnEntityLiving(entityArmorStand);

		//PacketPlayOutSpawnEntity packetPlayOutSpawnEntity = new PacketPlayOutSpawnEntity(entityArmorStand, entityArmorStand.aa());
		//PacketPlayOutEntityMetadata packetPlayOutEntityMetadata = new PacketPlayOutEntityMetadata(entityArmorStand.aa(), entityArmorStand)

		((CraftPlayer)player).getHandle().b.a(packetPlayOutSpawnEntityLiving, null);
		((CraftPlayer)player).getHandle().b.a(packetPlayOutSpawnEntityLiving, null);

		Bukkit.getScheduler().runTaskLaterAsynchronously(RandomLootChestMain.getInstance(), () -> {
			PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(entityArmorStand.aa());
			((CraftPlayer)player).getHandle().b.a(packetPlayOutEntityDestroy, null);
		}, 20L);
	}

}