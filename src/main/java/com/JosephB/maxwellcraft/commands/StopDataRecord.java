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
 * Defines command to stop outputting entity data to a file
 * @author Joseph Brownless
 */
public class StopDataRecord implements ICommand
{
	/**
	 * List of alternative names for command
	 */
	private List aliases;
	
	/**
	 * Create command and define aliases
	 */
	public StopDataRecord()
	{
		this.aliases = new ArrayList();
		aliases.add("stoprecord");
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
		return "stopdatarecord";
	}

	/**
	 * @return What is given to the user to describe how to use the command
	 */
	@Override
	public String getCommandUsage(ICommandSender sender) 
	{
		
		return "stopdatarecord";
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
	 * Stops outputting entity data to external file
	 */
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(OutputHelper.finishOutput())
		{
			sender.addChatMessage(new TextComponentString("Stopping recording particle positions"));
		}
		else
		{
			sender.addChatMessage(new TextComponentString("Not running!"));
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
