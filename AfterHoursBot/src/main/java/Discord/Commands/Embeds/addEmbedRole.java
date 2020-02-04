package Discord.Commands.Embeds;

import java.util.Arrays;

import Discord.InfoEmbed;
import Discord.Commands.Command;
import Root.Root;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * @author SirMangler
 *
 * @date 31 Mar 2019
 */
public class addEmbedRole extends Command {

	@Override
	public boolean execute(String[] arguments, TextChannel channel) {
		Root.addRole(arguments[0], String.join(" ", Arrays.copyOfRange(arguments, 1, arguments.length)));
		
		InfoEmbed.updateEmbed();
		
		channel.sendMessage("Added "+arguments[0]+" with the description "+arguments[1]).queue();
		
		return true;
	}

	@Override
	public String getName() {
		return "addrole";
	}

	@Override
	public boolean isAdmin() {
		return true;
	}

}
