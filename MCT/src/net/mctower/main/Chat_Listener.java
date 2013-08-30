package net.mctower.main;

import java.lang.reflect.Array;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

@SuppressWarnings("deprecation")
public class Chat_Listener implements Listener {

	private Main plugin;

	public Chat_Listener(Main p) {
		plugin = p;
	}
	
	String prefix_vip = (ChatColor.DARK_GRAY+"["+ChatColor.DARK_AQUA+"VIP"+ChatColor.DARK_GRAY+"] ");
	String prefix_staff = (ChatColor.DARK_GRAY+"["+ChatColor.DARK_RED+"STAFF"+ChatColor.DARK_GRAY+"] ");
	
	
	@EventHandler
	public void onPlayerChat(PlayerChatEvent event){
		formatChat(event.getPlayer(), filterMessage(event.getPlayer(), event.getMessage()));
		event.setMessage("");
		event.setCancelled(true);
		return;
	}
	
	private String filterMessage(Player player, String message) {
		String chat = "ERROR";
		String Filterd = "";
		if(player.hasPermission("mctower.staff")){
			Filterd = message;
		}else{
			chat = message.toLowerCase();
			int count = 0;
			for(String word : Main.WordsLib.values()){
				Filterd = chat.replaceAll(word, "****");
				count++;
			}
			if(count == 0){
				Filterd = message;
			}
			Filterd = Filterd.replaceAll("\\s?"+"(\\s|$)", " "+" ").trim();
		}
		return Filterd;
	}


	public void formatChat(Player player, String message){
		if(message == null){
			errorReport.reportError(player, "ChatReturnError");
		}else{
			Bukkit.broadcastMessage(""+formatChannel(player)+" "+formatPrefix(player)+formatPlayer(player)+formatSuffix(player)+formatMesfix(player)+message);
		}
	}
	
	public String formatPlayer(Player player){
		String result = (ChatColor.GRAY+player.getDisplayName());
		if(player.hasPermission("mctower.staff")||player.isOp()){
			result = (ChatColor.DARK_RED+player.getDisplayName());
		}else if(player.hasPermission("mctower.vip")){
			result = (ChatColor.DARK_AQUA+player.getDisplayName());
		}
		return result;
	}
	
	public String formatChannel(Player player){
		String result = (ChatColor.GRAY+"[G]:Lobby");
		return result;
	}
	
	public String formatPrefix(Player player){
		String result = ("");
		if(player.hasPermission("mctower.staff")||player.isOp()){
			result = (ChatColor.DARK_RED+prefix_staff);
		}else if(player.hasPermission("mctower.vip")){
			result = (ChatColor.DARK_AQUA+prefix_vip);
		}
		return result;
	}
	
	public String formatSuffix(Player player){
		String result = (ChatColor.DARK_GRAY+": ");
		return result;
	}
	
	public String formatMesfix(Player player){
		String result = (ChatColor.GRAY+"");
		if(player.hasPermission("mctower.staff")||player.isOp()){
			result = (ChatColor.WHITE+"");
		}else if(player.hasPermission("mctower.vip")){
			result = (ChatColor.WHITE+"");
		}
		return result;
	}
}
