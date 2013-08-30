package net.mctower.filecontrol;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class FileControl {

	static HashMap<YamlConfiguration, File> Files = new HashMap<YamlConfiguration, File>();
	
	
	private FileControl(){
	}

	static FileControl instance = new FileControl();

	public static FileControl getInstance() {
		return instance;
	}

	Plugin p;

	public static YamlConfiguration ChatFilterYaml;
	public static File ChatFilterFile;

	public static void FileControlsetup(Plugin p) {
		// config.options().copyDefaults(true);
		// saveConfig();
		
		/*if (!p.getDataFolder().exists()) {
			p.getDataFolder().mkdir();
		}*/
		ChatFilterFile = new File(p.getDataFolder(), "ChatFilter.yml");
		ChatFilterYaml = YamlConfiguration.loadConfiguration(ChatFilterFile);
		Files.put(ChatFilterYaml, ChatFilterFile);
		if (!ChatFilterFile.exists()) {
			try {
				ChatFilterFile.createNewFile();
			} catch (IOException e) {
				Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create chatFilter.yml!");
			}
		}
		
	}

	public FileConfiguration getData(YamlConfiguration data) {
		return data;
	}

	public void saveData(YamlConfiguration data) {
		try {
			data.save(Files.get(data));
		} catch (IOException e) {
			Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save "+data.getCurrentPath()+"!");
		}
	}

	public void reloadData(YamlConfiguration data) {
		data = YamlConfiguration.loadConfiguration(Files.get(data));
	}

	public static FileConfiguration getConfigFile(YamlConfiguration data) {
		return data;
	}

	public void saveConfig(YamlConfiguration data) {
		try {
			data.save(Files.get(data));
		} catch (IOException e) {
			Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save "+data.getCurrentPath()+"!");
		}
	}

	public void reloadConfig(YamlConfiguration data) {
		YamlConfiguration.loadConfiguration(Files.get(data));
	}

}
