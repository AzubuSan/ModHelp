package com.azubusan.modhelp;

import java.io.File;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.azubusan.modhelp.commands.ModHelpCommand;
import com.azubusan.modhelp.listeners.IsBannedListener;
import com.azubusan.modhelp.listeners.IsFrozenListener;

/**
 * 
 * ModHelp - A plugin made to make moderating easier and more efficient Version
 * 1.0.0
 * 
 * @author AzubuSan
 * 
 */

public class ModHelp extends JavaPlugin implements Listener {

	@SuppressWarnings("unused")
	private ModHelp plugin;

	@Override
	public void onEnable() {

		// External Class Events
		new IsBannedListener().RegisterEvents(this); // Ban Listener
		new IsFrozenListener().RegisterEvents(this); // Freeze Listener

		// Register Commands
		getCommand("modhelp").setExecutor(new ModHelpCommand(this));

		// Generate config while saving default values
		saveDefaultConfig();

		// Load custom properties diretories
		
				File warnedFolder = new File(getDataFolder() + File.separator
						+ "WPP");
				if (!warnedFolder.exists()) {
					getLogger()
							.info("Warned players properties folder not found, generating...");
					warnedFolder.mkdir();
					getLogger().info(
							"Warned players properties folder created.");
				} else {
					getLogger()
							.info("Warned players properties folder found, good to go!");
				}

				File bannedFolder = new File(getDataFolder() + File.separator
						+ "BPP");
				if (!bannedFolder.exists()) {
					getLogger()
							.info("Banned players properties folder not found, generating...");
					bannedFolder.mkdir();
					getLogger().info(
							"Banned players properties folder created.");
				} else {
					getLogger()
							.info("Banned players properties folder found, good to go!");
				}

				File frozenFolder = new File(getDataFolder() + File.separator
						+ "FPP");
				if (!frozenFolder.exists()) {
					getLogger()
							.info("Frozen player properties folder not found, generating...");
					frozenFolder.mkdir();
					getLogger()
							.info("Frozen player properties folder created.");
				} else {
					getLogger()
							.info("Frozen players properties folder found, good to go!");
				}

		this.getLogger().info("Enabled!");
	}

	// Event to check if the plugin developer logs on, can be disabled via the
	// config
	
}
