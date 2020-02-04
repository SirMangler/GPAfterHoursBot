package Discord;

import java.util.Arrays;

import Discord.Commands.Command;
import Discord.Commands.Ping;
import Discord.Commands.addToNameQueue;
import Discord.Commands.help;
import Discord.Commands.listCommands;
import Discord.Commands.listNames;
import Discord.Commands.reloadConfig;
import Discord.Commands.removeNameFromQueue;
import Discord.Commands.setMusicChannel;
import Discord.Commands.setName;
import Discord.Commands.Embeds.addEmbedMediaLink;
import Discord.Commands.Embeds.addEmbedRole;
import Discord.Commands.Embeds.createInfoEmbed;
import Discord.Commands.Embeds.quickEmbed;
import Discord.Commands.Embeds.removeEmbedMediaLink;
import Discord.Commands.Embeds.removeEmbedRole;
import Discord.Commands.Embeds.updateEmbed;
import Discord.Commands.Embeds.updateRules;
import Discord.LavaPlayer.MusicBot;
import Root.ReactRoleTuple;
import Root.Root;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageReaction;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * @author SirMangler
 *
 * @date 30 Mar 2019
 */
public class DiscordListener extends ListenerAdapter {

	//final private static Logger log = Logger.getLogger("EventListener");
	MusicBot mb;

	Command[] commands = new Command[] {
			new addToNameQueue(), 
			new removeNameFromQueue(), 
			new setName(), 
			new listNames(), 
			new addEmbedMediaLink(), 
			new removeEmbedMediaLink(), 
			new updateRules(), 
			new createInfoEmbed(),
			new addEmbedRole(),
			new removeEmbedRole(),
			new listCommands(),
			new setMusicChannel(),
			new Ping(),
			new updateEmbed(),
			new reloadConfig(),
			new quickEmbed(),
			new help()
	};
	
	public DiscordListener() {
		mb = new MusicBot();
	}
	
	Role staffrole = DiscordBot.jda.getRoleById("503645234882805770");
	
	@Override
	public void onMessageReceived(final MessageReceivedEvent e) {
		String content = e.getMessage().getContentRaw();
		
		if (e.getAuthor().isBot())
			return;
		
		
		if (e.getMessage().getContentRaw().startsWith(DiscordBot.commandPrefix)) {
			for (Command command : commands) {
				if (command.isAdmin()) {
					if (e.getMember().getRoles().contains(staffrole) || e.getAuthor().getId().equalsIgnoreCase("167445584142139394")) {
						if (command.tryCommand(content, e.getTextChannel())) {
							break;
						}
					}
				} else {
					if (command.tryCommand(content, e.getTextChannel())) {
						break;
					}
				}
			}
			
			String[] command = e.getMessage().getContentRaw().split(" ", 2);
			if (command[0].replace(DiscordBot.commandPrefix, "").equalsIgnoreCase("play")) {
				mb.loadAndPlay(e.getTextChannel(), command[1]);
			} else if (command[0].replace(DiscordBot.commandPrefix, "").equalsIgnoreCase("skip")) {
				mb.skipTrack(e.getTextChannel());
			} else if (command[0].replace(DiscordBot.commandPrefix, "").equalsIgnoreCase("addreactrole")) {
				String[] tokens = content.split(" ");
				String[] arguments = Arrays.copyOfRange(tokens, 1, tokens.length);
				
				if (arguments.length < 4) {
					e.getChannel().sendMessage("Insufficient Arguments. Usage: addReactRole #CHANNEL MSG_ID :EMOTE: @ROLE").queue();
					return;
				}
				
				TextChannel c = e.getMessage().getMentionedChannels().get(0);
				if (c == null) {
					e.getChannel().sendMessage("Channel required. Usage: addReactRole #CHANNEL MSG_ID :EMOTE: @ROLE").queue();
					return;
				}
				
				Message m = c.getMessageById(arguments[1]).complete();
				if (m == null) {
					e.getChannel().sendMessage("Cannot obtain message. Usage: addReactRole #CHANNEL MSG_ID :EMOTE: @ROLE").queue();
					return;
				}
				
				String unicodeemote =  arguments[2];			

				Role r = e.getMessage().getMentionedRoles().get(0);
				if (r == null) {
					e.getChannel().sendMessage("Cannot obtain role. Usage: addReactRole #CHANNEL MSG_ID :EMOTE: @ROLE").queue();
					return;
				}
				
				Root.addReactRole(unicodeemote, new ReactRoleTuple(c.getId(), m.getId(), r.getId()));
				
				m.addReaction(unicodeemote).complete();
				
				e.getChannel().sendMessage("Created Reaction-Role with "+unicodeemote+" for "+r.getAsMention()).complete();
			} else if (command[0].replace(DiscordBot.commandPrefix, "").equalsIgnoreCase("removereactrole")) {
				String[] tokens = content.split(" ");
				String[] arguments = Arrays.copyOfRange(tokens, 1, tokens.length);
				
				if (arguments.length < 3) {
					e.getChannel().sendMessage("Insufficient Arguments. Usage: removeReactRole #CHANNEL MSG_ID :EMOTE:").queue();
					return;
				}
				
				TextChannel c = e.getMessage().getMentionedChannels().get(0);
				if (c == null) {
					e.getChannel().sendMessage("Channel required. Usage: removeReactRole #CHANNEL MSG_ID :EMOTE:").queue();
					return;
				}
				
				Message m = c.getMessageById(arguments[1]).complete();
				if (m == null) {
					e.getChannel().sendMessage("Cannot obtain message. Usage: removeReactRole #CHANNEL MSG_ID :EMOTE:").queue();
					return;
				}
				
				String unicodeemote = arguments[2];					
				
				Root.removeReactRole(unicodeemote);
				
				for (MessageReaction re : m.getReactions()) {
					if (re.getReactionEmote().getName().equalsIgnoreCase(unicodeemote)) {
						re.removeReaction();
						
						e.getChannel().sendMessage("Removed Reaction-Role: "+unicodeemote).complete();
						return;
					}
				}
				
				e.getChannel().sendMessage("Cannot find emote: "+unicodeemote).queue();
			}
		}
	}
	
	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent e) {
		if (e.getMember().equals(e.getGuild().getSelfMember()))
			return;
		
		Root.reactroles.forEach((emote, tuple) -> {
			if (e.getReactionEmote().getName().equalsIgnoreCase(emote)) {
				if (e.getChannel().getId().equalsIgnoreCase(tuple.channelID)) {
					if (e.getMessageId().equalsIgnoreCase(tuple.messageID)) {
						e.getGuild().getController().addRolesToMember(e.getMember(), DiscordBot.guild.getRoleById(tuple.roleID)).complete();
						return;
					}
				}
				return;
			}
		});
	}
	
	@Override
	public void onMessageReactionRemove(MessageReactionRemoveEvent e) {
		if (e.getMember().equals(e.getGuild().getSelfMember()))
			return;
		
		Root.reactroles.forEach((emote, tuple) -> {
			if (e.getReactionEmote().getName().equalsIgnoreCase(emote)) {
				if (e.getChannel().getId().equalsIgnoreCase(tuple.channelID)) {
					if (e.getMessageId().equalsIgnoreCase(tuple.messageID)) {
						e.getGuild().getController().removeRolesFromMember(e.getMember(), DiscordBot.guild.getRoleById(tuple.roleID)).complete();
						return;
					}
				}
				return;
			}
		});
	}
}
