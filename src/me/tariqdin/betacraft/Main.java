package me.tariqdin.betacraft;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	private HashMap<Material, Integer> foodHealth;
	@Override
	public void onEnable() {
		foodHealth = new HashMap<Material, Integer>();
		foodHealth.put(Material.APPLE, 4);
		foodHealth.put(Material.BREAD, 3);
		foodHealth.put(Material.RAW_CHICKEN, 2);
		foodHealth.put(Material.COOKED_CHICKEN, 6);
		foodHealth.put(Material.GOLDEN_APPLE, 10);
		foodHealth.put(Material.COOKED_BEEF, 8);
		foodHealth.put(Material.RAW_BEEF, 3);
		foodHealth.put(Material.GRILLED_PORK, 8);
		foodHealth.put(Material.PORK, 3);
		foodHealth.put(Material.COOKED_FISH, 5);
		foodHealth.put(Material.RAW_FISH, 2);
		foodHealth.put(Material.COOKIE, 1);
		foodHealth.put(Material.BAKED_POTATO, 5);
		foodHealth.put(Material.POTATO_ITEM, 1);
		foodHealth.put(Material.POISONOUS_POTATO, 1);
		foodHealth.put(Material.GOLDEN_CARROT, 6);
		foodHealth.put(Material.CARROT_ITEM, 4);
		foodHealth.put(Material.MELON, 2);
		foodHealth.put(Material.MUSHROOM_SOUP, 6);
		foodHealth.put(Material.PUMPKIN_PIE, 8);
		foodHealth.put(Material.ROTTEN_FLESH, 1);
		foodHealth.put(Material.SPIDER_EYE, 1);
		foodHealth.put(Material.CAKE_BLOCK, 2);
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e){
		resetFood(e.getPlayer());
	}
	 
	public void onRespawn(final PlayerRespawnEvent e){
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			
			@Override
			public void run() {
				resetFood(e.getPlayer());
			}
		}, 10L);
	}
	
	private void resetFood(Player player) {
		player.setFoodLevel(6);
	}

	@EventHandler
	public void onNom(PlayerItemConsumeEvent e){
		e.setCancelled(true);
		Player player = e.getPlayer();
		double playerHealth = player.getHealth();
		playerHealth += foodHealth.get(e.getItem().getType());
		if(playerHealth > 20){
			playerHealth = 20;
		}
		player.setHealth(playerHealth);
		player.setItemInHand(new ItemStack(Material.AIR, 1));
	}
	
	@EventHandler
	public void onHungerChange(FoodLevelChangeEvent e){
		if(e.getEntityType() == EntityType.PLAYER){
			Player player = (Player) e.getEntity();
			if(e.getFoodLevel() != 6){
				resetFood(player);
			}
		}
	}
	
	@EventHandler
	public void nommyNommyCake(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(e.getClickedBlock().getType() == Material.CAKE_BLOCK){
				Player player = e.getPlayer();
				double playerHealth = player.getHealth();
				playerHealth += foodHealth.get(Material.CAKE_BLOCK);
				if(playerHealth > 20){
					playerHealth = 20;
				}
				player.setHealth(playerHealth);
			}
		}
		
	}
}
