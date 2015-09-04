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

public class StopDataRecord implements ICommand
{
	private List aliases;
	public StopDataRecord()
	{
		this.aliases = new ArrayList();
		aliases.add("stoprecord");
	}
	
	@Override
	public int compareTo(Object arg0) 
	{
		return 0;
	}

	@Override
	public String getName() 
	{
		return "stopdatarecord";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) 
	{
		
		return "stopdatarecord";
	}

	@Override
	public List getAliases() 
	{
		return aliases;
	}

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException 
	{
		if(OutputHelper.finishOutput())
		{
			sender.addChatMessage(new ChatComponentText("Stopping recording particle positions"));
		}
		else
		{
			sender.addChatMessage(new ChatComponentText("Not running!"));
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
