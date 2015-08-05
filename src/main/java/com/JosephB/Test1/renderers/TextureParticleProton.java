package com.josephb.test1.renderers;

import com.josephb.test1.reference.Reference;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureClock;
import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public class TextureParticleProton extends TextureAtlasSprite
{

	private static ResourceLocation loc = new ResourceLocation(Reference.MOD_ID_LC, "textures/item/proton.png");
	
	protected TextureParticleProton(String spriteName) 
	{
		super(spriteName);
	}
	
	public TextureParticleProton()
	{
		super(loc.toString());
	}
	
	 @Override
	 public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location) 
	 {
		 return true;
	 }
}
