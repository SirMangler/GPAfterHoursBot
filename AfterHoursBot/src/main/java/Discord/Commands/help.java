package Discord.Commands;

import java.awt.Color;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * @author SirMangler
 *
 * @date 29 Apr 2019
 */
public class help extends Command {

	@Override
	public boolean execute(String[] arguments, TextChannel channel) {
		EmbedBuilder b = new EmbedBuilder();
		b.setTitle("List of Commands");
		b.setDescription(
				"```http\n"+
				"play [link] - Plays the link (e.g youtube or spotify) in the music channel.\n\n"+
				"skip - Skips the playing song.\n\n"+
				"listcommands - Admin Command.\n\n"+
				"ping - Tests response time.\n\n```"
				);
		
		b.setColor(Color.GREEN);
		b.setFooter("@SirMangler - Bot Developer", "https://cdn-images-1.medium.com/max/1440/1*nv_LrLfy5B6suWwmbSQw3w.jpeg");
		
		channel.sendMessage(b.build()).queue();
		
		return false;
	}

	@Override
	public String getName() {
		return "help";
	}

	@Override
	public boolean isAdmin() {
		return false;
	}

}
