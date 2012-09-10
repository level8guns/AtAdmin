package to.uk.Skynykx;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class AtAdmin extends JavaPlugin implements Listener {
	
	private static ChatColor atPrefixColour = ChatColor.DARK_GREEN;
	private static ChatColor hashPrefixColour = ChatColor.GOLD;
	private static ChatColor messageColour = ChatColor.WHITE;
	private static ChatColor userColour = ChatColor.DARK_AQUA;
	private static ChatColor br = ChatColor.DARK_GRAY;
	

	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onServerCommand(ServerCommandEvent event) {
		CommandSender server = event.getSender();
		String message = event.getCommand();
		
		if (message.startsWith("@")) {
			event.setCommand("at");
			//String msg = message.replace("@", "");
			String msg = message.replaceFirst("@", "");
			msg = ChatColor.translateAlternateColorCodes('&', msg);
			String m = br + "[" + atPrefixColour + "@" + br + "]" + userColour + "" + ChatColor.ITALIC + "Console" + ChatColor.RESET + "" + messageColour + ": " + msg;
			
			server.sendMessage(m);
			
			for (Player p : getServer().getOnlinePlayers()) {
				if (p.hasPermission("atadmin.admin")) {
					p.sendMessage(m);
				}
			}
		}
	}
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		
		if (message.startsWith("@")) {
			if (player.hasPermission("atadmin.admin")) {
				event.setCancelled(true);
				if (message.length() > 1) {
				
					//String msg = message.replace("@", "");
					String msg = message.replaceFirst("@", "");
					msg = ChatColor.translateAlternateColorCodes('&', msg);
				
					String m = br + "[" + atPrefixColour + "@" + br + "]" + userColour + player.getName() + messageColour + ": " + msg;
				
					getServer().getConsoleSender().sendMessage(m);
					for (Player p : getServer().getOnlinePlayers()) {
						if (p.hasPermission("atadmin.admin")) {
							p.sendMessage(m);
						}
					}
				} else {
					player.sendMessage(ChatColor.RED + "Please specify a message.");
				}
			}
		}
		
		if (message.startsWith("#")) {
			if (player.hasPermission("atadmin.user")) {
				event.setCancelled(true);
				if (message.length() > 1) {
				
					//String msg = message.replace("#", "");
					String msg = message.replaceFirst("#", "");
					msg = ChatColor.translateAlternateColorCodes('&', msg);
				
					String m = br + "[" + hashPrefixColour + "#" + br + "]" + userColour + player.getName() + messageColour + ": " + msg;
					
					getServer().getConsoleSender().sendMessage(m);
					player.sendMessage(m);
					for (Player p : getServer().getOnlinePlayers()) {
						if (p.hasPermission("atadmin.admin")) {
							if (p.getName() != player.getName()) {
								p.sendMessage(m);
							}
						}
					}
				} else {
					player.sendMessage(ChatColor.RED + "Please specify a message to be sent to the server admins.");
				}
			}
		}
	}
	
	public void onDisable() {
		
	}
}
