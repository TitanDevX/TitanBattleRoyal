package me.titan.titanbattleroyal.util;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Iterator;

public class SchematicScanner {

	public static MoveableStructure scan(CuboidRegion rg, World w){
		MoveableStructure m = new MoveableStructure();
		Iterator<BlockVector> it = rg.iterator();

		int size = 0;
		for(int i =0;it.hasNext();i++){

			BlockVector v = it.next();
			m.put(i, new Location(w, v.getX(),v.getY(), v.getZ()).getBlock());
			size = i;
		}
		System.out.println(rg.getWidth() + " " + rg.getLength() + " " + rg.getHeight() + " " + rg.getArea());

//		Location pos1 = new Location(w,rg.getPos1().getX(), rg.getPos1().getY(), rg.getPos2().getZ());
//		List<Location> topEastCorner = new ArrayList<>();
//		for(int i =0;i<rg.getLength();i++){
//			Location nloc = pos1.clone().subtract(0,0,i);
//
//			m.put(i, nloc.getBlock());
//			for(int h =0;h<rg.getHeight();h++){
//
//				Location nloc2 = nloc.clone().subtract(0,h,0);
//				m.put(rg.getLength()+h, nloc2.getBlock());
//			}
//		}
		System.out.println(m);

		return m;

	}

}
