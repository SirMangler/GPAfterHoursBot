package Discord.Commands.Embeds;

import net.dv8tion.jda.core.entities.TextChannel;
import Discord.InfoEmbed;
import Discord.Commands.Command;
import Root.Root;

/**
 * @author SirMangler
 *
 * @date 31 Mar 2019
 */
public class updateRules extends Command {

	@Override
	public boolean execute(String[] arguments, TextChannel channel) {
		Root.updateRules(String.join(" ", arguments));
		
		InfoEmbed.updateEmbed();
		
		channel.sendMessage("Updated Rules").queue();
		
		return false;
	}

	@Override
	public String getName() {
		return "updaterules";
	}

	@Override
	public boolean isAdmin() {
		return true;
	}

}
