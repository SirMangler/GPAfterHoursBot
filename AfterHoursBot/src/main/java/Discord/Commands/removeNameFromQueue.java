package Discord.Commands;

import Root.Root;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * @author SirMangler
 *
 * @date 30 Mar 2019
 */
public class removeNameFromQueue extends Command {

	@Override
	public boolean execute(String[] arguments, TextChannel channel) {
		String nickName = String.join(" ", arguments);
		
		Root.removeName(nickName);
		
		channel.sendMessage(nickName + " removed from names list.").complete();
		return true;
	}

	@Override
	public String getName() {
		return "removename";
	}

	@Override
	public boolean isAdmin() {
		return true;
	}
}
