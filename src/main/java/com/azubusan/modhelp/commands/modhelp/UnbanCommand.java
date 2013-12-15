package com.azubusan.modhelp.commands.modhelp;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.azubusan.modhelp.ModHelp;
import com.azubusan.modhelp.commands.IModHelpCommand;

public class UnbanCommand implements IModHelpCommand {

	@SuppressWarnings("unused")
	private ModHelp plugin;
	
	public UnbanCommand(ModHelp plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		if(args.length == 0) {
			return false;
		}
		
		if(args.length == 1) {
			String targetName = args[0];
			OfflinePlayer offtarget = Bukkit.getOfflinePlayer(targetName);
			File file = new File("plugins/ModHelp/BPP/ModHelp_" + targetName.toString()+ ".PROPERTIES");
			offtarget.setBanned(false);
			try {
				if(!file.exists()) {
					return true;
				}
				file.delete();
				sender.sendMessage("§6Player " + "§c" + targetName + " §6unbanned");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			return true;
		}
		return true;
	}

	@Override
	public String getUsage() {
		
		return "/mh unban <player>";
	}

	@Override
	public String getPermission() {
		return "modhelp.commands.unban";
	}

}
