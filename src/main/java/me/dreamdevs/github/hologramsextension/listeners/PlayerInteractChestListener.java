package me.dreamdevs.github.hologramsextension.listeners;

import me.dreamdevs.github.hologramsextension.HologramsExtensionMain;
import me.dreamdevs.github.randomlootchest.RandomLootChestMain;
import me.dreamdevs.github.randomlootchest.api.events.PlayerInteractChestEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerInteractChestListener implements Listener
{

    @EventHandler
    public void clickChestEvent(PlayerInteractChestEvent event) {
        if(RandomLootChestMain.getInstance().getCooldownManager().isOnCooldown(event.getPlayer().getUniqueId(), event.getChestLocation())) {
            HologramsExtensionMain.createTempHolo(event.getPlayer(), event.getChestLocation());
        }
    }

}