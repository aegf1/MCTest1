package com.josephb.maxwellcraft.init;

import com.josephb.maxwellcraft.reference.Reference;
import com.josephb.maxwellcraft.tileentity.*;

import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Class with methods to register all tile entities in mod
 * @author Joseph
 *
 */
@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModTileEntities 
{
	
	public static void init()
	{
		GameRegistry.registerTileEntity(TileEntityMagnet.class, "Tile_Entity_Magnet");
		GameRegistry.registerTileEntity(TileEntityPosCharge.class, "Tile_Entity_PosCharge");
		GameRegistry.registerTileEntity(TileEntityNegCharge.class, "Tile_Entity_NegCharge");
		//Do this for every tile entity
	}
}