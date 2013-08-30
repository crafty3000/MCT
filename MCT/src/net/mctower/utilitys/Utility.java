package net.mctower.utilitys;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.mctower.filecontrol.FileControl;
import net.mctower.interfaces.ShopInterface;
import net.mctower.main.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Utility{
	
	private static Main plugin;

	public Utility(Main p) {
		plugin = p;
	}
	
    public static String getColors(String input) {
        String result = "";
        String Message = input;
        result = Message.replaceAll("(&([a-f0-9l-l]))", "\u00A7$2");
        return result;
    }
    
    public static String StripColors(String input) {
        String result = "";
        String Message = input;
        result = Message.replaceAll("(&([a-f0-9l-l]))", "");
        return result;
    }

    public static int RandomNumber(int High, int Low){
    	Random random = new Random();
    	return random.nextInt(High - Low);
    }
    
	public static String cantAfford(String Prefix){
		String[] message = {
    			"You don't the money in your pocket to afford that.",
    			"The money you have does not cover the cost of this purchase.",
    			"Money is only accepted here, You better get somemore.",
    			"You need more money to pay for that.",
    	};
		return Prefix+message[RandomNumber(3,0)];
	}
	
	public static boolean playerMoneyCheck(Player player, double cost) {
		if(Main.econ.has(player.getName(), cost)){
			return true;
		}
		return false;
	}
	
	public static void playerMoneyTake(Player player, double cost) {
		Main.econ.withdrawPlayer(player.getName(), cost);
		player.sendMessage(Main.ChatPrefix+ChatColor.DARK_GREEN+"Bank: "+"$"+cost+" has been taken out of your bank.");
	}

	public static void givePlayerItem(Player player, ItemStack Item) {
		player.getInventory().addItem(Item);
		player.updateInventory();
		player.sendMessage(Main.ChatPrefix+ChatColor.DARK_GREEN+"Inventory: Item has been added to your inventory.");
	}
	
	public static void getServerVersion(Player player) {
		player.sendMessage(
				ChatColor.DARK_AQUA+" Server Running:"+ChatColor.GOLD+" MCTower \n"+
				ChatColor.DARK_AQUA+"Build Version:"+ChatColor.GOLD+"     1.0.0 \n"+
				ChatColor.DARK_AQUA+"Implementing:"+ChatColor.GOLD+"      MCT ModAPI \n"+
				ChatColor.DARK_AQUA+"Connection:"+ChatColor.GOLD+"        1.6.2 \n"+
				ChatColor.DARK_AQUA+"Minecraft:"+ChatColor.GOLD+"          1.6.2"
				);
		return;
	}

	public static void BootScreen() {
		Runtime runtime = Runtime.getRuntime();

	    NumberFormat format = NumberFormat.getInstance();
	    long maxMemory = runtime.maxMemory();
	    long allocatedMemory = runtime.totalMemory();
	    long freeMemory = runtime.freeMemory();
		System.out.println("-----------------------------------------------------");
		System.out.println("-                                                   -");
		System.out.println("-                                                   -");
		System.out.println(ChatColor.RED+"-                 MC TOWER BOOTING                  -");
		System.out.println("-                                                   -");
		System.out.println("-                                                   -");
		System.out.println("-----------------------------------------------------");
	}
	
	public static void loadConfigChatFilter(){
		List<String> ChatConfig = (List<String>)FileControl.getConfigFile(FileControl.ChatFilterYaml).getList("ChatFilter");
		int ChatCount = 0;
		for(String command : ChatConfig){
			Main.WordsLib.put(command, command);
			ChatCount++;
		}
		System.out.println("[MCT ChatFilter] "+ChatCount+" filtered words added to catch.");
		//allowedcommand.add(args[0]);
		//this.getConfig(),set("List", allowedcommand);
	}
	
	
	
}