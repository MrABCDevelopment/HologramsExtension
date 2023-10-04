package me.dreamdevs.hologramsextension;

import lombok.Getter;
import me.dreamdevs.hologramsextension.listeners.PlayerInteractChestListener;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.extensions.Extension;
import me.dreamdevs.randomlootchest.api.utils.Util;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import org.bukkit.Bukkit;

public class HologramsExtensionMain extends Extension {

    private @Getter static HologramsExtensionMain instance;
    private @Getter HologramsManager hologramsManager;

    @Override
    public void onExtensionEnable() {
        Util.sendPluginMessage("&aEnabling HologramsExtension...");
        instance = this;
        if (Bukkit.getPluginManager().getPlugin("HolographicDisplays") == null) {
            Util.sendPluginMessage("&cYou have to install HolographicDisplays to use this extension!");
            return;
        }
        saveDefaultConfig();

        this.hologramsManager = new HologramsManager(this);

        registerListener(new PlayerInteractChestListener());
        Util.sendPluginMessage("&aSuccessfully loaded HologramsExtension v1.1.0!");
    }


    @Override
    public void onExtensionDisable() {
        if (Bukkit.getPluginManager().getPlugin("HolographicDisplays") == null) {
            return;
        }
        HolographicDisplaysAPI.get(RandomLootChestMain.getInstance()).getHolograms().forEach(Hologram::delete);
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();

        hologramsManager.load();
    }
}