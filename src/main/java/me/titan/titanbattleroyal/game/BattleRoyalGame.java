package me.titan.titanbattleroyal.game;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import me.titan.titanbattleroyal.core.MetaManager;
import me.titan.titanbattleroyal.core.TitanBattleRoyalPlugin;
import me.titan.titanbattleroyal.util.SchematicScanner;
import me.titan.titanlib.Common;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BattleRoyalGame {

	final World world;
	final Region busSchem;


	List<Player> players = new ArrayList<>();


	GameBus bus;

	public BattleRoyalGame(World world, Region busSchem) {
		this.world = world;
		this.busSchem = busSchem;
		TitanBattleRoyalPlugin.instance.currentGame = this;


	}
	public void joinPlayer(Player p){

		players.add(p);
		Common.tell(p,"&6You have joined a battle royal game!");

	}

	public void start(){

		Location spawnPoint = world.getSpawnLocation();

		Vector c = busSchem.getCenter();
		Block s = new Location(world,c.getX(), c.getY(), c.getZ()).getBlock();
		Map<Integer, Block> busBlocks = SchematicScanner.scan((CuboidRegion) busSchem, world);


		//if(true) return;


//		for(Block b : busBlocks.values()){
//			b.setType(Material.IRON_BLOCK);
//		}
		bus = new GameBus(BlockFace.EAST,busBlocks, this);
		for(Player p : players){
			p.teleport(s.getLocation().clone().add(0,1,0));
			MetaManager.addMetadata(p,GameBus.IN_BUS_META,bus);


		}
		Bukkit.getScheduler().cancelTasks(TitanBattleRoyalPlugin.instance);
		bus.runTaskTimer(TitanBattleRoyalPlugin.instance, 40,20);



	}
}
