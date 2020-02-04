package Discord.Commands;

import net.dv8tion.jda.core.entities.TextChannel;

/**
 * @author SirMangler
 *
 * @date 31 Mar 2019
 */
public class Ping extends Command {

	@Override
	public boolean execute(String[] arguments, TextChannel channel) {
		long time = System.currentTimeMillis();
		
		if (arguments.length > 0) {
			System.out.println("<< PING "+String.join(" ", arguments));
			channel.sendMessage(String.join(" ", arguments)).queue();
		} else {
			channel.sendMessage("Pong!").queue(success -> {
				long timetaken = System.currentTimeMillis() - time;
				success.editMessage("Pong! ("+timetaken+"ms)").complete();
			});
		}

		return true;
	}

	@Override
	public String getName() {
		return "ping";
	}

	@Override
	public boolean isAdmin() {
		return false;
	}

}
