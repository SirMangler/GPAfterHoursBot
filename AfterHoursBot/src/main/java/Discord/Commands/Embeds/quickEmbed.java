package Discord.Commands.Embeds;

import Discord.Commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * @author SirMangler
 *
 * @date 1 Apr 2019
 */
public class quickEmbed extends Command {

	@Override
	public boolean execute(String[] arguments, TextChannel channel) {
		String description = String.join(" ", arguments);
		
		EmbedBuilder b = new EmbedBuilder();
		b.setDescription(description);
		b.setColor(6684927);
		
		channel.sendMessage(b.build()).complete();
		//channel.getGuild().getTextChannelById(id)
		return false;
	}

	@Override
	public String getName() {
		return "quickembed";
	}

	@Override
	public boolean isAdmin() {
		return true;
	}

}
