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
public class removeEmbedMediaLink extends Command {

	@Override
	public boolean execute(String[] arguments, TextChannel channel) {
		Root.removeMediaLink(arguments[0]);
		
		InfoEmbed.updateEmbed();
		
		channel.sendMessage("Removed "+arguments[0]).queue();
		
		return true;
	}

	@Override
	public String getName() {
		return "removemedialink";
	}

	@Override
	public boolean isAdmin() {
		return true;
	}

}
