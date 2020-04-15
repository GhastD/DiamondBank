package cc.ghast.diamondbank.managers;

import cc.ghast.diamondbank.DiamondBank;
import cc.ghast.diamondbank.api.manager.Manager;
import cc.ghast.diamondbank.commands.BankCommand;
import cc.ghast.diamondbank.commands.PayCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;

/**
 * @author Ghast
 * @since 14-Apr-20
 */
public class CommandManager implements Manager {
    private DiamondBank plugin;

    private BankCommand bankCommand;
    private PayCommand payCommand;

    public CommandManager(DiamondBank plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {
        this.bankCommand = new BankCommand(plugin);
        this.payCommand = new PayCommand(plugin);
        registerDynamicCommands();
    }

    @Override
    public void disinit() {

    }

    private void registerDynamicCommands(){

        try {
            if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
                Field f = SimplePluginManager.class.getDeclaredField("commandMap");
                f.setAccessible(true);

                SimpleCommandMap cmdMap = (SimpleCommandMap) f.get(Bukkit.getPluginManager());
                // REGISTER HERE
                cmdMap.register("bank", "bank::bank", bankCommand.getCommandBukkit());
                cmdMap.register("pay", "bank::pay", payCommand.getCommandBukkit());
            }
        } catch (IllegalAccessException | NoSuchFieldException e){
            e.printStackTrace();
        }

    }
}
