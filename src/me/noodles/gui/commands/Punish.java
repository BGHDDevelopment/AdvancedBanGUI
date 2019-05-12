package me.noodles.gui.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import me.noodles.gui.inv.InvCreator;
import me.noodles.gui.inv.InvNames;
import me.noodles.gui.inv.Items;
import me.noodles.gui.main.Main;

public class Punish implements Listener, CommandExecutor {

	public static Player bannedPlayer;


	public Punish() {
		this.bannedPlayer = null;
	}


	public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
		if (!(sender instanceof Player)) {
			Bukkit.getServer().getLogger().warning(Main.plugin.getConfig().getString("NoPlayer"));
			return true;
		}
		final Player p = (Player) sender;
		if (!cmd.getName().equalsIgnoreCase("punish")) {
			return true;
		}
		if (args.length < 1) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("NoMessage")));
			return true;
		}
		if (args.length == 1) {
			bannedPlayer = Bukkit.getPlayer(args[0]);
		}
		if (!sender.hasPermission("punish.use")) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("NoPermission")));
			return true;
		}
		if (args.length > 1) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("NoMessage")));
			return true;
		}
		if (bannedPlayer == null) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("OfflinePlayer")));
			return true;
		}
		InvCreator.Main.setItem(Main.plugin.getguiitems1Config().getInt("ChatOffensesLocation"), Items.ChatOffences(p));
		InvCreator.Main.setItem(Main.plugin.getguiitems1Config().getInt("GeneralOffensesLocation"), Items.GeneralOffences(p));
		InvCreator.Main.setItem(Main.plugin.getguiitems1Config().getInt("ClientModOffensesLocation"), Items.ClientModOffences(p));
		InvCreator.Main.setItem(Main.plugin.getguiitems1Config().getInt("Severity1MuteLocation"), Items.Severity1Mute(p));
		InvCreator.Main.setItem(Main.plugin.getguiitems1Config().getInt("Severity1GeneralBanLocation"), Items.Severity1GeneralBan(p));
		InvCreator.Main.setItem(Main.plugin.getguiitems1Config().getInt("Severity1ClientBanLocation"), Items.Severity1ClientBan(p));
		InvCreator.Main.setItem(Main.plugin.getguiitems1Config().getInt("PermanentMuteLocation"), Items.PermMute(p));
		InvCreator.Main.setItem(Main.plugin.getguiitems1Config().getInt("Severity2MuteLocation"), Items.Severity2Mute(p));
		InvCreator.Main.setItem(Main.plugin.getguiitems1Config().getInt("Severity2ClientBanLocation"), Items.Severity2ClientBan(p));
		InvCreator.Main.setItem(Main.plugin.getguiitems1Config().getInt("PermanentBanLocation"), Items.PermBan(p));
		InvCreator.Main.setItem(Main.plugin.getguiitems1Config().getInt("Severity3MuteLocation"), Items.Severity3Mute(p));
		InvCreator.Main.setItem(Main.plugin.getguiitems1Config().getInt("Severity3ClientBanLocation"), Items.Severity3ClientBan(p));
		InvCreator.Main.setItem(Main.plugin.getguiitems1Config().getInt("WarningLocation"), Items.Warning(p));
		for (int i = 0; i < 54; ++i) {
			if (InvCreator.Main.getItem(i) == null) {
				InvCreator.Main.setItem(i, Items.Glass(p));
			}
		}
		p.openInventory(InvCreator.Main);
		return true;
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getView().getTitle().equals(null)) {
			return;
		}
		if (e.getView().getTitle().equals(InvNames.Main)) {
			e.setCancelled(true);
		} else {
			e.setCancelled(false);
		}
		if (e.getView().getTitle().equals(InvNames.Main)) {
			if (e.getCurrentItem().equals(Items.PermMute(p))) {
				p.chat("/mute " + bannedPlayer.getName() + " -s" + " " + Main.plugin.getbanreason1Config().getString("PermMuteReason"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Prefix") + Main.plugin.getbanreason1Config().getString("PermMuteMessage").replace("%player%", bannedPlayer.getName())));
				p.closeInventory();

			}
			if (e.getCurrentItem().equals(Items.Severity1Mute(p))) {
				p.chat("/tempmute " + bannedPlayer.getName() + " " + Main.plugin.getbanreason1Config().getString("Severity1MuteTime") + " -s" + " " + Main.plugin.getbanreason1Config().getString("Severity1MuteReason"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Prefix") + Main.plugin.getbanreason1Config().getString("Severity1MuteMessage").replace("%player%", bannedPlayer.getName())));
				p.closeInventory();

			}
			if (e.getCurrentItem().equals(Items.Severity1GeneralBan(p))) {
				p.chat("/tempban " + bannedPlayer.getName() + " " + Main.plugin.getbanreason1Config().getString("Severity1GeneralBanTime") + " -s" + " " + Main.plugin.getbanreason1Config().getString("Severity1GeneralBanReason"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Prefix") + Main.plugin.getbanreason1Config().getString("Severity1GeneralBanMessage").replace("%player%", bannedPlayer.getName())));
				p.closeInventory();

			}
			if (e.getCurrentItem().equals(Items.Severity1ClientBan(p))) {
				p.chat("/tempban " + bannedPlayer.getName() + " " + Main.plugin.getbanreason1Config().getString("Severity1ClientBanTime") + " -s" + " " + Main.plugin.getbanreason1Config().getString("Severity1ClientBanReason"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Prefix") + Main.plugin.getbanreason1Config().getString("Severity1ClientBanMessage").replace("%player%", bannedPlayer.getName())));
				p.closeInventory();

			}
			if (e.getCurrentItem().equals(Items.Severity2Mute(p))) {
				p.chat("/tempmute " + bannedPlayer.getName() + " " + Main.plugin.getbanreason1Config().getString("Severity2MuteTime") + " -s" + " " + Main.plugin.getbanreason1Config().getString("Severity2MuteReason"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Prefix") + Main.plugin.getbanreason1Config().getString("Severity2MuteMessage").replace("%player%", bannedPlayer.getName())));
				p.closeInventory();

			}
			if (e.getCurrentItem().equals(Items.Severity3Mute(p))) {
				p.chat("/tempmute " + bannedPlayer.getName() + " " + Main.plugin.getbanreason1Config().getString("Severity3MuteTime") + " -s" + " " + Main.plugin.getbanreason1Config().getString("Severity3MuteReason"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Prefix") + Main.plugin.getbanreason1Config().getString("Severity3MuteMessage").replace("%player%", bannedPlayer.getName())));
				p.closeInventory();

			}
			if (e.getCurrentItem().equals(Items.Severity2ClientBan(p))) {
				p.chat("/tempban " + bannedPlayer.getName() + " " + Main.plugin.getbanreason1Config().getString("Severity2ClientBanTime") + " -s" + " " + Main.plugin.getbanreason1Config().getString("Severity2ClientBanReason"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Prefix") + Main.plugin.getbanreason1Config().getString("Severity2ClientBanMessage").replace("%player%", bannedPlayer.getName())));
				p.closeInventory();

			}
			if (e.getCurrentItem().equals(Items.Severity3ClientBan(p))) {
				p.chat("/tempban " + bannedPlayer.getName() + " " + Main.plugin.getbanreason1Config().getString("Severity3ClientBanTime") + " -s" + " " + Main.plugin.getbanreason1Config().getString("Severity3ClientBanReason"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Prefix") + Main.plugin.getbanreason1Config().getString("Severity3ClientBanMessage").replace("%player%", bannedPlayer.getName())));
				p.closeInventory();

			}
			if (e.getCurrentItem().equals(Items.PermBan(p))) {
				p.chat("/ban " + bannedPlayer.getName() + " -s" + " " + Main.plugin.getbanreason1Config().getString("PermBanReason"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Prefix") + Main.plugin.getbanreason1Config().getString("PermBanMessage").replace("%player%", bannedPlayer.getName())));
				p.closeInventory();
			}
			if (e.getCurrentItem().equals(Items.Warning(p))) {
				p.chat("/warn " + bannedPlayer.getName() + " -s" + " " + Main.plugin.getbanreason1Config().getString("WarnReason"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Prefix") + Main.plugin.getbanreason1Config().getString("WarnMessage").replace("%player%", bannedPlayer.getName())));
				p.closeInventory();
			}
		}
	}
}



