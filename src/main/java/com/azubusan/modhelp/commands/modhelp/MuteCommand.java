package com.azubusan.modhelp.commands.modhelp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.azubusan.modhelp.ModHelp;
import com.azubusan.modhelp.commands.IModHelpCommand;

public class MuteCommand implements IModHelpCommand {

	@SuppressWarnings("unused")
	private ModHelp plugin;
	
	public MuteCommand(ModHelp plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getUsage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPermission() {
		// TODO Auto-generated method stub
		return null;
	}

}
