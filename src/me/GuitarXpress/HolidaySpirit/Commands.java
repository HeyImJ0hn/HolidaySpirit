package me.GuitarXpress.HolidaySpirit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
	Main plugin;

	public Commands(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {

		if (!(sender instanceof Player))
			return true;

		Player p = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("holidayspirit")) {
			p.sendMessage("§4H§fa§4p§fp§4y §fH§4o§fl§4i§fd§4a§fy§4s§f!");
			if (args.length == 1) {
				if (p.hasPermission("holiday.spawn")) {
					switch (args[0]) {
					case "bag":
						p.getInventory().addItem(ItemManager.goodieBag);
						break;
					case "hat":
						p.getInventory().addItem(ItemManager.santaHat);
						break;
					}
					return true;
				}
			}
		}

		return true;
	}

}
