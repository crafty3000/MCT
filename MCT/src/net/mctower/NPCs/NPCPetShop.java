package net.mctower.NPCs;

import org.bukkit.Location;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import net.mctower.main.Main;

public class NPCPetShop extends NPCController {
	public NPCPetShop(Location location, float yaw, float pitch) {
		npc=Main.NPCs.spawnHumanNPC(Main.formatNPCName(Main.NPCChars[1]), location);
		npc.getEntity().yaw=yaw;
		npc.getEntity().pitch=pitch;
		NPCid=npc.getBukkitEntity().getEntityId();
	}
	@Override
	public void manageAttack(EntityDamageByEntityEvent event) {
		event.setCancelled(true);
	}
}
