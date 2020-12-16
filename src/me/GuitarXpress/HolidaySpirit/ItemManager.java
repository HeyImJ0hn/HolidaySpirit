package me.GuitarXpress.HolidaySpirit;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.codec.binary.Base64;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class ItemManager {

	public static ItemStack goodieBag;
	public static ItemStack santaHat;

	public static void init() {
		createGoodieBag();
		createSantaHat();
	}

	private static void createSantaHat() {
		ItemStack item = new ItemStack(Material.CARVED_PUMPKIN, 1);
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setDisplayName("§4S§fa§4n§ft§4a §fH§4a§ft");
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§7Santa's Hat!");
		lore.add(" ");
		lore.add("§7Make sure to use our resourcepack and optifine");
		lore.add("§7in order to see the custom texture.");
		meta.setLore(lore);
		item.setItemMeta(meta);
		santaHat = item;
	}

	private static void createGoodieBag() {
		ItemStack item = getSkull(
				"http://textures.minecraft.net/texture/6cef9aa14e884773eac134a4ee8972063f466de678363cf7b1a21a85b7");
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§7Happy Holidays!");
		meta.setLore(lore);
		meta.setDisplayName("§6Goodie Bag");
		item.setItemMeta(meta);
		goodieBag = item;
	}
	
	private static ItemStack getSkull(String url) {
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
		if (url == null || url.isEmpty())
			return skull;
		SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
		profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
		Field profileField = null;
		try {
			profileField = skullMeta.getClass().getDeclaredField("profile");
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		profileField.setAccessible(true);
		try {
			profileField.set(skullMeta, profile);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		skull.setItemMeta(skullMeta);
		return skull;
	}

}
