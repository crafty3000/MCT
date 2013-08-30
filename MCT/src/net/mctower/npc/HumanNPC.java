package net.mctower.npc;

import net.mctower.npc.NPCUtils;
import net.mctower.npc.NPCEntity;
import net.minecraft.server.v1_6_R2.EntityPlayer;
import net.minecraft.server.v1_6_R2.Packet18ArmAnimation;
import net.minecraft.server.v1_6_R2.Packet35EntityHeadRotation;
import net.minecraft.server.v1_6_R2.Packet38EntityStatus;
import net.minecraft.server.v1_6_R2.Packet5EntityEquipment;
import net.minecraft.server.v1_6_R2.WorldServer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class HumanNPC extends NPC {

	private final net.minecraft.server.v1_6_R2.ItemStack[]	previousEquipment	= { null, null, null, null, null };
	
	public HumanNPC(final NPCEntity npcEntity) {
		super(npcEntity);
	}

	public void actAsHurt() {
		((WorldServer) getEntity().world).tracker.a(getEntity(), new Packet38EntityStatus(getEntity().id, (byte)2));
	}

	public void animateArmSwing() {
		((WorldServer) getEntity().world).tracker.a(getEntity(), new Packet18ArmAnimation(getEntity(), 1));
	}
	public void updateHead() {
		((WorldServer)getEntity().world).tracker.a(getEntity(), new Packet35EntityHeadRotation(getEntity().id, (byte)(Math.round(getEntity().yaw/360*256))));
	}
	public void updateHead(float Yaw) {
		((WorldServer)getEntity().world).tracker.a(getEntity(), new Packet35EntityHeadRotation(getEntity().id, (byte)(Math.round(Yaw/360*256))));
	}

	public PlayerInventory getInventory() {
		return ((HumanEntity) getEntity().getBukkitEntity()).getInventory();
	}

	public String getName() {
		//return ((NPCEntity) getEntity()).name;//Changed due to 1.6 update.
		return ((NPCEntity) getEntity()).getName();
	}

	public void getOutOfBed() {
		((NPCEntity) getEntity()).a(true, true, true);
	}

	public void lookAtPoint(final Location point) {
		if (getEntity().getBukkitEntity().getWorld() != point.getWorld()) {
			return;
		}
		final Location npcLoc = ((LivingEntity) getEntity().getBukkitEntity()).getEyeLocation();
		final double xDiff = point.getX() - npcLoc.getX();
		final double yDiff = point.getY() - npcLoc.getY();
		final double zDiff = point.getZ() - npcLoc.getZ();
		final double DistanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
		final double DistanceY = Math.sqrt(DistanceXZ * DistanceXZ + yDiff * yDiff);
		double newYaw = Math.acos(xDiff / DistanceXZ) * 180 / Math.PI;
		final double newPitch = Math.acos(yDiff / DistanceY) * 180 / Math.PI - 90;
		if (zDiff < 0.0) {
			newYaw = newYaw + Math.abs(180 - newYaw) * 2;
		}
		getEntity().yaw = (float) (newYaw - 90);
		getEntity().pitch = (float) newPitch;
		//((EntityPlayer) getEntity()).az = (float) (newYaw - 90);//Changed due to 1.6 update.
		((EntityPlayer) getEntity()).az = (int) (newYaw - 90);
	}

	public void putInBed(final Location bed) {
		getEntity().setPosition(bed.getX(), bed.getY(), bed.getZ());
		getEntity().a((int) bed.getX(), (int) bed.getY(), (int) bed.getZ());
	}

	public void setItemInHand(final Material m) {
		setItemInHand(m, (short) 0);
		updateEquipment();
	}

	public void setItemInHand(final Material m, final short damage) {
		((HumanEntity) getEntity().getBukkitEntity()).setItemInHand(new ItemStack(m, 1, damage));
		updateEquipment();
	}

	public void setName(final String name) {
		//((NPCEntity) getEntity()).name = name;//Changed due to 1.6 update.
		((NPCEntity) getEntity()).displayName = name;
	}

	public void setSneaking() {
		getEntity().setSneaking(true);
	}
	
	public void updateEquipment() {
		for (int i = 0; i < previousEquipment.length; i++) {
			final net.minecraft.server.v1_6_R2.ItemStack previous = previousEquipment[i];
			final net.minecraft.server.v1_6_R2.ItemStack current = ((EntityPlayer) getEntity()).getEquipment(i);
			if (previous != current) {
				NPCUtils.sendPacketNearby(getBukkitEntity().getLocation(), new Packet5EntityEquipment(getEntity().id, i, current), 20);
				previousEquipment[i] = current;
			}
		}
	}
	public void setRotation(float yaw) {
		getEntity().yaw = (float) (yaw - 90);
		//((EntityPlayer) getEntity()).az = (float) (yaw - 90);//Changed due to 1.6 update.
		((EntityPlayer) getEntity()).az = (int) (yaw - 90);
		//((EntityPlayer)getEntity()).az=yaw;
	}

}