package me.titan.titanbattleroyal.util;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockType {
	final Material material;
	final byte data;

	public BlockType(Material material, byte data) {
		this.material = material;
		this.data = data;
	}
	public BlockType(Block b) {
		this.material = b.getType();
		this.data = b.getData();
	}

	public byte getData() {
		return data;
	}

	public int getTypeId(){
		return material.getId();
	}
}
