package net.mctower.npc;


import net.minecraft.server.v1_6_R2.Entity;

import org.bukkit.Location;

public class NPC {

	private final Entity	entity;

	public NPC(final Entity entity) {
		this.entity = entity;
	}

	public org.bukkit.entity.Entity getBukkitEntity() {
		return entity.getBukkitEntity();
	}

	public Entity getEntity() {
		return entity;
	}

	public void moveTo(final Location l) {
		getBukkitEntity().teleport(l);
	}

	public void removeFromWorld() {
		try {
			entity.world.removeEntity(entity);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}