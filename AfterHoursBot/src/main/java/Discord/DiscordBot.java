package Discord;

import java.util.logging.Logger;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;

/**
 * @author SirMangler
 *
 * @date 30 Mar 2019
 */
public class DiscordBot implements Runnable {

	public static JDA jda;
	final private static Logger log = Logger.getLogger("DiscordBot");
	public static String commandPrefix = ",";
	public static Guild guild;
	public static InfoEmbed infoembed = new InfoEmbed();

	public static Clock clock = new Clock();
	
	public void startJDA() {
		log.info("Connecting to Discord");
		
		try {
			jda = new JDABuilder(AccountType.BOT).setToken(System.getenv("bot-token")).buildBlocking();
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		log.info("Adding Listener");
		
		jda.getPresence().setGame(Game.streaming(",help", "https://www.twitch.tv/gpafterhours"));
		
		jda.addEventListener(new DiscordListener());
		guild = jda.getGuildById("402992121419792404");
		clock.run();
	}
	
	public void run() {
		startJDA();
	}
}
