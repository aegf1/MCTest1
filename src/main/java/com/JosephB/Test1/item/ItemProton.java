package com.josephb.test1.item;

public class ItemProton extends ItemTest1
{
	
	private final String name = "Proton";
	
	public ItemProton()
	{
		super();
		this.setUnlocalizedName(name);
	}

	@Override
	public String getName()
	{
		return name;
	}
}
