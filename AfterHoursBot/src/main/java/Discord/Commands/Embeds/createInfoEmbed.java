package Discord.Commands.Embeds;

import Discord.DiscordBot;
import Discord.InfoEmbed;
import Discord.Commands.Command;
import net.dv8tion.jda.core.entities.TextChannel;
import Root.Root;

/**
 * @author SirMangler
 *
 * @date 31 Mar 2019
 */
public class createInfoEmbed extends Command {

	@Override
	public boolean execute(String[] arguments, TextChannel channel) {
		InfoEmbed.embed = channel.sendMessage(DiscordBot.infoembed.buildEmbed()).complete();
		
		Root.setinfoEmbedID(InfoEmbed.embed.getId(), channel.getId());
		
		return true;
	}
	
	@Override
	public String getName() {
		return "infoembed";
	}

	@Override
	public boolean isAdmin() {
		return true;
	}

}
