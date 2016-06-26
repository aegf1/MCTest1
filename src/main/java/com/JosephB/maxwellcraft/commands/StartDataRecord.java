package com.JosephB.maxwellcraft.commands;

import java.util.ArrayList;
import java.util.List;

import com.JosephB.maxwellcraft.utility.dataoutput.OutputHelper;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class StartDataRecord implements ICommand
{
	private List aliases;
	public StartDataRecord()
	{
		this.aliases = new ArrayList();
		aliases.add("startrecord");
	}
	
	@Override
	public int compareTo(ICommand arg0) 
	{
		return 0;
	}

	@Override
	public String getCommandName() 
	{
		return "startdatarecord";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) 
	{
		
		return "startdatarecord";
	}

	@Override
	public List getCommandAliases() 
	{
		return aliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(OutputHelper.startOutput())
		{
			sender.addChatMessage(new TextComponentString("Starting to record particle positions"));
		}
		else
		{
			sender.addChatMessage(new TextComponentString("Already running!"));
		}
		
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) 
	{
		return true;
	}

	@Override
	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args,
			net.minecraft.util.math.BlockPos pos) 
	{
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) 
	{
		return false;
	}

}
