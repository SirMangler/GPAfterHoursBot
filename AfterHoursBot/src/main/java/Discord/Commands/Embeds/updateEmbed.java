package Discord.Commands.Embeds;

import Discord.InfoEmbed;
import Discord.Commands.Command;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * @author SirMangler
 *
 * @date 31 Mar 2019
 */
public class updateEmbed extends Command {

	@Override
	public boolean execute(String[] arguments, TextChannel channel) {
		InfoEmbed.updateEmbed();
		channel.sendMessage("Embed Updated").queue();
		
		return true;
	}

	@Override
	public String getName() {
		return "updateembed";
	}

	@Override
	public boolean isAdmin() {
		return true;
	}

}
