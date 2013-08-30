package net.mctower.npc;

import java.io.IOException;
import java.lang.reflect.Field;

import net.minecraft.server.v1_6_R2.Connection;
import net.minecraft.server.v1_6_R2.MinecraftServer;
import net.minecraft.server.v1_6_R2.NetworkManager;
import net.minecraft.server.v1_6_R2.Packet;

/**
 * 
 * @author martin
 */
public class NPCNetworkManager extends NetworkManager {

	public NPCNetworkManager() throws IOException {
		super(MinecraftServer.getServer().getLogger(), new NullSocket(), "NPC Manager", new Connection() {
			@Override
			public boolean a() {
				return true;
			}
		}, null);
		try {
			final Field f = NetworkManager.class.getDeclaredField("n");
			f.setAccessible(true);
			f.set(this, false);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void a() {
	}

	@Override
	public void a(final Connection connection) {
	}

	@Override
	public void a(final String s, final Object... aobject) {
	}

	@Override
	public void queue(final Packet packet) {
	}

}