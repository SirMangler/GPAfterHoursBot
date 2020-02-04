package Discord.Commands;

import Root.Root;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * @author SirMangler
 *
 * @date 1 Apr 2019
 */
public class reloadConfig extends Command {

	@Override
	public boolean execute(String[] arguments, TextChannel channel) {
		Root.loadConfig();
		
		channel.sendMessage("Reloaded the Config").queue();
		
		return true;
	}

	@Override
	public String getName() {
		return "reloadconfig";
	}

	@Override
	public boolean isAdmin() {
		return true;
	}

}
