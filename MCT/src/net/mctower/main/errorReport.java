package net.mctower.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class errorReport {

	private static Main plugin;

	public errorReport(Main p) {
		plugin = p;
	}
	
	public static void reportError(Player player, String errorType) {
		player.sendMessage(getErrorType(errorType));
	}

	private static String getErrorType(String errorType) {
		String result = plugin.getConfig().getString("Error_Message."+errorType);
		if(result == null){
			result = (ChatColor.RED+"Error! Please report it to a member of staff.");
			debug.sendMessage("Error Missing Type: "+errorType);
		}
		return result;
	}

}
