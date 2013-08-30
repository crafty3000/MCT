package net.mctower.scoreboard;

import net.mctower.utilitys.Utility;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class ScoreBoardControl {

	
	
	public static void ScoreBoardHeadControl(){
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		
		Objective objective = board.registerNewObjective("Head", "dummy");
		objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
		
		for(Player onlinePlayer : Bukkit.getOnlinePlayers()){
			if(onlinePlayer.isOnline()){
			if(onlinePlayer.hasPermission("mctower.vip")){
				objective.setDisplayName(Utility.getColors("&8[&3VIP&8]"));
			}
			if(onlinePlayer.hasPermission("mctower.staff")){
				objective.setDisplayName(Utility.getColors("&8[&4STAFF&8]"));
			}
			onlinePlayer.setScoreboard(board);
		}
		}
	}
	
	
	
}
