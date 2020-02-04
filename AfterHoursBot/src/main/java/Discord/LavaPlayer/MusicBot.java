package Discord.LavaPlayer;

import java.util.HashMap;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import Discord.DiscordBot;
import Root.Root;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;

/**
 * @author SirMangler
 *
 * @date 31 Mar 2019
 */
public class MusicBot {
	
	private AudioPlayerManager playerManager;
	private Map<Long, GuildMusicManager> musicManagers;

	public MusicBot() {
		Thread t = new Thread(() -> run());
		t.start();
	}
	
	public void run() {
		this.musicManagers = new HashMap<>();

		this.playerManager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerRemoteSources(playerManager);
		AudioSourceManagers.registerLocalSource(playerManager);
	}

	private synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
		long guildId = Long.parseLong(guild.getId());
		GuildMusicManager musicManager = musicManagers.get(guildId);

		if (musicManager == null) {
			musicManager = new GuildMusicManager(playerManager);
			musicManagers.put(guildId, musicManager);
		}

		guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

		return musicManager;
	}

//	@Override
//	public void onMessageReceived(MessageReceivedEvent event) {
//		String[] command = event.getMessage().getContentRaw().split(" ", 2);
//		Guild guild = event.getGuild();
//
//		if (guild != null) {
//			if ("~play".equals(command[0]) && command.length == 2) {
//				loadAndPlay(event.getTextChannel(), command[1]);
//			} else if ("~skip".equals(command[0])) {
//				skipTrack(event.getTextChannel());
//			}
//		}
//
//		super.onMessageReceived(event);
//	}

	public void loadAndPlay(final TextChannel channel, final String trackUrl) {
		final GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

		playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
			@Override
			public void trackLoaded(AudioTrack track) {
				channel.sendMessage("Adding to queue " + track.getInfo().title).queue();

				play(channel.getGuild(), musicManager, track);
			}

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				AudioTrack firstTrack = playlist.getSelectedTrack();

				if (firstTrack == null) {
					firstTrack = playlist.getTracks().get(0);
				}

				channel.sendMessage("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();

				play(channel.getGuild(), musicManager, firstTrack);
			}

			@Override
			public void noMatches() {
				channel.sendMessage("Nothing found by " + trackUrl).queue();
			}

			@Override
			public void loadFailed(FriendlyException exception) {
				channel.sendMessage("Could not play: " + exception.getMessage()).queue();
			}
		});
	}

	private void play(Guild guild, GuildMusicManager musicManager, AudioTrack track) {
		connectToMusicChannel(guild.getAudioManager());

		musicManager.scheduler.queue(track);
	}

	public void skipTrack(TextChannel channel) {
		GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
		musicManager.scheduler.nextTrack();

		channel.sendMessage("Skipped to next track.").queue();
	}

	private static void connectToMusicChannel(AudioManager audioManager) {
		if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
			VoiceChannel c = DiscordBot.jda.getVoiceChannelById(Root.musicChannel);
			if (c == null) {
				System.out.println("Music ERR Channel doesn't exist");
			} else {
				audioManager.openAudioConnection(c);
			}
		}
	}
}
