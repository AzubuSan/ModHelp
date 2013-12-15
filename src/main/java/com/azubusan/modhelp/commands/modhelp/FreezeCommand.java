package com.azubusan.modhelp.commands.modhelp;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.azubusan.modhelp.ModHelp;
import com.azubusan.modhelp.commands.IModHelpCommand;

public class FreezeCommand implements IModHelpCommand {

	private ModHelp plugin;

	public FreezeCommand(ModHelp plugin) {
		this.plugin = plugin;
	}

	public static List<String> frozen = new ArrayList<String>();

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, final String[] args) {
		if (args.length <= 1) {
			return false;
		}

		if (args.length >= 3) {

			final String targetName = args[0];
			final Player target = Bukkit.getPlayer(targetName);
			final File file = new File("plugins/ModHelp/FPP/ModHelp_"
					+ targetName.toString() + ".PROPERTIES");
			Properties Banprops = new Properties();

			StringBuilder sb = new StringBuilder();
			for (int i = 2; i < args.length; i++) {
				sb.append(args[i]);
				if (i < args.length) {
					sb.append(" ");
				}
			}

			if (target == null) {
				sender.sendMessage(ChatColor.RED
						+ "Sorry, player is offline, cannot freeze.");
				return true;
			}
			if (!frozen.contains(targetName)) {
				frozen.add(targetName);
				try {
					if (!file.exists()) {
						file.createNewFile();
						FileOutputStream out = new FileOutputStream(
								"plugins/ModHelp/FPP/ModHelp_"
										+ targetName.toString() + ".PROPERTIES");
						Banprops.put(targetName, args[1].toString());
						Banprops.store(out, "Frozen Player | Duration");
						out.close();
					}
					FileOutputStream out = new FileOutputStream(
							"plugins/ModHelp/FPP/ModHelp_"
									+ targetName.toString() + ".PROPERTIES");
					Banprops.put(targetName.toString(), args[1].toString());
					Banprops.store(out, "Frozen Player | Duration");
					out.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				target.sendMessage("§6You were frozen for§c " + args[1]
						+ " §6seconds by§c " + sender.getName() + " §6for "
						+ sb.toString().replaceAll("&", "§"));
				sender.sendMessage("§6Player " + "§c" + targetName
						+ " §6frozen for " + "§c" + args[1] + " §6seconds for "
						+ sb.toString().replaceAll("&", "§"));
				try {
					final int banpropsint = Integer.parseInt(Banprops
							.getProperty(targetName).toString());
					new BukkitRunnable() {
						@Override
						public void run() {
							frozen.remove(targetName);
							target.sendMessage("§6Unfrozen after§c " + args[1]
									+ " §6seconds");
							try {
								file.delete();
							} catch(Exception ex) {
								ex.printStackTrace();
							}
						}
					}.runTaskLater(plugin, 20 * banpropsint);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				return true;
			}
			frozen.remove(targetName);
			target.sendMessage("§6You were unfrozen by§c " + sender.getName());
			return true;

		}
		return true;
	}

	@Override
	public String getUsage() {
		return "§6/mh freeze <player> <duration> <reason>";
	}

	@Override
	public String getPermission() {
		return "mh.commands.freeze";
	}

}
