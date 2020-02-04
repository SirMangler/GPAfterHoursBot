package Discord.Commands;

import Discord.DiscordBot;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * @author SirMangler
 *
 * @date 31 Mar 2019
 */
public class ToggleClock extends Command {
	
	@Override
	public boolean execute(String[] arguments, TextChannel channel) {
		boolean running = DiscordBot.clock.toggleRunning();
		
		if (running) channel.sendMessage("Clock is now running.").queue();
		else channel.sendMessage("Clock is no longer running.").queue();
		
		return true;
	}

	@Override
	public String getName() {
		return "toggleclock";
	}

	@Override
	public boolean isAdmin() {
		return true;
	}

}
