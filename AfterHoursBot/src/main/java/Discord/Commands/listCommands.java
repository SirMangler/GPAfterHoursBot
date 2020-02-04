package Discord.Commands;

import java.awt.Color;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * @author SirMangler
 *
 * @date 31 Mar 2019
 */
public class listCommands extends Command {

	@Override
	public boolean execute(String[] arguments, TextChannel channel) {
		EmbedBuilder b = new EmbedBuilder();
		b.setTitle("List of Commands");
		b.setDescription(
				"```http\nlistNames - Lists the names the bot uses.\n\n"+
				"toggleClock - Toggles the timer that changes the bot name/icon and server icon.\n\n"+
				"setName [name] - Set's the bot's name.\n\n"+
				"addName [name] - Adds a name for the bot to use.\n\n"+
				"removeName [name] - Removes a name that the bot can use.\n\n"+
				"infoEmbed - Creates the editable Info Embed.\n\n"+
				"updateRules [rules] - Changes the rules on the info embed.\n\n"+
				"addMediaLink [emote] [link] - Adds a media link using an emote to the info embed.\n\n"+
				"removeMediaLink [emote] - Removes the emote and link from the info embed.\n\n"+
				"addRole @[role] [description] - Adds a role and it's description to the info embed.\n\n"+
				"removeRole @[role] - Removes a role and it's description from the info embed.\n\n"+
				"play [link] - Plays the link (e.g youtube or spotify) in the music channel.\n\n"+
				"skip - Skips the playing song.\n\n"+
				"ping - Tests response time.\n\n"+
				"setMusicChannel - Sets the channel that the bot joins for music.\n\n"+
				"reloadConfig - Reloads the config. [Only really needed for DEV]\n\n"+
				"quickEmbed [description] - Quickly makes an embed with the description.```"
				);
		
		b.setColor(Color.CYAN);
		b.setFooter("@SirMangler - Bot Developer", "https://cdn-images-1.medium.com/max/1440/1*nv_LrLfy5B6suWwmbSQw3w.jpeg");
		
		channel.sendMessage(b.build()).queue();
		
		return true;
	}

	@Override
	public String getName() {
		return "listcommands";
	}

	@Override
	public boolean isAdmin() {
		return true;
	}

}
