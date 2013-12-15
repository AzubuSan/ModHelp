package com.azubusan.modhelp.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.azubusan.modhelp.ModHelp;
import com.azubusan.modhelp.commands.modhelp.BanCommand;
import com.azubusan.modhelp.commands.modhelp.FreezeCommand;
import com.azubusan.modhelp.commands.modhelp.KickCommand;
import com.azubusan.modhelp.commands.modhelp.KillCommand;
import com.azubusan.modhelp.commands.modhelp.MuteCommand;
import com.azubusan.modhelp.commands.modhelp.TempBanCommand;
import com.azubusan.modhelp.commands.modhelp.UnbanCommand;
import com.azubusan.modhelp.commands.modhelp.WarnCommand;

/**
 * 
 * Base command for all modhelp commands
 * 
 * @author AzubuSan
 * 
 */

public class ModHelpCommand implements CommandExecutor {

	private Map<String, IModHelpCommand> subCommands = new HashMap<>();

	public ModHelpCommand(ModHelp plugin) {
		subCommands.put("tempban", new TempBanCommand(plugin));
		subCommands.put("ban", new BanCommand(plugin));
		subCommands.put("freeze", new FreezeCommand(plugin));
		subCommands.put("kill", new KillCommand(plugin));
		subCommands.put("kick", new KickCommand(plugin));
		subCommands.put("mute", new MuteCommand(plugin));
		subCommands.put("warn", new WarnCommand(plugin));
		subCommands.put("unban", new UnbanCommand(plugin));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		// Entering the basecommand without arguments is assumed to be an ask
		// for help, lets show them that
		if (args.length == 0) {
			for (IModHelpCommand modHelpCommand : subCommands.values()) {
				String permission = modHelpCommand.getPermission();
				if (permission != null && sender.hasPermission(permission)) {
					sender.sendMessage(ChatColor.GOLD
							+ modHelpCommand.getUsage());
				}
			}
			return true;
		}

		String subCommandName = args[0];
		IModHelpCommand modHelpCommand = subCommands.get(subCommandName
				.toLowerCase());
		if (modHelpCommand == null) {
			return false; // Sender mistyped command or command didn't exist
		}
		
		//Permissions check handling
		String permission = modHelpCommand.getPermission();
		if (permission != null && !sender.hasPermission(permission)) {
			sender.sendMessage(ChatColor.RED
					+ "Sorry, you dont have permission to use this command.");
			return true;
		}
		
		// Remove the subCommand from the args list and pass along the rest
				if (!modHelpCommand.onCommand(sender, command, label,
						Arrays.copyOfRange(args, 1, args.length))) {
					// A subCommand returning a false should display usage information
					// for that subCommand
					sender.sendMessage(modHelpCommand.getUsage());
				}
		return true;
	}

}
