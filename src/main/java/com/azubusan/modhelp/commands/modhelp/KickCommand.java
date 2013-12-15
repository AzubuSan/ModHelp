package com.azubusan.modhelp.commands.modhelp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.azubusan.modhelp.ModHelp;
import com.azubusan.modhelp.commands.IModHelpCommand;

public class KickCommand implements IModHelpCommand {

	@SuppressWarnings("unused")
	private ModHelp plugin;
	
	public KickCommand(ModHelp plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		if (args.length == 0) {
			return false;
		}
		if (args.length >= 2) { 
			String targetName = args[0];
			Player target = Bukkit.getServer().getPlayer(targetName);
			
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i < args.length; i++) {
				sb.append(args[i]);
				if (i < args.length) {
					sb.append(" ");
				}
			}
			
			if(target == null) {
				sender.sendMessage(ChatColor.RED + "Sorry, player is offline, cannot kick.");
				return true;
			}
			sender.sendMessage("§6Player " + "§c" + targetName + " §6kicked for " + sb.toString().replaceAll("&", "§"));
			target.kickPlayer("You were kicked for " + sb.toString().replaceAll("&", "§") + " §0by " + sender.getName());
			return true;
			
		}
		return true;
	}

	@Override
	public String getUsage() {
		return "/mh kick <player> <reason>";
	}

	@Override
	public String getPermission() {
		return "mh.commands.kick";
	}

}
