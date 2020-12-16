package me.GuitarXpress.HolidaySpirit;

import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	@SuppressWarnings("unchecked")
	@Override
	public void onEnable() {
		saveDefaultConfig();
		saveConfig();
		getServer().getPluginManager().registerEvents(new Events(this), this);
		getCommand("holidayspirit").setExecutor(new Commands(this));

		Events.possibleCommonLoot = (List<ItemStack>) getConfig().get("data.commonitems");
		Events.possibleRareLoot = (List<ItemStack>) getConfig().get("data.rareitems");

		Events.commonDropAmount = getConfig().getInt("dropchances.bag.commondropamount");
		Events.rareDropAmount = getConfig().getInt("dropchances.bag.raredropamount");
		Events.dropChance = getConfig().getDouble("dropchances.bag.droprate");
		Events.rareChance = getConfig().getDouble("dropchances.bag.rarelootchance");
		Events.santaDropChance = getConfig().getDouble("dropchances.santahat");
		Events.santaSpawnChance = getConfig().getDouble("spawnchances.santahat");

		saveDefaultConfig();
		saveConfig();
		ItemManager.init();
		System.out.println("§7[§4H§fo§4l§fi§4d§fa§4y§fS§4p§fi§4r§fi§4t§7] §eEnabled!");
	}

	@Override
	public void onDisable() {
		System.out.println("§7[§4H§fo§4l§fi§4d§fa§4y§fS§4p§fi§4r§fi§4t§7] §eDisabled!");
	}

}
