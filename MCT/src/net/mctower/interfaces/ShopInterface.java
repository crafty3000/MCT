package net.mctower.interfaces;

import java.util.ArrayList;
import java.util.List;

import net.mctower.main.Main;
import net.mctower.shops.list.Hats;
import net.mctower.shops.list.Hats_List;
import net.mctower.utilitys.Utility;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class ShopInterface implements Listener{
	
	private Main plugin;

	public ShopInterface(Main p) {
		plugin = p;
	}
	
	public static Inventory HatShop;
	
	private static ItemStack setName(ItemStack is, String name, List<String> Lore){
        ItemMeta im = is.getItemMeta();
        if (name != null)
                im.setDisplayName(name);
        if (Lore != null)
                im.setLore(Lore);
        is.setItemMeta (im);
        return is;
	}
	
	public static void openShop(Player player, String NPCname) {
		if(NPCname.equalsIgnoreCase("Hat Shop")){
			HatShopInterface(player, NPCname);
		}else if(player.isOp() || player.hasPermission("mctower.staff")){
			player.sendMessage(ChatColor.RED+"NPC is missing GUI Interface.");
		}
	}
		
	public static void runShopLoader(){
		HatShop = Bukkit.createInventory(null, 54, Main.ChatPrefix+ChatColor.LIGHT_PURPLE + "Hat Shop | McTower.net");
		runShopItemLoader();
	}
	
	public static void HatShopInterface(Player player, String NPCname){
		player.openInventory(HatShop);
	}
	
	public static void runShopItemLoader(){
		Hats_List.load();
	}
	
	@EventHandler
	public void ShopControl(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		/*if(event.getCurrentItem() == null){
			event.setCancelled(true);
			return;
		}*/
		if (event.getInventory().getTitle().startsWith(Main.ChatPrefix)){
			if (event.getClick().isKeyboardClick()){
				event.setCancelled(true);
			}
			if (event.isShiftClick()) {
				event.setCancelled(true);
				return;
			}
			if(event.getHotbarButton() >= 0){
				event.setCancelled(true);
				return;
			}
			if (event.isRightClick() || event.isLeftClick()) {
				if (!event.isShiftClick()) {
					if (event.getRawSlot() < 54) {
						String ShopName = Utility.StripColors(event.getInventory().getTitle());
						if(ShopName.contains("Hat Shop")){
							Hats.HatShopControl(event, player);
							//event.setCancelled(true);
							//return;
						}
					}else{
						event.setCancelled(true);
						return;
					}
				}
			}
		}
	}
}
