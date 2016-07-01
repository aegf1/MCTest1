package com.JosephB.maxwellcraft.commands;

import java.util.ArrayList;
import java.util.List;

import com.JosephB.maxwellcraft.utility.physics.EMField;
import com.JosephB.maxwellcraft.utility.physics.Vector3;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

/**
 * Defines command to get magnetic field at a point.
 * @author Joseph Brownless
 */
public class GetBField implements ICommand
{
	/**
	 * List of alternative names for command
	 */
	private List aliases;
	
	/**
	 * Create command and define aliases
	 */
	public GetBField()
	{
		this.aliases = new ArrayList();
		aliases.add("bfield");
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
		return "getbfield";
	}

	/**
	 * @return What is given to the user to describe how to use the command
	 */
	@Override
	public String getCommandUsage(ICommandSender sender) 
	{
		
		return "getbfield <x> <y> <z>";
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
	 * Sends user message containing x,y,z components of magnetic field at provided coordinates
	 */
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if(args.length == 3)
		{
			double x = Double.parseDouble(args[0]);
			double y = Double.parseDouble(args[1]);
			double z = Double.parseDouble(args[2]);
			sender.addChatMessage(new TextComponentString(EMField.getBField(new Vector3(x,y,z)).toString()));
		}
		else
		{
			sender.addChatMessage(new TextComponentString("Invalid agruments"));
		}
		
	}

	/**
	 * Returns if particular argument is a username
	 */
	@Override
	public boolean isUsernameIndex(String[] args, int index) 
	{
		return false;
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

}
