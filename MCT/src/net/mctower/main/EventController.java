package net.mctower.main;

import net.mctower.interfaces.PlayerInterface;
import net.mctower.interfaces.ShopInterface;
import net.mctower.npc.NPCManager;
import net.mctower.npc.NpcEntityTargetEvent;
import net.mctower.npc.NpcEntityTargetEvent.NpcTargetReason;
import net.mctower.utilitys.Utility;
import net.minecraft.server.v1_6_R2.Packet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_6_R2.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class EventController implements Listener {
	
	private Main plugin;

	public EventController(Main p) {
		plugin = p;
	}
	
	@EventHandler
	 public void onEntityTarget(EntityTargetEvent event) {

	        if (event instanceof NpcEntityTargetEvent) {
	            NpcEntityTargetEvent nevent = (NpcEntityTargetEvent)event;
	            
	            String npc = NPCManager.getNPCIdFromEntity(event.getEntity());
	            

	           //HumanNpc npc = parent.HumanNPCList.getBasicHumanNpc(event.getEntity());
	            //HumanNPC npc = (HumanNPC) event.getEntity();
	            String name = (ChatColor.stripColor(npc.toString()));
	            Boolean IsPlayer = false;
	            for(Player p : Bukkit.getOnlinePlayers()){
	                if(p.getName().equalsIgnoreCase(name)){
	                	IsPlayer = true;
	                }
	            }
	            if(IsPlayer == true){
	            	 Player p = (Player) event.getTarget();
	                    ShopInterface.openShop(p, name);
	                    event.setCancelled(true);
	            }else if (event.getTarget() instanceof Player) {
	                if (nevent.getNpcReason() == NpcTargetReason.NPC_BOUNCED) {
	                    Player p = (Player) event.getTarget();
	                    p.sendMessage(ChatColor.BLUE+"Stop touching on me your so rude!");
	                    event.setCancelled(true);
	                }
	            }
	        }
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEntityEvent  event){
		Player player = event.getPlayer();
		org.bukkit.entity.Entity entity = event.getRightClicked();
		
		 Boolean IsPlayer = false;
         for(Player p : Bukkit.getOnlinePlayers()){
             if(p.getName().equalsIgnoreCase(((Player) entity).getName())){
             	IsPlayer = true;
             }
         }
         if(IsPlayer == true){
        	Player target = ((Player) entity).getPlayer();
 			PlayerInterface.Interface(player, target);
 			event.setCancelled(true);
         }else{
        	 if(entity instanceof HumanEntity){
        		 String npc = NPCManager.getNPCIdFromEntity(event.getRightClicked());
        		 String name = (ChatColor.stripColor(npc.toString()));
        		 ShopInterface.openShop(player, name);
        		 event.setCancelled(true); 
        	 }
         }
	}

	@EventHandler
    public void spawn(CreatureSpawnEvent event) {
        EntityType entity = event.getEntityType();
        if(entity == EntityType.BAT){
	        if ((entity.isAlive()) && (entity.isSpawnable())) {
	        	String[] splash = {
	        			"www.mctowner.net",
	        			"See Games Suite for a load of fun!",
	        			"You better keep your self looking good!",
	        			"See the shops in the mall!",
	        			"Read more books!",
	        			"Lots of places to relax and enjoy!",
	        			"Vist the VIP area for extra fun!",
	        			"Donate at mctower.net and get a jet pack!",
	        			"Click a player to interact with them!",
	        	};
	        	
	            event.getEntity().setCustomName(""+ChatColor.LIGHT_PURPLE +ChatColor.BOLD+ splash[Utility.RandomNumber(splash.length, 0)]);
	            event.getEntity().setCustomNameVisible(true);
	            return;
	            //org.bukkit.entity.Entity minecart = Bukkit.getServer().getWorlds().get(0).spawnEntity(event.getEntity().getLocation(), EntityType.BOAT);
	            //minecart.setPassenger(event.getEntity());
	        }
        }
        return;
    }
	
	public static void sendPacket(Player pl, Packet pa) {
        ((CraftPlayer)pl).getHandle().playerConnection.sendPacket(pa);
	}
	
	@EventHandler
	public static void BlockUpdate(BlockDamageEvent event){
		if(!event.getPlayer().isOp())event.setCancelled(true);
	}
	
	@EventHandler
	public static void BlockChange(BlockBreakEvent event){
		if(!event.getPlayer().isOp())event.setCancelled(true);
	}
	
	
	
	
	
	
	
	
	
	
	
}
