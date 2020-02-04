package Discord.Commands;

import net.dv8tion.jda.core.entities.TextChannel;
import Root.Root;

/**
 * @author SirMangler
 *
 * @date 31 Mar 2019
 */
public class setMusicChannel extends Command {

	@Override
	public boolean execute(String[] arguments, TextChannel channel) {
		Root.setMusicChannel(arguments[0]);
		channel.sendMessage("Updated music channel to: "+channel.getJDA().getVoiceChannelById(arguments[0]).getName()).queue();
		return false;
	}

	@Override
	public String getName() {
		return "setmusicchannel";
	}

	@Override
	public boolean isAdmin() {
		return true;
	}

}
