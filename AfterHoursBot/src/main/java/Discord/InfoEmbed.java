package Discord;

import java.util.Map.Entry;

import Root.Root;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * @author SirMangler
 *
 * @date 31 Mar 2019
 */
public class InfoEmbed {
	
	public static Message embed;
	
	EmbedBuilder builder = new EmbedBuilder();
	public MessageEmbed buildEmbed() {
		builder.clear();
		
		//System.out.println("setting description");
		//builder.setDescription("Check out our [latest video here](https://ltstyt.be/3Cc)!");
		//builder.addField("** **", "[Latest Video](https://ltstyt.be/3Cc)", true);
		
		//System.out.println("setting rules");
		builder.addField("Rules", Root.rules.toString()+"\r\n", false);
		
		//System.out.println("role line");
		String roleline = "This discord gives roles to active dedicated casuals ecks dee\n\n"+
				"Level #1 - <@&556946282824990731> \r\n" + 
				"Level #3 - <@&556946769792204814> \r\n" + 
				"Level #5 - <@&556948062497407016> \r\n" + 
				"Level #10 - <@&556946870249848844> \r\n" + 
				"Level #17 - <@&556946702742061107> \r\n"+
				"\nIn the case of a discord server emergency, do your best to not panic, and call @reno911 if your feelings get hurt from a furry porn raid";
		
		builder.addField("Roles", roleline, false);
		
		String medialinkline = "";
		for (Entry<String, String> medialinks : Root.medialinks.entrySet()) {
			medialinkline += "["+medialinks.getKey()+"]("+medialinks.getValue()+") ";
		}
		builder.addField("Follow us everywhere!", medialinkline+"\nCheck out our [latest video here](https://ltstyt.be/3Cc)!", false);
		
		builder.setTitle("Welcome to Geeks Peak After Hours");
		builder.setThumbnail("https://cdn.discordapp.com/attachments/554750421445378054/562367098605666310/FaZeClAn.png");
		builder.setColor(8388863);
		
		builder.setFooter("@SirMangler - Bot Developer", "https://cdn.discordapp.com/emojis/401510348294848524.gif?v=1");
		
		//System.out.println(builder.length());
		
		class buildThread {
			public MessageEmbed get() {
				return builder.build();
			}
		};
		
		System.out.println(builder.build().getLength());
		return new buildThread().get();
	}
	
	public static void updateEmbed() {
		if (embed == null) {
			if (Root.infoChannelId != null) {
				TextChannel c = DiscordBot.guild.getTextChannelById(Root.infoChannelId);
				embed = c.getMessageById(Root.infoEmbedId).complete();
			} else {
				System.out.println("ERR InfoChannelID is null!!");
			}
		}
		
		embed.editMessage(DiscordBot.infoembed.buildEmbed()).queue();
	}
}
