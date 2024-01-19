package me.dreamdevs.hologramsextension;

import lombok.Getter;
import me.dreamdevs.hologramsextension.listeners.PlayerInteractChestListener;
import me.dreamdevs.randomlootchest.api.extensions.Extension;
import me.dreamdevs.randomlootchest.api.utils.Util;

public class HologramsExtensionMain extends Extension {

    private @Getter static HologramsExtensionMain instance;
    private @Getter HologramsManager hologramsManager;

    @Override
    public void onExtensionEnable() {
        Util.sendPluginMessage("&aEnabling HologramsExtension...");
        instance = this;

        saveDefaultConfig();

        this.hologramsManager = new HologramsManager(this);

        registerListener(new PlayerInteractChestListener());
        Util.sendPluginMessage("&aSuccessfully loaded HologramsExtension v2.0.0!");
    }


    @Override
    public void onExtensionDisable() {
        Util.sendPluginMessage("&aDisabling HologramsExtension...");
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();

        hologramsManager.load();
    }
}