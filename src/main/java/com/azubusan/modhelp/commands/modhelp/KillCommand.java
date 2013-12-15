package com.azubusan.modhelp.commands.modhelp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.azubusan.modhelp.ModHelp;
import com.azubusan.modhelp.commands.IModHelpCommand;

public class KillCommand implements IModHelpCommand {
	
	@SuppressWarnings("unused")
	private ModHelp plugin;
	
	public KillCommand(ModHelp plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		if (args.length <= 0) {
			return false;
		}
		if(args.length >= 2 && sender.hasPermission("mh.commands.kill")) {
			String targetName = args[0];
			Player target = Bukkit.getServer().getPlayer(targetName);
			
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i < args.length; i++) {
				sb.append(args[i]);
				if (i < args.length) {
					sb.append(" ");
				}
			}
			
			if (target == null) {
				sender.sendMessage(ChatColor.RED + "Sorry, player is offline, cannot kill.");
				return true;
			}
			target.setHealth(0.0);
			target.sendMessage(ChatColor.GOLD + "You were killed for " + sb.toString().replaceAll("&", "§") + ChatColor.GOLD + " by " + sender.getName());
			sender.sendMessage("§6Player " + "§c" + targetName + " §6killed for " + sb.toString().replaceAll("&", "§"));
			return true;
			
		}
		return true;
	}
	
	 @Override
	public String getPermission() {
		return "mh.commands.kill";
	}

	@Override
	public String getUsage() {
		return "/mh kill <player> <reason>";
	}
	 

}
