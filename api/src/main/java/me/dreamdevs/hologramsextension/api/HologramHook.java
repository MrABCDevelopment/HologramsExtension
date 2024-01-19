package me.dreamdevs.hologramsextension.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface HologramHook {

	void showHologram(Player player, Location location, String text);

}