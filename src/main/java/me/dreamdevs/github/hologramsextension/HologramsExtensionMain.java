package me.dreamdevs.github.hologramsextension;

import me.dreamdevs.github.hologramsextension.listeners.PlayerInteractChestListener;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.extensions.Extension;
import me.dreamdevs.github.randomlootchest.utils.TimeUtil;
import me.dreamdevs.github.randomlootchest.utils.Util;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.VisibilitySettings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class HologramsExtensionMain extends Extension
{

    @Override
    public void onExtensionEnable() {
        Util.sendPluginMessage("&aEnabling HologramsExtension...");
        if(Bukkit.getPluginManager().getPlugin("HolographicDisplays") == null) {
            Util.sendPluginMessage("&cYou have to install HolographicDisplays to use this extension!");
            return;
        }
        saveDefaultConfig();
        RandomLootChestMain.getInstance().getMessagesManager().addMessage("hologram-on-chest", getConfig().getString("hologram-on-chest"));
        registerListener(new PlayerInteractChestListener());
        Util.sendPluginMessage("&aLoaded configuration file, registered listeners...");
    }


    @Override
    public void onExtensionDisable() {

    }

    public static void createTempHolo(Player player, Location location) {
        String text = RandomLootChestMain.getInstance().getMessagesManager().getMessages().get("hologram-on-chest");
        String time = TimeUtil.formattedTime(RandomLootChestMain.getInstance().getCooldownManager().getPlayerCooldown(player.getUniqueId(), location));
        HolographicDisplaysAPI holoAPI = HolographicDisplaysAPI.get(RandomLootChestMain.getInstance());
        Hologram hologram = holoAPI.createHologram(location.clone().add(0.5, 1.5, 0.5));
        hologram.getLines().appendText(text.replace("%TIME%", time));
        hologram.getVisibilitySettings().setGlobalVisibility(VisibilitySettings.Visibility.HIDDEN);
        hologram.getVisibilitySettings().setIndividualVisibility(player, VisibilitySettings.Visibility.VISIBLE);
        Bukkit.getScheduler().runTaskLater(RandomLootChestMain.getInstance(), hologram::delete, 20L);
    }
}