package Discord.Commands;

import Root.Root;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * @author SirMangler
 *
 * @date 30 Mar 2019
 */
public class listNames extends Command {

	@Override
	public boolean execute(String[] arguments, TextChannel channel) {
		String[] names = new String[Root.names.size()];
		String list = String.join(", ", Root.names.toArray(names));
		
		channel.sendMessage("Names: "+list).complete();
		
		return true;
	}

	@Override
	public String getName() {
		return "listnames";
	}

	@Override
	public boolean isAdmin() {
		return true;
	}
}
