package me.GuitarXpress.HolidaySpirit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

public class Events implements Listener {
	Main plugin;

//	List<Material> possibleCommonLoot = new ArrayList<Material>(
//			Arrays.asList(Material.IRON_INGOT, Material.GOLD_INGOT, Material.SPECTRAL_ARROW, Material.LEATHER,
//					Material.EXPERIENCE_BOTTLE, Material.COOKED_BEEF, Material.NAUTILUS_SHELL));
//	List<Material> possibleRareLoot = new ArrayList<Material>(
//			Arrays.asList(Material.DIAMOND, Material.HEART_OF_THE_SEA, Material.EMERALD, Material.ENDER_PEARL,
//					Material.GOLDEN_APPLE, Material.NAME_TAG, Material.SADDLE));

	static List<ItemStack> possibleCommonLoot = new ArrayList<ItemStack>();
	static List<ItemStack> possibleRareLoot = new ArrayList<ItemStack>();

	public static int commonDropAmount;
	public static int rareDropAmount;
	public static double rareChance;
	public static double dropChance;
	public static double santaDropChance;
	public static double santaSpawnChance;

	public Events(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onMobSpawn(EntitySpawnEvent event) {
		if (event.getEntity() instanceof Zombie || event.getEntity() instanceof Skeleton) {
			LivingEntity e = (LivingEntity) event.getEntity();
			if (Math.random() < santaSpawnChance) {
				e.getEquipment().setHelmet(ItemManager.santaHat);
				e.getEquipment().setHelmetDropChance(0f);
			}
		}
	}

	@EventHandler
	public void onMobDeath(EntityDeathEvent event) {
		if (event.getEntity() instanceof Monster) {
			if (Math.random() < dropChance) {
				Location loc = event.getEntity().getLocation();
				loc.getWorld().dropItemNaturally(loc, ItemManager.goodieBag);
				event.getEntity().getKiller().sendMessage("§eThe enemy you killed dropped a §6Goodie Bag§e!");
			}
			if (event.getEntity().getEquipment() != null) {
				if (event.getEntity().getEquipment().getHelmet() != null) {
					if (event.getEntity().getEquipment().getHelmet().hasItemMeta()) {
						if (event.getEntity().getEquipment().getHelmet().getItemMeta()
								.equals(ItemManager.santaHat.getItemMeta())) {
							if (Math.random() < santaDropChance) {
								Location loc = event.getEntity().getLocation();
								loc.getWorld().dropItemNaturally(loc, ItemManager.santaHat);
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onEntityExplosion(ExplosionPrimeEvent event) {
		if (event.getEntity() instanceof Creeper) {
			spawnFireworks(event.getEntity().getLocation());
		}
	}

	@EventHandler
	public void onRightClick(PlayerInteractEvent event) {
		Player p = (Player) event.getPlayer();

		if (event.getItem() == null)
			return;

		if (!event.getItem().hasItemMeta())
			return;

		if (!event.getItem().getItemMeta().hasLore())
			return;

		if (event.getClickedBlock() != null)
			if (event.getClickedBlock().getType().isInteractable())
				return;

		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getItem().getItemMeta().getLore().get(0).equals("§7Happy Holidays!")) {
				openGoodieBag(p);
				int amount = event.getItem().getAmount();
				event.getItem().setAmount(amount - 1);
			}
		}

	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (event.getItemInHand() == null)
			return;

		if (!event.getItemInHand().hasItemMeta())
			return;

		if (!event.getItemInHand().getItemMeta().hasLore())
			return;

		if (event.getItemInHand().getItemMeta().getLore().get(0).equals("§7Happy Holidays!"))
			event.setCancelled(true);

		if (event.getItemInHand().getItemMeta().getLore().get(0).equals("§7Santa's Hat!"))
			event.setCancelled(true);
	}

	private void openGoodieBag(Player p) {
		Random rndCommon = new Random();
		Random rndRare = new Random();
		for (int i = 0; i < commonDropAmount; i++) {
			int index = rndCommon.nextInt(possibleCommonLoot.size());
			ItemStack item = possibleCommonLoot.get(index);
			p.getInventory().addItem(new ItemStack(item));
		}

		if (Math.random() < rareChance) {
			for (int i = 0; i < rareDropAmount; i++) {
				int index = rndRare.nextInt(possibleRareLoot.size());
				ItemStack item = possibleRareLoot.get(index);
				p.getInventory().addItem(new ItemStack(item));
			}
		}

		if (Math.random() < 0.031) { // Normal Minecraft Chance
			p.getInventory().addItem(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
		}
		p.sendMessage("§4H§fa§4p§fp§4y §fH§4o§fl§4i§fd§4a§fy§4s§f!");
	}

	private void spawnFireworks(Location loc) {
		Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
		FireworkMeta fwm = fw.getFireworkMeta();

		fwm.setPower(1);
		fwm.addEffect(FireworkEffect.builder().withColor(Color.WHITE).withFade(Color.RED).with(Type.CREEPER).flicker(true).build());

		fw.setFireworkMeta(fwm);
		fw.detonate();
	}

}
