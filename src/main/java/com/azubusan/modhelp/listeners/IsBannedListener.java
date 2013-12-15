package com.azubusan.modhelp.listeners;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import com.azubusan.modhelp.ModHelp;

public class IsBannedListener implements Listener {

	@SuppressWarnings("unused")
	private ModHelp plugin;

	public void RegisterEvents(ModHelp plugin) {
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onPlayerLogin(final PlayerLoginEvent event) {
		File PropsDir = new File("plugins/ModHelp/BPP/ModHelp_"
				+ event.getPlayer().getName().toString() + ".PROPERTIES");

		if (event.getPlayer().isBanned()) {
			try {
				Properties Banprops = new Properties();
				FileInputStream fis = new FileInputStream(PropsDir);
				Banprops.load(fis);
				String banMessage = Banprops.getProperty(event.getPlayer()
						.getName().toString());
				if (Banprops
						.containsKey(event.getPlayer().getName().toString())) {
					event.disallow(Result.KICK_BANNED,
							banMessage.replaceAll("\u00A7", "§"));
					fis.close();
				}
				fis.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
