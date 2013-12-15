package com.azubusan.modhelp.listeners;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.azubusan.modhelp.ModHelp;
import com.azubusan.modhelp.commands.modhelp.FreezeCommand;


public class IsFrozenListener implements Listener {

	private ModHelp plugin;

	public void RegisterEvents(ModHelp plugin) {
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		new FreezeCommand(plugin);
		if (FreezeCommand.frozen.contains(event.getPlayer().getName())) {
			Location back = new Location(event.getFrom().getWorld(), event
					.getFrom().getX(), event.getFrom().getY(), event.getFrom()
					.getZ());
			event.getPlayer().teleport(back);
		}
	}

	@EventHandler
	public void onPlayerLogoutWhileFrozen(final PlayerQuitEvent event) {
		new FreezeCommand(plugin);
		if (FreezeCommand.frozen.contains(event.getPlayer().getName())) {
			final Properties Banprops = new Properties();
			final File bannedfile = new File("plugins/ModHelp/BPP/ModHelp_"
					+ event.getPlayer().getName().toString() + ".PROPERTIES");
			final File frozenfile = new File("plugins/ModHelp/FPP/ModHelp_"
					+ event.getPlayer().getName().toString() + ".PROPERTIES");
			Bukkit.getOfflinePlayer(event.getPlayer().getName())
					.setBanned(true);
			try {
				bannedfile.createNewFile();
				FileOutputStream out = new FileOutputStream(bannedfile);
				FileInputStream in = new FileInputStream(frozenfile);
				Banprops.load(in);
				int banpropsint = Integer.parseInt(Banprops.getProperty(event
						.getPlayer().getName().toString()));
				Banprops.setProperty(event.getPlayer().getName().toString(),
						"§6Sorry, you logged off while frozen and have been banned until unfrozen: §8[§c"
								.toString() + banpropsint + "§8] §6seconds was your freeze duration");
				Banprops.store(out, "Banned Players | Reasons");
				out.close();
				new BukkitRunnable() {
					@Override
					public void run() {
						Bukkit.getOfflinePlayer(event.getPlayer().getName())
								.setBanned(false);
						try {
							if (bannedfile.exists()) {
								bannedfile.delete();
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}.runTaskLater(plugin, 20 * banpropsint);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
