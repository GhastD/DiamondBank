package cc.ghast.diamondbank.managers;

import cc.ghast.diamondbank.DiamondBank;
import cc.ghast.diamondbank.api.manager.Manager;
import cc.ghast.diamondbank.commands.gui.BankGUI;
import cc.ghast.diamondbank.commands.gui.DepositGUI;
import fr.minuskube.inv.InventoryManager;
import fr.minuskube.inv.SmartInvsPlugin;

/**
 * @author Ghast
 * @since 14-Apr-20
 */
public class GUIManager implements Manager {
    private DiamondBank plugin;
    private BankGUI bankGUI;
    private DepositGUI depositGUI;
    private InventoryManager inventoryManager;

    public GUIManager(DiamondBank plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {
        this.inventoryManager = new InventoryManager(plugin);
        this.inventoryManager.init();
        this.bankGUI = new BankGUI(plugin, inventoryManager);
        this.depositGUI = new DepositGUI(plugin, inventoryManager);
    }

    @Override
    public void disinit() {
        this.inventoryManager = null;
        this.bankGUI = null;
        this.depositGUI = null;
    }
}
