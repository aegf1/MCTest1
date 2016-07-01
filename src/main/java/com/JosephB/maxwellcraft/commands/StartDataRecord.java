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

/**
 * Defines command to start outputting entity data to a file
 * @author Joseph Brownless
 */
public class StartDataRecord implements ICommand
{
	/**
	 * List of alternative names for command
	 */
	private List aliases;
	
	/**
	 * Create command and define aliases
	 */
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

	/**
	 * @return command name
	 */
	@Override
	public String getCommandName() 
	{
		return "startdatarecord";
	}

	/**
	 * @return What is given to the user to describe how to use the command
	 */
	@Override
	public String getCommandUsage(ICommandSender sender) 
	{
		
		return "startdatarecord";
	}

	/**
	 * Get alternative names for command
	 */
	@Override
	public List getCommandAliases() 
	{
		return aliases;
	}

	/**
	 * Called when command is input by user.
	 * Starts outputting entity data to external file
	 */
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

	/**
	 * Checks if particular user can use command
	 */
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

	/**
	 * Returns if particular argument is a username
	 */
	@Override
	public boolean isUsernameIndex(String[] args, int index) 
	{
		return false;
	}

}
