package com.josephb.maxwellcraft.commands;

import java.util.ArrayList;
import java.util.List;

import com.josephb.maxwellcraft.utility.dataoutput.OutputHelper;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class StartDataRecord implements ICommand
{
	private List aliases;
	public StartDataRecord()
	{
		this.aliases = new ArrayList();
		aliases.add("startrecord");
	}
	
	@Override
	public int compareTo(Object arg0) 
	{
		return 0;
	}

	@Override
	public String getName() 
	{
		return "startdatarecord";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) 
	{
		
		return "startdatarecord";
	}

	@Override
	public List getAliases() 
	{
		return aliases;
	}

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException 
	{
		if(OutputHelper.startOutput())
		{
			sender.addChatMessage(new ChatComponentText("Starting to record particle positions"));
		}
		else
		{
			sender.addChatMessage(new ChatComponentText("Already running!"));
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
