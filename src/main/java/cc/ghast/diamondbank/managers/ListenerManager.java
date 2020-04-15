package cc.ghast.diamondbank.managers;

import cc.ghast.diamondbank.DiamondBank;
import cc.ghast.diamondbank.api.manager.Manager;
import cc.ghast.diamondbank.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

/**
 * @author Ghast
 * @since 14-Apr-20
 */
public class ListenerManager implements Manager {
    private DiamondBank plugin;
    private PlayerListener listener;

    public ListenerManager(DiamondBank plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {
        this.listener = new PlayerListener(plugin);
        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }

    @Override
    public void disinit() {
        HandlerList.unregisterAll(listener);
        this.listener = null;
    }
}
