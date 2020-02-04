package Root;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.logging.Logger;

import Discord.DiscordBot;
import net.dv8tion.jda.core.entities.Icon;

/**
 * @author SirMangler
 *
 * @date 30 Mar 2019
 */
public class Root {
	
	public static Icon[] guildicons;
	public static Icon[] boticons;
	public static List<String> names = new ArrayList<String>();
	public static String infoEmbedId;
	public static String infoChannelId;
	public static String musicChannel;
	final private static Logger log = Logger.getLogger("ROOT");
	
	public static void main(String[] args) {		
		System.out.println("Loading data");
		loadData();
		loadConfig();
		
		System.out.println("Loading Bot");
		Runnable discord = new DiscordBot();
		discord.run();
	}
	
	static Random r = new Random();
	
	public static void addName(String name) {
		names.add(name);
		updateCfg();
	}
	
	public static void removeName(String name) {
		names.remove(name);
		updateCfg();
	}
	
	public static void addMediaLink(String emote, String link) {
		medialinks.put(emote, link);
		updateCfg();
	}
	
	public static void removeMediaLink(String emote) {
		medialinks.remove(emote);
		updateCfg();
	}
	
	public static void addRole(String role, String description) {
		roles.put(role, description);
		updateCfg();
	}
	
	public static void removeRole(String role) {
		roles.remove(role);
		updateCfg();
	}
	
	public static void updateRules(String newrules) {
		rules = new StringBuilder();
		rules.append(newrules);
		updateCfg();
	}
	
	public static void setinfoEmbedID(String id, String channelID) {
		infoEmbedId = id;
		infoChannelId = channelID;
		updateCfg();
	}
	
	public static void setMusicChannel(String channelID) {
		musicChannel = channelID;
		updateCfg();
	}
	
	public static void addReactRole(String emote, String channel, String message, String role) {
		reactroles.put(emote, new ReactRoleTuple(channel, message, role));
		updateCfg();
	}
	
	public static void addReactRole(String emote, ReactRoleTuple tuple) {
		reactroles.put(emote, tuple);
		updateCfg();
	}
	
	public static void removeReactRole(String emote) {
		reactroles.remove(emote);
		updateCfg();
	}
	
	public static void updateCfg() {
		FileWriter fw;
		try {
			log.info("Loading "+getFileFromResource("Afterhours.cfg").getPath());
			fw = new FileWriter(getFileFromResource("AfterHours.cfg"));
			
			fw.write("names-"+String.join(";", names)+"\n");
			
			fw.write("medialinks-\n");
			for (Entry<String, String> medialink : medialinks.entrySet()) {
				fw.write(medialink.getKey()+"@"+medialink.getValue()+"\n");
			}
			fw.write("-\n");
			
			fw.write("rules-\n");
			fw.write(rules.toString()+"\n");
			fw.write("-\n");
			
			fw.write("roles-\n");
			for (Entry<String, String> roles : roles.entrySet()) {
				fw.write(roles.getKey()+"@"+roles.getValue()+"\n");
			}
			fw.write("-\n");
			
			fw.write("infoembed-"+infoEmbedId+"\n");
			fw.write("infochannel-"+infoChannelId+"\n");
			fw.write("musicchannel-"+musicChannel+"\n");

			fw.write("reactroles-\n");
			for (Entry<String, ReactRoleTuple> reactroles : reactroles.entrySet()) {
				ReactRoleTuple tuple = reactroles.getValue();
				fw.write(reactroles.getKey()+"@"+tuple.channelID+"@"+tuple.messageID+"@"+tuple.roleID+"\n");
			}
			fw.write("-\n");
			
			fw.flush();
		} catch (IOException e) {
			log.warning(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static Icon getRandomGuildIcon() {
		return guildicons[r.nextInt(guildicons.length)];
	}
	
	public static Icon getRandomBotIcon() {
		return boticons[r.nextInt(boticons.length)];
	}
	
	public static String getRandomName() {
		return names.get(r.nextInt(names.size()));
	}
	
	static String jarlocation = ClassLoader.getSystemClassLoader().getResource(".").getPath();
	public static File getFileFromResource(String s) {
		System.out.println("Loaded ["+jarlocation+"/"+s+"] from resources.");
		return new File(jarlocation+"/"+s);
	}
	
	public static void loadData() {	
		File guild = getFileFromResource("guildicons");
		File[] files = guild.listFiles();
		
		guildicons = new Icon[files.length];
		
		for (int i = 0; i < guildicons.length; i++) {
			try {
				guildicons[i] = Icon.from(files[i]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		File bot = getFileFromResource("boticons");
		files = bot.listFiles();
		
		boticons = new Icon[files.length];
		
		for (int i = 0; i < boticons.length; i++) {
			try {
				boticons[i] = Icon.from(files[i]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static HashMap<String, String> medialinks = new HashMap<String, String>();
	public static HashMap<String, String> roles = new HashMap<String, String>();
	public static StringBuilder rules = new StringBuilder();
	public static HashMap<String, ReactRoleTuple> reactroles = new HashMap<String, ReactRoleTuple>();
	
	public static void loadConfig() {
		File f = getFileFromResource("AfterHours.cfg");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			
			boolean medialink = false;
			boolean rule = false;
			boolean role = false;
			boolean reactrole = false;
			
			medialinks = new HashMap<String, String>();
			roles = new HashMap<String, String>();
			names = new ArrayList<String>();
			reactroles =  new HashMap<String, ReactRoleTuple>();
			
			String line;
			while ((line = reader.readLine()) != null) {
				if (reactrole) {
					if (line.equalsIgnoreCase("-")) {
						medialink = false;
					} else {
						String[] split = line.split("@", 4);
						reactroles.put(split[0], new ReactRoleTuple(split[1], split[2], split[3]));
					}
				}
				
				if (medialink) {
					if (line.equalsIgnoreCase("-")) {
						medialink = false;
					} else {
						String[] split = line.split("@", 2);
						medialinks.put(split[0], split[1]);
					}
				}
				
				if (rule) {
					if (line.equalsIgnoreCase("-")) {
						rule = false;
					} else {
						rules.append(line+"\n");
					}
				}
				
				if (role) {
					if (line.equalsIgnoreCase("-")) {
						role = false;
					} else {
						String[] split = line.split("@", 2);
						roles.put(split[0], split[1]);
					}
				}
				
				if (line.startsWith("names")) {
					String n = line.replace("names-", "");
					String[] split = n.split(";");
					
					for (String name : split) {
						names.add(name);
					}
				} else if (line.startsWith("medialinks")) {
					medialink = true;
				} else if (line.startsWith("rules")) {
					rule = true;
				} else if (line.startsWith("infoembed")) {
					infoEmbedId = line.replace("infoembed-", "");
				} else if (line.startsWith("infochannel")) {
					infoChannelId = line.replace("infochannel-", "");
				} else if (line.startsWith("musicchannel")) {
					musicChannel = line.replace("musicchannel-", "");
				}
			}
			
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
