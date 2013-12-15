package com.azubusan.modhelp.commands.modhelp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.azubusan.modhelp.ModHelp;
import com.azubusan.modhelp.commands.IModHelpCommand;

public class WarnCommand implements IModHelpCommand {

	@SuppressWarnings("unused")
	private ModHelp plugin;

	public WarnCommand(ModHelp plugin) {
		this.plugin = plugin;
	}

	public List<String> NotWarned = new ArrayList<String>();
	public List<String> WarnedOne = new ArrayList<String>();
	public List<String> WarnedTwo = new ArrayList<String>();
	public List<String> WarnedThree = new ArrayList<String>();

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 1 || args.length == 0) {
			return false;
		}

		if (args.length >= 2) {
			String targetName = args[0];
			Player target = Bukkit.getServer().getPlayer(targetName);
			File warnedProps = new File("plugins/ModHelp/WPP/ModHelp_"
					+ targetName.toString() + ".PROPERTIES");
			final File bannedProps = new File("plugins/ModHelp/BPP/ModHelp_"
					+ targetName.toString() + ".PROPERTIES");
			Properties Warnprops = new Properties();

			// String builder //
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i < args.length; i++) {
				sb.append(args[i]);
				if (i < args.length) {
					sb.append(" ");
				}
			}
			// String builder //

			if (target == null) {
				sender.sendMessage(ChatColor.RED
						+ "Sorry, player is offline, cannot warn.");
				return true;
			}

			if (!NotWarned.contains(targetName)) {
				NotWarned.add(targetName);
				WarnedOne.add(targetName);
				sender.sendMessage("§6Warned Player §c" + targetName
						+ " §6For " + sb.toString().replaceAll("&", "§"));
				sender.sendMessage("§6Player §c"
						+ targetName
						+ " §6Was Warned For The First Time, Warns From Here On Will Result In The Player Being Kicked, And Banned At Three Warns");
				target.sendMessage("§6You Have Been Warned For "
						+ sb.toString().replaceAll("&", "§"));
				target.sendMessage("§6This Was Your First Warn, Any Warns From Here On Will Result In A Kick And Finally A Ban");
				try {
					warnedProps.createNewFile();
					FileInputStream fis = new FileInputStream(warnedProps);
					FileOutputStream out = new FileOutputStream(warnedProps);
					Warnprops.load(fis);
					Warnprops.put(targetName, "0");
					Warnprops.store(out, "Warned Player | Warn Count");
					out.close();
					fis.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				return true;
			}
			if (WarnedOne.contains(targetName)) {
				WarnedOne.remove(targetName.toString());
				WarnedTwo.add(targetName.toString());
				sender.sendMessage("§6Warned Player §c" + targetName
						+ " §6For " + sb.toString().replaceAll("&", "§"));
				target.sendMessage("§6You Have Been Warned For §c"
						+ sb.toString().replaceAll("&", "§"));
				try {
					
					FileOutputStream out = new FileOutputStream(warnedProps);
					FileInputStream fis = new FileInputStream(warnedProps);
					Warnprops.load(fis);
					Warnprops.setProperty(targetName, "1");
					Warnprops.store(out, "Warned Player | Warn Count");
					sender.sendMessage("§6Player §c" + targetName
							+ "'s §6Warn Count Is Now §c"
							+ Warnprops.getProperty(targetName));
					target.sendMessage("§6Your Warn Count Is Now §c"
							+ Warnprops.getProperty(targetName));
					out.close();
					fis.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				return true;
			}
			if (WarnedTwo.contains(targetName)) {
				WarnedTwo.remove(targetName);
				WarnedThree.add(targetName);
				sender.sendMessage("§6Warned Player §c" + targetName
						+ " §6For " + sb.toString().replaceAll("&", "§"));
				target.sendMessage("§6You Have Been Warned For §c"
						+ sb.toString().replaceAll("&", "§"));
				try {
					FileInputStream fis = new FileInputStream(warnedProps);
					FileOutputStream out = new FileOutputStream(warnedProps);
					Warnprops.load(fis);
					Warnprops.setProperty(targetName, "2");
					Warnprops.store(out, "Warned Player | Warn Count");
					sender.sendMessage("§6Player §c" + targetName
							+ "'s §6Warn Count Is Now §c"
							+ Warnprops.getProperty(targetName)
							+ " §6And Has Been Kicked");
					target.kickPlayer("§6Your Warn Count Is Now §c"
							+ Warnprops.getProperty(targetName)
							+ " §6And Have Been Kicked");
					out.close();
					fis.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				return true;
			}

			if (WarnedThree.contains(targetName)) {
				WarnedThree.remove(targetName);
				NotWarned.remove(targetName);
				sender.sendMessage("§6Warned Player §c" + targetName
						+ " §6For " + sb.toString().replaceAll("&", "§"));
				target.sendMessage("§6You Have Been Warned For §c"
						+ sb.toString().replaceAll("&", "§"));
				try {
					bannedProps.createNewFile();
					FileInputStream fiss = new FileInputStream(bannedProps);
					FileOutputStream outt = new FileOutputStream(bannedProps);
					FileInputStream fis = new FileInputStream(warnedProps);
					FileOutputStream out = new FileOutputStream(warnedProps);
					Warnprops.load(fis);
					Warnprops.setProperty(targetName, "3");
					sender.sendMessage("§6Player §c" + targetName
							+ "'s §6Warn Count Is Now §c"
							+ Warnprops.getProperty(targetName)
							+ " §6And Has Been Kicked");
					target.kickPlayer("§6Your Warn Count Is Now §c"
							+ Warnprops.getProperty(targetName)
							+ " §6And Have Been Banned");
					Warnprops.store(out, "Warned Player | Warn Count");
					Warnprops.load(fiss);
					Warnprops.setProperty(targetName, "§6Your Warn Count Is Now §c3 §6And You Have Been Banned");
					Warnprops.store(outt, "Banned User | Reason");
					target.setBanned(true);
					outt.close();
					fiss.close();
					out.close();
					fis.close();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
				return true;
			}
			return true;
		}
		return true;
	}

	@Override
	public String getUsage() {
		return "§6/mh warn <player> <reason>";
	}

	@Override
	public String getPermission() {
		return "modhelp.commands.warn";
	}

}
