package me.dreamdevs.hologramsextension;

import me.dreamdevs.hologramsextension.api.HologramHook;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.Config;
import me.dreamdevs.randomlootchest.api.database.IPlayerData;
import me.dreamdevs.randomlootchest.api.extensions.Extension;
import me.dreamdevs.randomlootchest.api.utils.ColourUtil;
import me.dreamdevs.randomlootchest.api.utils.Util;
import me.dreamdevs.randomlootchest.api.utils.VersionUtil;
import me.dreamdevs.randomlootchest.utils.TimeUtil;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class HologramsManager {

	public static String CHEST_COOLDOWN_HOLOGRAM_MESSAGE;
	public static final String TIME_PLACEHOLDER = "%TIME%";

	private final FileConfiguration config;

	private HologramHook hologramHook;

	public HologramsManager(HologramsExtensionMain extension) {
		config = extension.getConfig();

		try {
			Class<? extends HologramHook> clazz = Class.forName("me.dreamdevs.hologramsextension.versions."+ VersionUtil.getVersion()+".Hologram").asSubclass(HologramHook.class);
			hologramHook = clazz.getDeclaredConstructor().newInstance();

			Util.sendPluginMessage("&aHologramsExtension hooked! You are using "+VersionUtil.getVersion()+"!");
		} catch (Exception e) {
			Util.sendPluginMessage("&cThis version is not supported!");

			extension.setState(Extension.State.DISABLED);
			return;
		}

		load();
	}

	public void load() {
		CHEST_COOLDOWN_HOLOGRAM_MESSAGE = ColourUtil.colorize(config.getString("hologram-on-chest","&cThis chest is on cooldown for "+TIME_PLACEHOLDER));
	}

	public void showHologram(Player player, Location location) {
		if (Config.USE_PERSONAL_COOLDOWN.toBoolean() && location.getChunk().isLoaded()) {
			new BukkitRunnable() {
				@Override
				public void run() {
					IPlayerData playerData = RandomLootChestMain.getInstance().getCooldownManager().getPlayerData(player);
					if ((player != null && player.isOnline()) && (playerData.hasCooldown(location)) && playerData.getLeftCooldownSeconds(location) > 0){
						hologramHook.showHologram(player, location, CHEST_COOLDOWN_HOLOGRAM_MESSAGE.replace(TIME_PLACEHOLDER,
								TimeUtil.formattedTime(RandomLootChestMain.getInstance().getCooldownManager().getCooldownByLocation(player, location))));
					} else {
						this.cancel();
					}
				}
			}.runTaskTimerAsynchronously(RandomLootChestMain.getInstance(), 20L, 20L);
		}
	}

}