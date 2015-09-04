package com.josephb.maxwellcraft.commands;

import java.util.ArrayList;
import java.util.List;

import com.josephb.maxwellcraft.utility.physics.EMField;
import com.josephb.maxwellcraft.utility.physics.Vector3;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

public class GetBField implements ICommand
{
	private List aliases;
	public GetBField()
	{
		this.aliases = new ArrayList();
		aliases.add("bfield");
	}
	
	@Override
	public int compareTo(Object arg0) 
	{
		return 0;
	}

	@Override
	public String getName() 
	{
		return "getbfield";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) 
	{
		
		return "getbfield <x> <y> <z>";
	}

	@Override
	public List getAliases() 
	{
		return aliases;
	}

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException 
	{
		if(args.length == 3)
		{
			double x = Double.parseDouble(args[0]);
			double y = Double.parseDouble(args[1]);
			double z = Double.parseDouble(args[2]);
			sender.addChatMessage(new ChatComponentText(EMField.getBField(new Vector3(x,y,z)).toString()));
		}
		else
		{
			sender.addChatMessage(new ChatComponentText("Invalid agruments"));
		}
		
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) 
	{
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) 
	{
		return false;
	}

}
