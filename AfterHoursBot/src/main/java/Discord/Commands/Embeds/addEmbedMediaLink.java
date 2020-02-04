package Discord.Commands.Embeds;

import Discord.InfoEmbed;
import Discord.Commands.Command;
import Root.Root;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * @author SirMangler
 *
 * @date 31 Mar 2019
 */
public class addEmbedMediaLink extends Command {
	
	@Override
	public boolean execute(String[] arguments, TextChannel channel) {
		Root.addMediaLink(arguments[0], arguments[1]);
		
		InfoEmbed.updateEmbed();
		
		channel.sendMessage("Added "+arguments[0]+" with the link "+arguments[1]).queue();
		
		return true;
	}

	@Override
	public String getName() {
		return "addmedialink";
	}

	@Override
	public boolean isAdmin() {
		return true;
	}

}
