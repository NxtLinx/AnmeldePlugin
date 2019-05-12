package de.nxtlinx.anmelden;

import de.nxtlinx.anmelden.listeners.PlayerConnection;
import de.nxtlinx.anmelden.mysql.MySQL;
import de.nxtlinx.anmelden.mysql.MySQLFile;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Anmelden extends JavaPlugin {

    public static final String PREFIX ="§3Web-Account§7| §r";

    private static Anmelden plugin;
    private MySQL mySQL;
    private MySQLFile mySQLFile;

    @Override
    public void onEnable() {
        plugin = this;
        mySQLFile = new MySQLFile();
        mySQL = new MySQL();

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerConnection(),this);

    }

    @Override
    public void onDisable() {
        if (mySQL.isConnected()){
            mySQL.disconnect();
        }
    }

    public static Anmelden getPlugin() {
        return plugin;
    }

    public MySQL getMySQL() {
        return mySQL;
    }

    public MySQLFile getMySQLFile() {
        return mySQLFile;
    }
}
