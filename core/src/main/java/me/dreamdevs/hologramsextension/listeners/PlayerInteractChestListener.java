package me.dreamdevs.hologramsextension.listeners;

import me.dreamdevs.hologramsextension.HologramsExtensionMain;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.Config;
import me.dreamdevs.randomlootchest.api.events.PlayerInteractChestEvent;
import me.dreamdevs.randomlootchest.managers.CooldownManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerInteractChestListener implements Listener {

    private final CooldownManager cooldownManager = RandomLootChestMain.getInstance().getCooldownManager();

    @EventHandler
    public void clickChestEvent(PlayerInteractChestEvent event) {
        if (!cooldownManager.getPlayerData(event.getPlayer()).hasCooldown(event.getChestLocation()) && Config.USE_PERSONAL_COOLDOWN.toBoolean()) {
            HologramsExtensionMain.getInstance().getHologramsManager().showHologram(event.getPlayer(), event.getChestLocation());
        }
    }

}