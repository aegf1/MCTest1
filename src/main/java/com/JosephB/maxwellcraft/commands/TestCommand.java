package com.josephb.maxwellcraft.commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class TestCommand implements ICommand
{
	private List aliases;
	public TestCommand()
	{
		this.aliases = new ArrayList();
	}
	
	@Override
	public int compareTo(Object arg0) 
	{
		return 0;
	}

	@Override
	public String getName() 
	{
		return "test";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) 
	{
		
		return "test <text>";
	}

	@Override
	public List getAliases() 
	{
		return aliases;
	}

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException 
	{
		if(args.length == 0)
		{
			sender.addChatMessage(new ChatComponentText("Invalid arguments"));
		}
		else
		{
			sender.addChatMessage(new ChatComponentText(
					"You said: [" + args[0] + "]"));
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
