package me.dreamdevs.hologramsextension;

import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.Config;
import me.dreamdevs.randomlootchest.api.utils.ColourUtil;
import me.dreamdevs.randomlootchest.utils.TimeUtil;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.VisibilitySettings;
import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class HologramsManager {

	public static String CHEST_COOLDOWN_HOLOGRAM_MESSAGE;
	private final String TIME_PLACEHOLDER = "%TIME%";

	private final FileConfiguration config;

	public HologramsManager(HologramsExtensionMain extension) {
		config = extension.getConfig();
		load();
	}

	public void load() {
		HolographicDisplaysAPI.get(RandomLootChestMain.getInstance()).getHolograms().forEach(Hologram::delete);
		CHEST_COOLDOWN_HOLOGRAM_MESSAGE = ColourUtil.colorize(config.getString("hologram-on-chest","&cThis chest is on cooldown for "+TIME_PLACEHOLDER));
	}

	public void showHologram(Player player, Location location) {
		Hologram hologram = HolographicDisplaysAPI.get(RandomLootChestMain.getInstance()).createHologram(location.clone().add(0.5,1.5,0.5));

		if (Config.USE_PERSONAL_COOLDOWN.toBoolean()) {
			hologram.getLines().insertText(0, CHEST_COOLDOWN_HOLOGRAM_MESSAGE.replace(TIME_PLACEHOLDER,
					TimeUtil.formattedTime(RandomLootChestMain.getInstance().getCooldownManager().getCooldownByLocation(player, location))));
			hologram.getVisibilitySettings().setGlobalVisibility(VisibilitySettings.Visibility.HIDDEN);
			hologram.getVisibilitySettings().setIndividualVisibility(player, VisibilitySettings.Visibility.VISIBLE);

			new BukkitRunnable() {
				@Override
				public void run() {
					((TextHologramLine)hologram.getLines().get(0)).setText(CHEST_COOLDOWN_HOLOGRAM_MESSAGE.replace(TIME_PLACEHOLDER,
							TimeUtil.formattedTime(RandomLootChestMain.getInstance().getCooldownManager().getCooldownByLocation(player, location))));
					if (RandomLootChestMain.getInstance().getCooldownManager().getCooldownByLocation(player, location) <= 0) {
						hologram.delete();
						cancel();
					}
				}
			}.runTaskTimer(RandomLootChestMain.getInstance(), 20L, 20L);
		} else {
			hologram.getLines().insertText(0, CHEST_COOLDOWN_HOLOGRAM_MESSAGE.replace(TIME_PLACEHOLDER,
					TimeUtil.formattedTime(RandomLootChestMain.getInstance().getCooldownManager().getCooldownForAllByLocation(location))));
			hologram.getVisibilitySettings().setGlobalVisibility(VisibilitySettings.Visibility.VISIBLE);

			new BukkitRunnable() {
				@Override
				public void run() {
					((TextHologramLine)hologram.getLines().get(0)).setText(CHEST_COOLDOWN_HOLOGRAM_MESSAGE.replace(TIME_PLACEHOLDER,
							TimeUtil.formattedTime(RandomLootChestMain.getInstance().getCooldownManager().getCooldownByLocation(player, location))));
					if (RandomLootChestMain.getInstance().getCooldownManager().getCooldownForAllByLocation(location) <= 0) {
						hologram.delete();
						cancel();
					}
				}
			}.runTaskTimer(RandomLootChestMain.getInstance(), 20L, 20L);
		}

	}

}