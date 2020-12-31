package me.titan.titanbattleroyal.util;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Player;

import java.util.EnumSet;

public class BattleRoyalUtil {
	private static BlockFace[] radial = {BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST};
	public static BlockFace yawToFace(float yaw) {
		return radial[Math.round(yaw / 45f) & 0x7].getOppositeFace();
	}

	public static void smoothTp(org.bukkit.entity.Entity en, Location l) {
		Entity e = ((CraftEntity) en).getHandle();
		byte b0 = 0;
		int i = b0 + 1;
		CommandAbstract.CommandNumber num = null;
		CommandAbstract.CommandNumber num1 = null;
		CommandAbstract.CommandNumber num2 = null;
		CommandAbstract.CommandNumber num3 = null;
		CommandAbstract.CommandNumber num4 = null;
		try {
			num = CommandAbstract.a(e.locX, l.getX() + "", true);

			num1 = CommandAbstract.a(e.locY, l.getY() + "", 0, 0, false);
			num2 = CommandAbstract.a(e.locZ, l.getZ() + "", true);
			num3 = CommandAbstract.a((double) e.yaw, l.getYaw() + "", false);
			num4 = CommandAbstract.a((double) e.pitch, l.getPitch() + "", false);
		} catch (ExceptionInvalidNumber exceptionInvalidNumber) {
			exceptionInvalidNumber.printStackTrace();
		}
		float f;

		if(en instanceof Player){
			EntityPlayer p = ((EntityPlayer) e);
		EnumSet enumset = EnumSet.noneOf(PacketPlayOutPosition.EnumPlayerTeleportFlags.class);
		if (num.c()) {
			enumset.add(PacketPlayOutPosition.EnumPlayerTeleportFlags.X);
		}

		if (num1.c()) {
			enumset.add(PacketPlayOutPosition.EnumPlayerTeleportFlags.Y);
		}

		if (num2.c()) {
			enumset.add(PacketPlayOutPosition.EnumPlayerTeleportFlags.Z);
		}

		if (num4.c()) {
			enumset.add(PacketPlayOutPosition.EnumPlayerTeleportFlags.X_ROT);
		}

		if (num3.c()) {
			enumset.add(PacketPlayOutPosition.EnumPlayerTeleportFlags.Y_ROT);
		}

		f = (float) num3.b();
		if (!num3.c()) {
			f = MathHelper.g(f);
		}

		float f1 = (float) num4.b();
		if (!num4.c()) {
			f1 = MathHelper.g(f1);
		}

		if (f1 > 90.0F || f1 < -90.0F) {
			f1 = MathHelper.g(180.0F - f1);
			f = MathHelper.g(f + 180.0F);
		}

		p.mount((Entity) null);
		p.playerConnection.a(num.b(), num1.b(), num2.b(), f, f1, enumset);
		p.f(f);
	}else

	{
		float f2 = (float) MathHelper.g(num3.a());
		f = (float) MathHelper.g(num4.a());
		if (f > 90.0F || f < -90.0F) {
			f = MathHelper.g(180.0F - f);
			f2 = MathHelper.g(f2 + 180.0F);
		}

		((Entity) e).setPositionRotation(num.a(), num1.a(), num2.a(), f2, f);
		((Entity) e).f(f2);
	}

		//CommandAbstract.a(p, new CommandTp(), "commands.tp.success.coordinates", p.getName(), num.a(), num1.a(), num2.a());

	}
	public static Location applyMod(Location loc, double modX, double modY, double modZ){
		return new Location(loc.getWorld(),loc.getX() + modX ,loc.getY() + modY, loc.getZ() + modZ, loc.getYaw(), loc.getPitch() );

	}
}
