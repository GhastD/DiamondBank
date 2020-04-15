package cc.ghast.diamondbank.managers;

import cc.ghast.diamondbank.DiamondBank;
import cc.ghast.diamondbank.api.manager.Manager;
import cc.ghast.diamondbank.utils.Configuration;

/**
 * @author Ghast
 * @since 14-Apr-20
 */
public class ConfigManager implements Manager {

    private static Configuration config;
    private DiamondBank plugin;

    public ConfigManager(DiamondBank plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {
        config = new Configuration("config.yml", plugin);
    }

    @Override
    public void disinit() {
        config = null;
    }
}
