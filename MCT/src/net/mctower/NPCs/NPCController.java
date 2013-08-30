package net.mctower.NPCs;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.comphenix.protocol.Packets;
import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.injector.GamePhase;
import net.mctower.main.Main;

import net.mctower.npc.HumanNPC;
import net.mctower.npc.NPC;

public class NPCController implements Listener {
	public NPC npc;
	public int NPCid;
	public boolean HeadUpdates=true;
	public static int MaxID=500;
	public static HashSet<Integer> NoUpdates=new HashSet<Integer>();
	
	public NPCController() {}	
	public NPCController(Main plug) {
		 Main.protocolManager.addPacketListener(new PacketAdapter(plug, ConnectionSide.SERVER_SIDE, GamePhase.PLAYING, new Integer[]{Packets.Server.ENTITY_TELEPORT}) {
				@Override
				public void onPacketSending(PacketEvent event) {
					if (event.getPacketID()==Packets.Server.ENTITY_TELEPORT) { //NPC?
						for (int i:NoUpdates) if (i==event.getPacket().getIntegers().read(0)) event.setCancelled(true);
					}
				}
		 });
		Bukkit.getServer().getScheduler().runTaskTimer(plug, new Runnable() {
			@Override
			public void run() {
				for (NPCController b:Main.NPClist) {
					if (b.npc!=null&&HeadUpdates) {
						((HumanNPC)b.npc).updateHead();
					}
				}
			}
		}, 20, 200);
	}
	
	@EventHandler
	public void OnAttack(org.bukkit.event.entity.EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof HumanEntity) {
            NPC a=Main.NPCs.getNPCFromEntity(event.getEntity());

            if (a!=null) {
            	for (NPCController b:Main.NPClist) {
            		if (b.NPCid==a.getBukkitEntity().getEntityId()) {
            			b.manageAttack(event);
            		}
            	}
            }
        }
	}

	public void manageAttack(EntityDamageByEntityEvent event) {
	}
}
