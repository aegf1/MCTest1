package com.josephb.test1.block;

import net.minecraft.block.material.Material;

public class BlockMagnet extends BlockTest1
{
	private final String name = "Magnet";
	
	public BlockMagnet()
	{
		super(Material.iron);
		this.setUnlocalizedName(name);
		
	}
	
	@Override
	public String getName()
	{
		return name;
	}

}
