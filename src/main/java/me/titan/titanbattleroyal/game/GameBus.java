package me.titan.titanbattleroyal.game;

import me.titan.titanbattleroyal.core.TitanBattleRoyalPlugin;
import me.titan.titanbattleroyal.util.BattleRoyalUtil;
import me.titan.titanbattleroyal.util.BlockType;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class GameBus extends BukkitRunnable {

	final BlockFace moveDir;
	final Map<Integer, Block> blocks;
	final BattleRoyalGame game;

	public static int chunkLimit = 50;

	public List<Player> playersIn = new ArrayList<>();

	public static int height = 1;

	public static String IN_BUS_META = "IN_BUS";
	public GameBus(BlockFace moveDir, Map<Integer, Block> blocks, BattleRoyalGame game) {
		this.moveDir = moveDir;
		this.blocks = blocks;
		this.game = game;
		playersIn.addAll(game.players);
	}

	public void playerJump(Player p){
		p.setFlying(true);
		playersIn.remove(p);
		PlayerPreLandController c = new PlayerPreLandController(p);
		BattleRoyalUtil.smoothTp(p, p.getLocation().clone().subtract(0,height,0));
        c.runTaskTimer(TitanBattleRoyalPlugin.instance,0,10);

	}

	int chunkMoved = 0;
	int lastChunkX = 0, lastChunkZ = 0;
	int NewChunkX = 0, NewChunkZ = 0;

	public static Map<Integer, Block> build(Block s, BlockFace moveDir){
		Map<Integer, Block> blocks = new HashMap<>();
		blocks.put(0,s.getRelative(BlockFace.EAST));
		blocks.put(1, s.getRelative(BlockFace.NORTH_EAST));
		blocks.put(2, s.getRelative(BlockFace.SOUTH_EAST));
		blocks.put(3,s);
		blocks.put(4,s.getRelative(BlockFace.SOUTH));
		blocks.put(5,s.getRelative(BlockFace.NORTH));
		blocks.put(6,s.getRelative(BlockFace.WEST));
		blocks.put(7, s.getRelative(BlockFace.NORTH_WEST));
		blocks.put(8, s.getRelative(BlockFace.SOUTH_WEST));
		return blocks;
	}
	Map<Integer
			, BlockType> types = new HashMap<>();
	public void move(BlockFace dir){

		chunkLimit = 200;

		if(types.isEmpty()) {

			for (int i = 0; i < blocks.size(); i++) {
				Block b = blocks.get(i);


					types.put(i, new BlockType(b));
			}
		}
		Map<Block, BlockType> ntypes = new HashMap<>();
//		Player titan = Bukkit.getPlayer("Lolalib");
//		if(titan.getItemInHand().getType() != Material.AIR){
//			titan.sendMessage(types + "");
//			titan.sendMessage("____________________________________");
//		}

		Map<Integer, Block> nblocks = new HashMap<>();
		int xDif = 0, zDif = 0;
		Iterator<Map.Entry<Integer, org.bukkit.block.Block>> it = blocks.entrySet().iterator();
		for(int i =0;i<blocks.size();i++){
			Block b = blocks.get(i);


			// N|<|--L
			Block nb = b.getRelative(dir);
			org.bukkit.Chunk c = b.getChunk();
			Chunk nc = nb.getChunk();
			if(i == 1 && lastChunkX != nc.getX() || lastChunkZ != nc.getZ()){
				lastChunkX = nc.getX();
				lastChunkZ = nc.getZ();

				chunkMoved++;
			}

			xDif = nb.getX() -  b.getX();
			zDif =  nb.getZ() - b.getZ();



			ntypes.put(nb,types.get(i));


			nblocks.put(i,nb);

		}
		for(Block b : blocks.values()){
			b.setType(Material.AIR);
		}
		for(Map.Entry<Block, BlockType> en : ntypes.entrySet()){
			en.getKey().setTypeIdAndData(en.getValue().getTypeId(),en.getValue().getData(),false);
		}

		blocks.clear();
		blocks.putAll(nblocks);

		for(Player p : playersIn){


		 Location nloc = new Location(p.getWorld(), p.getLocation().getX()+xDif,p.getLocation().getY(),p.getLocation().getZ() + zDif, p.getLocation().getYaw(), p.getLocation().getPitch());
		 BattleRoyalUtil.smoothTp(p,nloc);
		}

	}
	


	@Override
	public void run() {

		move(moveDir);
		if(chunkMoved > chunkLimit){
			cancel();
		}

	}
}
