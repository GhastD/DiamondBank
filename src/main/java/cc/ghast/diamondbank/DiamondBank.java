package cc.ghast.diamondbank;

import cc.ghast.diamondbank.api.manager.Manager;
import cc.ghast.diamondbank.managers.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiamondBank extends JavaPlugin {

    private EconomyManager economyManager;
    private CommandManager commandManager;
    private GUIManager guiManager;
    private ConfigManager configManager;
    private ListenerManager listenerManager;
    private final List<Manager> managers = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.economyManager = new EconomyManager(this);
        this.commandManager = new CommandManager(this);
        this.guiManager = new GUIManager(this);
        this.configManager = new ConfigManager(this);
        this.listenerManager = new ListenerManager(this);
        this.managers.addAll(Arrays.asList(configManager, economyManager, commandManager, guiManager, listenerManager));
        this.managers.forEach(Manager::init);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.managers.forEach(Manager::disinit);
        this.managers.clear();
        this.economyManager = null;
        this.commandManager = null;
        this.guiManager = null;
        this.configManager = null;
        this.listenerManager = null;
    }

    public EconomyManager getEconomyManager() {
        return economyManager;
    }

}
