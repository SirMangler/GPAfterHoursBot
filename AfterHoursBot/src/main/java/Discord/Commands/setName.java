package Discord.Commands;

import Discord.DiscordBot;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * @author SirMangler
 *
 * @date 30 Mar 2019
 */
public class setName extends Command {

	@Override
	public boolean execute(String[] arguments, TextChannel channel) {
		String newName = String.join(" ", arguments);
		
		DiscordBot.guild.getController().setNickname(DiscordBot.guild.getSelfMember(), newName).complete();
		
		channel.sendMessage("Nickname set to "+newName).complete();
		return true; 
	}

	@Override
	public String getName() {
		return "setname";
	}
	
	@Override
	public boolean isAdmin() {
		return true;
	}
}
