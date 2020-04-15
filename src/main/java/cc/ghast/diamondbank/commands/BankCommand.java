package cc.ghast.diamondbank.commands;

import cc.ghast.diamondbank.DiamondBank;
import cc.ghast.diamondbank.api.command.ACommand;
import cc.ghast.diamondbank.commands.gui.BankGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Ghast
 * @since 13-Apr-20
 */
public class BankCommand extends ACommand {
    public BankCommand(DiamondBank plugin) {
        super("bank", "Main command for the bank plugin", "diamondbank.bank", false, plugin);
        setPlayerOnly();
    }

    @Override
    public boolean run(CommandSender executor, DiamondBank artemis, String[] args) {
        Player player = (Player) executor;
        BankGUI.inventory.open(player);
        return false;
    }
}
