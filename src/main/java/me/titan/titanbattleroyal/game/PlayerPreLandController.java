package me.titan.titanbattleroyal.game;

import me.titan.titanbattleroyal.util.BattleRoyalUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class PlayerPreLandController extends BukkitRunnable {

	final Player p;
	int firstY;
	BlockFace lastFace;

	public PlayerPreLandController(Player p) {
		this.p = p;

		firstY = p.getLocation().getBlockY();
	}

	public boolean landed(){
		Player pl = Bukkit.getPlayer(p.getUniqueId());
		int highest = pl.getWorld().
				getHighestBlockYAt(pl.getLocation().getBlockX(), pl.getLocation().getBlockZ());

		// __
		// __
		// |
		return  highest < firstY && highest  >= pl.getLocation().getBlockY()-1;
	}
	public Location loc(){
		return p.getLocation();
	}

	@Override
	public void run() {
		if(landed()) {
			p.sendMessage("Landed");
			p.setFlying(false);
			cancel();

			return;
		}
		BlockFace face = BattleRoyalUtil.yawToFace(loc().getYaw());
		if(face == BlockFace.UP){
			face = BlockFace.SELF;
		}

//		if(lastFace == face) return;
//		lastFace = face;
//		BattleRoyalUtil.smoothTp(p,BattleRoyalUtil.applyMod(loc()
//		,(double)face.getModX()*2,(double) -(face.getModY()+1)*2,(double)face.getModZ()*2));

		p.setVelocity(new Vector((float)face.getModX(),(float) -(face.getModY()+1), (float)face.getModZ()).multiply(0.5));


	}
}
