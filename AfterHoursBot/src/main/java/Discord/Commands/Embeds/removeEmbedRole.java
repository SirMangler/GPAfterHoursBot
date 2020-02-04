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
public class removeEmbedRole extends Command {

	@Override
	public boolean execute(String[] arguments, TextChannel channel) {
		Root.removeRole(arguments[0]);
		
		InfoEmbed.updateEmbed();
		
		channel.sendMessage("Removed "+arguments[0]).queue();
		
		return true;
	}

	@Override
	public String getName() {
		return "removerole";
	}

	@Override
	public boolean isAdmin() {
		return true;
	}

}
