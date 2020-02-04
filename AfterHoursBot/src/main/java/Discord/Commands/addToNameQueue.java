package Discord.Commands;

import Root.Root;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * @author SirMangler
 *
 * @date 30 Mar 2019
 */
public class addToNameQueue extends Command {

	@Override
	public boolean execute(String[] arguments, TextChannel channel) {
		String nickName = String.join(" ", arguments);
		
		Root.addName(nickName);
		
		channel.sendMessage(nickName + " added to names list.").complete();	
		return true; 
	}
	
	@Override
	public String getName() {
		return "addname";
	}
	
	@Override
	public boolean isAdmin() {
		return true;
	}
}
