package com.josephb.test1.block;

import com.josephb.test1.reference.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

public class BlockTest1 extends Block
{
	/**
	 * Constructor that lets you define block material
	 * @param material
	 */
	public BlockTest1(Material material)
	{
		super(material);
	}

	/**
	 * Constructor that sets block material to iron
	 */
	public BlockTest1()
	{
		this(Material.iron);
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return String.format("tile.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	
	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
	
	public String getName()
	{
		return null;
	}
}
