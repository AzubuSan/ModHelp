package com.azubusan.modhelp.commands.modhelp;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.azubusan.modhelp.ModHelp;
import com.azubusan.modhelp.commands.IModHelpCommand;

public class BanCommand implements IModHelpCommand {

	@SuppressWarnings("unused")
	private ModHelp plugin;

	public BanCommand(ModHelp plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length >= 2 && !sender.hasPermission("modhelp.commands.ban")) {
			sender.sendMessage(ChatColor.RED
					+ "Sorry, you don't have enough permissions");
			return true;
		}

		if (args.length >= 2 && sender.hasPermission("modhelp.commands.ban")) {
			String targetName = args[0];
			File file = new File("plugins/ModHelp/BPP/ModHelp_" +targetName.toString()+".PROPERTIES");
			OfflinePlayer offtarget = Bukkit.getOfflinePlayer(targetName);
			Player target = Bukkit.getPlayer(targetName);
			Properties Banprops = new Properties();
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i < args.length; i++) {
				sb.append(args[i]);
				if (i < args.length) {
					sb.append(" ");
				}
			}

			if (target == null) {
				if(sender.hasPermission("modhelp.commands.banoffline")) {
					sender.sendMessage("§6Offline player " + "§c" + targetName
							+ " §6banned for " + sb.toString().replaceAll("&", "§"));
					offtarget.setBanned(true);
					try {
						if(!file.exists()) {
							file.createNewFile();
							FileOutputStream out = new FileOutputStream("plugins/ModHelp/BPP/ModHelp_" +targetName.toString()+".PROPERTIES");
							Banprops.put(targetName.toString(), sb.toString().replaceAll("&", "§").toString());
							Banprops.store(out, "Banned Players | Reasons");
							out.close();
						}
						FileOutputStream out = new FileOutputStream("plugins/ModHelp/BPP/ModHelp_" +targetName.toString()+".PROPERTIES");
						Banprops.put(targetName.toString(), sb.toString().replaceAll("&", "§").toString());
						Banprops.store(out, "Banned Players | Reasons");
						out.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					return true;
				}
				sender.sendMessage(ChatColor.RED + "Sorry, player is offline, cannot ban.");
				return true;
			}
			sender.sendMessage("§6Player " + "§c" + targetName + " §6banned for " + sb.toString().replaceAll("&", "§"));
			target.kickPlayer(sb.toString().replaceAll("&", "§"));
			target.setBanned(true);
			try {
				if(!file.exists()) {
					file.createNewFile();
					FileOutputStream out = new FileOutputStream("plugins/ModHelp/BPP/ModHelp_" +targetName.toString()+".PROPERTIES");
					Banprops.put(targetName.toString(), sb.toString().replaceAll("&", "§").toString());
					Banprops.store(out, "Banned Players | Reasons");
					out.close();
				}
				FileOutputStream out = new FileOutputStream("plugins/ModHelp/BPP/ModHelp_" +targetName.toString()+".PROPERTIES");
				Banprops.put(targetName.toString(), "".toString());
				Banprops.setProperty(targetName.toString(), sb.toString().replaceAll("&", "§").toString());
				Banprops.store(out, "Banned Players | Reasons");
				out.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			return true;
		}
		return true;
	}


	@Override
	public String getUsage() {
		// TODO Auto-generated method stub
		return "/mh ban <User> <Reason>";
	}

	@Override
	public String getPermission() {
		// TODO Auto-generated method stub
		return "modhelp.commands.ban";
	}

}
