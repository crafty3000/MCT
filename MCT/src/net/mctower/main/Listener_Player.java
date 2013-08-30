package net.mctower.main;

import net.mctower.scoreboard.ScoreBoardControl;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class Listener_Player implements Listener{

	private Main plugin;

	public Listener_Player(Main p) {
		plugin = p;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		ScoreBoardControl.ScoreBoardHeadControl();
		//Main.PlayerNameChange(player);
		if(player.hasPermission("tower.staffjoinmessage")){
			event.setJoinMessage(ChatColor.DARK_AQUA+"Staff Member "+player.getName()+" is now in the tower.");
		}else{
			event.setJoinMessage(ChatColor.DARK_AQUA+player.getName()+" is now in the tower.");
		}
		if(player.hasPlayedBefore() == false){
			player.sendMessage("Welcome "+player.getName()+" to the tower. Enjoy your stay.");
		}else{
			player.sendMessage("welcome back "+player.getName()+". Enjoy your stay.");
		}
		return;
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event){
		Player player = event.getPlayer();
		if(player.hasPermission("tower.staffjoinmessage")){
			event.setQuitMessage(ChatColor.DARK_AQUA+"Staff member "+player.getName()+" left the tower.");
		}else{
			event.setQuitMessage(ChatColor.DARK_AQUA+player.getName()+" left the tower.");
		}
		return;
	}
	
	@EventHandler
	public void onPlayerOpenInv(InventoryOpenEvent event){
		if(event.getPlayer().hasPermission("tower.openinvtory")){
			return;
		}else{
			//Inventory.openInventory((Player)event.getPlayer());
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onPlayerItemDrop(PlayerDropItemEvent event){
		if(event.getPlayer().hasPermission("tower.dropitem")){
			return;
		}else{
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent event){
		if(event.toWeatherState() == true){
			event.getWorld().setWeatherDuration(0);
			event.setCancelled(true);
		}
	}
	
}
