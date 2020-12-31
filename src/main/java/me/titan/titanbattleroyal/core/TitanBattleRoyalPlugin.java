package me.titan.titanbattleroyal.core;

import me.titan.titanbattleroyal.game.BattleRoyalGame;
import me.titan.titanbattleroyal.listeners.PlayerListener;
import me.titan.titanlib.TitanLib;
import me.titan.titanlib.TitanPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class TitanBattleRoyalPlugin extends JavaPlugin implements TitanPlugin {

	public static TitanBattleRoyalPlugin instance;


	public BattleRoyalGame currentGame;

	@Override
	public void onEnable() {
		instance = this;
		try{
			TitanLib.setPlugin(this);
		}catch(NoClassDefFoundError ex){

		}


		Bukkit.getPluginManager().registerEvents(new PlayerListener(),this);
	}

	@Override
	public void onDisable() {
		instance = null;
	}

	@Override
	public void onPreReload() {

	}

	@Override
	public void onReload() {

	}

	@Override
	public void saveResource(String s, boolean b) {

		super.saveResource(s,b);
	}
}
