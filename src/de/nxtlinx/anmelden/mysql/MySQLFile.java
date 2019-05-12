package de.nxtlinx.anmelden.mysql;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MySQLFile {

    private File file = new File("plugins/MySQL/Anmelden","mysql.yml");
    private FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    private String user,password,host,database;

    public MySQLFile(){
        if (!file.exists()){
            setDefault();
            loadInfos();
        } else {
            loadInfos();
        }
    }

    private void setDefault(){
        cfg.addDefault("MySQL.Host","localhost");
        cfg.addDefault("MySQL.User","root");
        cfg.addDefault("MySQL.Password"," ");
        cfg.addDefault("MySQL.Database","stats");

        cfg.options().copyDefaults(true);

        try {
            cfg.save(file);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("§cEs gab ein Fehler bei der Default config!");
        }

    }

    private void loadInfos(){
        user = cfg.getString("MySQL.User");
        password = cfg.getString("MySQL.Password");
        host = cfg.getString("MySQL.Host");
        database = cfg.getString("MySQL.Database");
    }

    public void saveConfig(){
        try {
            cfg.save(file);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("§cEs gab ein Fehler der Speicherung der Config!");
        }
    }


    public File getFile() {
        return file;
    }

    public FileConfiguration getCFG() {
        return cfg;
    }

    public String getDatabase() {
        return database;
    }

    public String getHost() {
        return host;
    }

    public String getPassword() {
        return password;
    }

    public String getUser() {
        return user;
    }
}
