package cc.ghast.diamondbank.listeners;

import cc.ghast.diamondbank.DiamondBank;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Ghast
 * @since 14-Apr-20
 */
public class PlayerListener implements Listener {

    private final DiamondBank plugin;

    public PlayerListener(DiamondBank plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        plugin.getEconomyManager().cachePlayer(e.getPlayer());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        plugin.getEconomyManager().unCachePlayer(e.getPlayer());
    }
}
