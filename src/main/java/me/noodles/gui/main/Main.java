package me.noodles.gui.main;

import java.io.File;
import java.io.IOException;

import me.noodles.gui.commands.AdvancedBanGUICommand;
import me.noodles.gui.commands.AdvancedBanGUIReloadCommand;
import me.noodles.gui.utils.MetricsLite;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import me.noodles.gui.commands.Punish;
import me.noodles.gui.utils.updatechecker.JoinExample;
import me.noodles.gui.utils.updatechecker.UpdateChecker;

public class Main extends JavaPlugin
{
	private UpdateChecker checker;
    public static Main plugin;

    public void onEnable() {
    	PluginDescriptionFile VarUtilType = this.getDescription();
		this.getLogger().info("AdvancedBanGUI V" + VarUtilType.getVersion() + " starting...");
		this.getLogger().info("AdvancedBanGUI V" + VarUtilType.getVersion() + " loading commands and config files...");
        this.createFiles();
        this.registerEvents();
        this.registerCommands();
        plugin = this;
        MetricsLite metrics = new MetricsLite(this);
        this.setEnabled(true);
		getLogger().info("AdvancedBanGUI V" + VarUtilType.getVersion() + " started!");
		this.getLogger().info("AdvancedBanGUI V" + VarUtilType.getVersion() + " checking for updates...");
        this.checker = new UpdateChecker(this);
		if (this.checker.isConnected()) {
			if (this.checker.hasUpdate()) {
				getServer().getConsoleSender().sendMessage("------------------------");
				getServer().getConsoleSender().sendMessage("AdvancedBanGUI is outdated!");
				getServer().getConsoleSender().sendMessage("Newest version: " + this.checker.getLatestVersion());
				getServer().getConsoleSender().sendMessage("Your version: " + Main.plugin.getDescription().getVersion());
				getServer().getConsoleSender().sendMessage("Please Update Here: https://www.spigotmc.org/resources/59570");
				getServer().getConsoleSender().sendMessage("------------------------");
			} else {
				getServer().getConsoleSender().sendMessage("------------------------");
				getServer().getConsoleSender().sendMessage("AdvancedBanGUI is up to date!");
				getServer().getConsoleSender().sendMessage("------------------------");
			}
		}
	}
    
    
    public void registerEvents() {
        final PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new Punish(), this);
        pm.registerEvents(new JoinExample(), this);
    }
    public void registerCommands() {
    	this.getCommand("punish").setExecutor(new Punish());
        this.getCommand("advancedbanguireload").setExecutor(new AdvancedBanGUIReloadCommand());
        this.getCommand("advancedbangui").setExecutor(new AdvancedBanGUICommand());

    }

    private File configf, guiitems, banreason, guicommands;
    private FileConfiguration config, guiitems1, banreason1, guicommands1;


    public FileConfiguration getguiitems1Config() {
        return this.guiitems1;
    }
    public FileConfiguration getbanreason1Config() {
        return this.banreason1;
    }
    public FileConfiguration getguicommands1Config() {
        return this.guicommands1;
    }


    public void reloadFiles() {
        config = YamlConfiguration.loadConfiguration(configf);
        guiitems1 = YamlConfiguration.loadConfiguration(guiitems);
        banreason1 = YamlConfiguration.loadConfiguration(banreason);
        guicommands1 = YamlConfiguration.loadConfiguration(guicommands);

    }

    
    private void createFiles() {
        configf = new File(getDataFolder(), "config.yml");
        guiitems = new File(getDataFolder(), "guiitems.yml");
        banreason = new File(getDataFolder(), "banreason.yml");
        guicommands = new File(getDataFolder(), "guicommands.yml");

        if (!configf.exists()) {
            configf.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }
        if (!guiitems.exists()) {
            guiitems.getParentFile().mkdirs();
            saveResource("guiitems.yml", false);
         }
        if (!banreason.exists()) {
            banreason.getParentFile().mkdirs();
            saveResource("banreason.yml", false);
         }
        if (!guicommands.exists()) {
            guicommands.getParentFile().mkdirs();
            saveResource("guicommands.yml", false);
        }

        config = new YamlConfiguration();
        guiitems1 = new YamlConfiguration();
        banreason1 = new YamlConfiguration();
        guicommands1 = new YamlConfiguration();

        try {
            config.load(configf);
            guiitems1.load(guiitems);
            banreason1.load(banreason);
            guicommands1.load(guicommands);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}