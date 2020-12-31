package me.titan.titanbattleroyal.listeners;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.regions.Region;
import me.titan.titanbattleroyal.core.MetaManager;
import me.titan.titanbattleroyal.core.TitanBattleRoyalPlugin;
import me.titan.titanbattleroyal.game.BattleRoyalGame;
import me.titan.titanbattleroyal.game.GameBus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerListener implements Listener {
	@EventHandler
	public void PlayerJoin(PlayerJoinEvent e){
		System.out.println("gg");
		Player p = e.getPlayer();

		new BukkitRunnable(){

			@Override
			public void run() {
				LocalSession se = WorldEdit.getInstance().getSession(p.getName());
				Region r = null;
				try {
					r= se.getSelection(se.getSelectionWorld());



				} catch (IncompleteRegionException incompleteRegionException) {
					return;
				}
				BattleRoyalGame game = new BattleRoyalGame(p.getWorld(), r);
				game.joinPlayer(p);
				game.start();
			}
		}.runTaskLater(TitanBattleRoyalPlugin.instance, 40);

	}

	@EventHandler
	public void onShift(PlayerToggleSneakEvent e){

		Player p = e.getPlayer();

		if(p.hasMetadata(GameBus.IN_BUS_META)){
			GameBus bus = (GameBus) MetaManager.getMetadataObject(p,GameBus.IN_BUS_META);
			bus.playerJump(p);
			MetaManager.removeMetadata(p, GameBus.IN_BUS_META);
		}
	}
}
