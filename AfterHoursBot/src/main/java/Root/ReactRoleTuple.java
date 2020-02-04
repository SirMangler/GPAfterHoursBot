package Root;

/**
 * @author SirMangler
 *
 * @date 3 Apr 2019
 */
public class ReactRoleTuple {

	public String channelID;
	public String messageID;
	public String roleID;
	
	public ReactRoleTuple(String channelID, String messageID, String roleID) {
		this.channelID=channelID;
		this.messageID=messageID;
		this.roleID=roleID;
	}
}
