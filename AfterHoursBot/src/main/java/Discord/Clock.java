package Discord;

import java.util.logging.Logger;

import Root.Root;

/**
 * @author SirMangler
 *
 * @date 30 Mar 2019
 */
public class Clock implements Runnable {
	
	final private static Logger log = Logger.getLogger("Clock");
	boolean running = true;
	
	public void run() {
		log.info("Starting Clock");
		
		while (true) {
			if (!running) continue;
			
			DiscordBot.guild.getManager().setIcon(Root.getRandomGuildIcon()).complete();
			DiscordBot.jda.getSelfUser().getManager().setAvatar(Root.getRandomBotIcon()).complete();
			DiscordBot.guild.getController().setNickname(DiscordBot.guild.getSelfMember(), Root.getRandomName()).complete();
			
			try {
				Thread.sleep(1800000);
			} catch (InterruptedException e) {
				log.severe("Clock Interrupted");
				e.printStackTrace();
			}
		}
	}
	
	public boolean toggleRunning() {
		return running = !running;
	}
}
