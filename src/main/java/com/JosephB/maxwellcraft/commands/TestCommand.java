package com.JosephB.maxwellcraft.commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class TestCommand implements ICommand
{
	private List aliases;
	public TestCommand()
	{
		this.aliases = new ArrayList();
	}
	
	@Override
	public int compareTo(ICommand arg0) 
	{
		return 0;
	}

	@Override
	public String getCommandName() 
	{
		return "test";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) 
	{
		
		return "test <text>";
	}

	@Override
	public List getCommandAliases() 
	{
		return aliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(args.length == 0)
		{
			sender.addChatMessage(new TextComponentString("Invalid arguments"));
		}
		else
		{
			sender.addChatMessage(new TextComponentString(
					"You said: [" + args[0] + "]"));
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
