package Discord.Commands;

import java.util.Arrays;

import Discord.DiscordBot;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * @author SirMangler
 *
 * @date 30 Mar 2019
 */
public abstract class Command {

	public boolean tryCommand(String line, TextChannel channel) {
		String[] tokens = line.split(" ");
		String command = tokens[0];
		
		String[] arguments = Arrays.copyOfRange(tokens, 1, tokens.length);
		
		String prefix = DiscordBot.commandPrefix+getName();
		if (command.toLowerCase().startsWith(prefix)) {
			return execute(arguments, channel);
		}
		
		return false;
	}
	
	abstract public boolean execute(String[] arguments, TextChannel channel);
	
	abstract public String getName();
	
	abstract public boolean isAdmin();
}
