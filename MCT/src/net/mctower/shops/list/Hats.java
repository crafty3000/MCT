package net.mctower.shops.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.mctower.interfaces.ShopInterface;
import net.mctower.main.Main;
import net.mctower.utilitys.Utility;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Hats {

	public static HashMap<String, Integer> HatCost = new HashMap<String, Integer>();
	
	public static ItemStack gethat(String SkullName, String ItemName, List<String> lore, int price) {
		lore.add(ChatColor.DARK_PURPLE+"Price: "+ChatColor.GRAY+"$"+price);
		ItemStack skullitem = setName(new ItemStack(Material.SKULL_ITEM), Utility.getColors(ItemName), lore);
		skullitem.setDurability((short)3);
		SkullMeta skullmeta = (SkullMeta)skullitem.getItemMeta();
		skullmeta.setOwner(SkullName);
		skullitem.setItemMeta(skullmeta);
		HatCost.put(lore.toString(),price);
		return skullitem;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getLore(String lore) {
		List<String> loredata = new ArrayList();
		loredata.add(""+Utility.getColors(lore));
		return loredata;
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
	
	@EventHandler
	public static void HatShopControl(InventoryClickEvent event, Player player) {
		if(event.getInventory().getName().startsWith(Main.ChatPrefix+ChatColor.LIGHT_PURPLE + "Hat Shop | McTower.net")){
			if(event.getCurrentItem().hasItemMeta() == false){
				event.setCancelled(true);
				return;
			}
			int price = HatCost.get(event.getCurrentItem().getItemMeta().getLore().toString());
			if(Utility.playerMoneyCheck(player, price)==true){
				Utility.playerMoneyTake(player, price);
				List<String> Lore = event.getCurrentItem().getItemMeta().getLore();
				Lore.add(Utility.getColors("&7Right Click &8| &7To wear this hat."));
				ItemStack Item = setName(new ItemStack(event.getCurrentItem().getType()), event.getCurrentItem().getItemMeta().getDisplayName(), Lore);
				Item.setDurability((short)3);
				SkullMeta skullmeta = (SkullMeta)Item.getItemMeta();
				skullmeta.setOwner(skullmeta.getOwner());
				Item.setItemMeta(skullmeta);
				Utility.givePlayerItem(player, Item);
				event.setCancelled(true);
				return;
			}else{
				player.sendMessage(Utility.getColors(Utility.cantAfford(Main.ChatPrefix+"&cHatShop&8: &7")));
				event.setCancelled(true);
				return;
			}
		}
	}
}
