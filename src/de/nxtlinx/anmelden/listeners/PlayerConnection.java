package de.nxtlinx.anmelden.listeners;

import de.nxtlinx.anmelden.Anmelden;
import de.nxtlinx.anmelden.mysql.Stats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerConnection implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (Anmelden.getPlugin().getMySQL().isConnected()) {
            if (!Stats.playerExists(uuid)) {
                player.sendMessage(Anmelden.PREFIX + "§cDu hast noch kein Web-Account. Mach §6/anmelden");
                Stats.createPlayerSave(uuid,player.getName(),0,"Admin","12345",player.getName());
                player.sendMessage("§cPasswpord: §6"+Stats.getPasswordSave(uuid));
            }
        } else {
            player.sendMessage(Anmelden.PREFIX+"§cAktuell ist keine MySQL verbunden.");
        }
    }
}
