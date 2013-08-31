package net.mctower.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.mctower.main.Main;
import net.mctower.utilitys.Utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class PlayerInterface implements Listener{
	
	private Main plugin;

	public PlayerInterface(Main p) {
		plugin = p;
	}
	
	public static Inventory PlayerInterface;
	public static Inventory Emotions;
	public static HashMap<Player, Player> ActivePlayerIteraction = new HashMap<Player, Player>();
	//sssssssssssssssssssssssssssssssssss
	
	public static void Interface(Player player, Player target) {
		ActivePlayerIteraction.put(player, target);
		player.openInventory(PlayerInterface);
	}
	
	public static void runPlayerInterfaceLoader(){
		PlayerInterface = Bukkit.createInventory(null, 9, Main.ChatPrefix+ChatColor.LIGHT_PURPLE + "Interaction | McTower.net");
		Emotions = Bukkit.createInventory(null, 9, Main.ChatPrefix+ChatColor.LIGHT_PURPLE + "Emotions | McTower.net");
		runPlayerInterfaceItems();
	}

	private static void runPlayerInterfaceItems() {
		PlayerInterface.addItem(ItemCreator(Material.EMERALD,        "&6Trade",         "&7Right Click &8| &7Trade items with other players."));
		PlayerInterface.addItem(ItemCreator(Material.JUKEBOX,        "&4Buddy Tower",   "&7Right Click &8| &7Buddy Tower lets to select your buddys."));
		PlayerInterface.addItem(ItemCreator(Material.CAKE,           "&3Emotions",      "&7Right Click &8| &7Emotions for players to see."));
		PlayerInterface.addItem(ItemCreator(Material.SADDLE,         "&5Saddle",        "&7Right Click &8| &7Saddle up a player and ride them."));
		PlayerInterface.addItem(ItemCreator(Material.IRON_SWORD,     "&2Dual",          "&7Right Click &8| &7Dual them in a quick pvp battle."));
		PlayerInterface.addItem(ItemCreator(Material.BOOK,           "&eExit",          "&7Right Click &8| &7Exit this menu."));
		
		Emotions.addItem(ItemCreator(Material.EMERALD, "&dLove Hearts", "&7Right Click!"));
	}
	
	@SuppressWarnings("unchecked")
	public static ItemStack ItemCreator(Material Mat, String Name, String Lore){
		ItemStack Item = setName(new ItemStack(Mat),Utility.getColors(Name),getLore(Lore));
		return Item;
	}
	
	private static ItemStack setName(ItemStack is, String name, List<String> lore){
        ItemMeta im = is.getItemMeta();
        if (name != null)
                im.setDisplayName(name);
        if (lore != null)
                im.setLore(lore);
        is.setItemMeta (im);
        return is;
	}
	
	public static List getLore(String lore) {
		List<String> loredata = new ArrayList();
		loredata.add(""+Utility.getColors(lore));
		return loredata;
	}
	
	@EventHandler
	public void playerInteractionMenu(InventoryClickEvent event){
		Player player = (Player)event.getWhoClicked();
		if(event.getInventory().getTitle().startsWith(Main.ChatPrefix+ChatColor.LIGHT_PURPLE + "Interaction | McTower.net")){
			if(event.getCurrentItem().getType() == Material.SADDLE){
				Player Target = ActivePlayerIteraction.get(event.getWhoClicked());
				Target.setPassenger(event.getWhoClicked());
				Target.sendMessage(Utility.getColors(Main.ChatPrefix+"&2Interaction&8: &7Player "+event.getWhoClicked().getName()+" is riding you, Use &c/eject &2to remove them."));
				((Player)event.getWhoClicked()).sendMessage(Utility.getColors(Main.ChatPrefix+"&2Interaction&8: &7You are now riding "+Target.getName()+"&2, Press &fSHIFT &2to jump off."));
				event.setCancelled(true);
				return;
	 		}
			if(event.getCurrentItem().getType() == Material.BOOK){
				event.getWhoClicked().closeInventory();
				return;
			}
			
			
			if(event.getCurrentItem().getType() == Material.CAKE){
				event.getWhoClicked().closeInventory();
				event.getWhoClicked().openInventory(Emotions);
				event.setCancelled(true);
				return;
			}
			event.setCancelled(true);
			return;
		}
		if(event.getInventory().getTitle().startsWith(Main.ChatPrefix+ChatColor.LIGHT_PURPLE + "Emotions | McTower.net")){
			if(event.getCurrentItem().getType() == Material.EMERALD){
				Bukkit.getServer().getWorlds().get(0).playEffect(player.getLocation(),Effect.MOBSPAWNER_FLAMES, 12);
				event.setCancelled(true);
				return;
			}
		}
		if(event.getCurrentItem().getType() == Material.SKULL_ITEM && event.getInventory().getType() == InventoryType.PLAYER){
			if(event.getSlotType() == SlotType.ARMOR){
				if(event.getCurrentItem() != null){
					player.getInventory().addItem(event.getCurrentItem());
					player.getInventory().setHelmet(null);
					player.updateInventory();
					player.sendMessage(Utility.getColors(Main.ChatPrefix+"&2Inventory&8: &7You are no longer wearing your hat!"));
					event.setCancelled(true);
					return;
				}else{
					event.setCancelled(true);
					return;
				}
			}else if(event.getSlotType() == SlotType.CONTAINER || event.getSlotType() == SlotType.QUICKBAR){
				if(player.getInventory().getHelmet() == null){
					player.getInventory().setHelmet(event.getCurrentItem());
					player.getInventory().removeItem(event.getCurrentItem());
					player.updateInventory();
					player.sendMessage(Utility.getColors(Main.ChatPrefix+"&2Inventory&8: &7You are now wearing your hat! Use F5 Key to see a better view of it."));
					event.setCancelled(true);
					return;
				}else{
					player.sendMessage("ERROR YOU HAVE A HAT ON!");
					event.setCancelled(true);
					player.updateInventory();
					return;
				}
			}else{
				event.setCancelled(true);
				return;
			}
		}else{
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void playerCloseInteractionMenu(InventoryCloseEvent event){
		if(event.getInventory().getTitle().startsWith(Main.ChatPrefix+ChatColor.LIGHT_PURPLE + "Interaction | McTower.net")){
			ActivePlayerIteraction.remove(event.getPlayer());
		}
	}
	
	public static void PlayerEjectCommand(Player player) {
		if(player.getPassenger() != null){
			Player Passenger = (Player)player.getPassenger();
			Passenger.getVehicle().eject();
			player.sendMessage(Utility.getColors(Main.ChatPrefix+"&2Interaction&8: &7You have kicked "+Passenger.getName()+" &2off of you!"));
		}else{
			player.sendMessage(Utility.getColors(Main.ChatPrefix+"&cError&8: &7You Currently don't have someone riding you!"));
		}
		return;
	}
	
	
}
