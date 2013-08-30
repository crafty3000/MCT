package net.mctower.main;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.persistence.Entity;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_6_R2.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import net.mctower.NPCs.NPCHatShop;
import net.mctower.NPCs.NPCController;
import net.mctower.NPCs.NPCPetShop;
import net.mctower.filecontrol.FileControl;
import net.mctower.interfaces.PlayerInterface;
import net.mctower.interfaces.ShopInterface;
import net.mctower.npc.NPCManager;
import net.mctower.npc.HumanNPC;
import net.mctower.npc.NpcEntityTargetEvent;
import net.mctower.npc.NpcEntityTargetEvent.NpcTargetReason;
import net.mctower.utilitys.Utility;
import net.milkbowl.vault.economy.Economy;
import net.minecraft.server.v1_6_R2.EntityBat;
import net.minecraft.server.v1_6_R2.EntityPlayer;
import net.minecraft.server.v1_6_R2.Packet;
import net.minecraft.server.v1_6_R2.Packet20NamedEntitySpawn;


public class Main extends JavaPlugin implements Listener{
	FileControl filec = FileControl.getInstance();
	//LoadOnPlugin
	public static HashMap<String, String> WordsLib = new HashMap<String, String>();
	public static HashMap<String, String> ShopsNames = new HashMap<String, String>();
	
	public static HashSet<NPCController> NPClist=new HashSet<NPCController>();
	
	public static NPCManager NPCs;
	public static ProtocolManager protocolManager;
	public static Economy econ = null;
	
	public static String ChatPrefix = (""+ChatColor.DARK_AQUA+ChatColor.BOLD+"»");
	
	private Main plugin;
	
	public void onLoad() {
        protocolManager=ProtocolLibrary.getProtocolManager();
    }
	
	public void onEnable(){
		saveDefaultConfig();
		Utility.BootScreen();
		NPCLoader();
		filec.FileControlsetup(this);
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		pm.registerEvents(new Listener_Player(this), this);
		pm.registerEvents(new Chat_Listener(this), this);
		pm.registerEvents(new NPCController(this), this);
		pm.registerEvents(new ShopInterface(this), this);
		pm.registerEvents(new PlayerInterface(this), this);
		pm.registerEvents(new EventController(this), this);
		ShopInterface.runShopLoader();
		PlayerInterface.runPlayerInterfaceLoader();
		setupEconomy();
		
		//Load Last Data - (May load wrong.)
		Utility.loadConfigChatFilter();
	}
	
	private void NPCLoader() {
		NPCs=new NPCManager(this);
		NPClist.add(new NPCHatShop(new Location(Bukkit.getWorlds().get(0), -235.5, 78, 296.5), -90, -5));
		NPClist.add(new NPCPetShop(new Location(Bukkit.getWorlds().get(0), -183.5, 78, 296.5), 90, -5));
	}
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
	}

	public void OnDisable(){
		
	}
	
	@EventHandler
	public void onCommand(org.bukkit.event.player.PlayerCommandPreprocessEvent event) {
			Player player = event.getPlayer();
			//event.setCancelled(true);
			String[] args=event.getMessage().split(" ");
			////////////////////////////////////////////////////////////////////////////////////////////////////////
			if(args[0].equalsIgnoreCase("/tower")){
				player.sendMessage("Hi "+player.getName());
				event.setCancelled(true);
			}else if(args[0].equalsIgnoreCase("/eject")){
				PlayerInterface.PlayerEjectCommand(event.getPlayer());
				event.setCancelled(true);
			////////////////////////////////////////////////////////////////////////////////////////////////////////
			}else if(args[0].equalsIgnoreCase("/lobby")){
				player.sendMessage(ChatColor.DARK_AQUA+"Warping to Lobby.");
				player.sendBlockChange(player.getLocation(), Material.PORTAL, (byte)1);
				player.sendBlockChange(player.getLocation().add(0.0, 1.0, 0.0), Material.PORTAL, (byte)1);
				player.setVelocity(new Vector(0,8,0));
				Location location = player.getLocation();
				location.setWorld(Bukkit.getServer().getWorld(getConfig().getString("")));
				location.setX(getConfig().getDouble(""));
				location.setY(getConfig().getDouble(""));
				location.setZ(getConfig().getDouble(""));
				location.setPitch(getConfig().getInt(""));
				location.setYaw(getConfig().getInt(""));
				//player.teleport(location);
				event.setCancelled(true);
				return;
			////////////////////////////////////////////////////////////////////////////////////////////////////////
			}else if(args[0].equalsIgnoreCase("/version")){
				Utility.getServerVersion(player);
				event.setCancelled(true);
				return;
			}else{
				if(!player.hasPermission("mctower.staff")){
					player.sendMessage(ChatColor.AQUA+"Command: "+args[0]+" Not Found! see /help");
					event.setCancelled(true);
					return;
				}
			}
	}

	public static String formatName(String username) {
		return formatName(username, true);
	}
	public static String formatName(String username, boolean withName) {
		if (username.equalsIgnoreCase("mct")) return (withName?ChatColor.DARK_AQUA+"MCTower: ":"")+ChatColor.RED;
		if (username.startsWith("__NPC__")) return (withName?ChatColor.GREEN+"["+ChatColor.DARK_GREEN+"NPC"+ChatColor.GREEN+"] "+username.substring(7)+": ":"")+ChatColor.LIGHT_PURPLE;
		return (withName?ChatColor.GRAY+username+": ":"")+ChatColor.WHITE;
	}
	public static String formatNPCName(String username) {
		return ""+ChatColor.YELLOW+ChatColor.BOLD+username.substring(7);
	}

	final static public String NPCChars[]={
		"__NPC__Hat Shop",
		"__NPC__Pet Shop"
	};

	/*@EventHandler
	public void onNameTag(PlayerReceiveNameTagEvent event) {
		Player player = event.getPlayer();
		String Prefix = "";
		String Color = "";
		if(event.getPlayer().hasPermission("mctower.staff")){
			Prefix = ("&8[&4STAFF&8] ");
			Color = ("&c");
		}
		if(event.getPlayer().hasPermission("mctower.vip")){
			Prefix = ("&8[&3VIP&8] ");
			Color = ("&3");
		}
		event.setTag(Utility.getColors(Prefix+Color+player.getName()));
	}*/

	/*public static void PlayerNameChange(Player player){
		EntityPlayer PlayerName = ((CraftPlayer)player).getHandle();
		String Prefix = "";
		String Color = "";
		if(player.hasPermission("mctower.vip")){
			Prefix = ("&8[&3VIP&8] ");
			Color = ("&3");
		}
		if(player.hasPermission("mctower.staff")){
			Prefix = ("&8[&4STAFF&8] ");
			Color = ("&c");
		}
		
		PlayerName.displayName = (Utility.getColors(Prefix+Color+player.getName()));
		
		for(Player onlinePlayer : Bukkit.getOnlinePlayers()){
			if(onlinePlayer != player){
				((CraftPlayer)onlinePlayer).getHandle().playerConnection.sendPacket(new Packet20NamedEntitySpawn(PlayerName));
				System.out.println("Player Name Change.");
			}
		}
	}*/

















}
