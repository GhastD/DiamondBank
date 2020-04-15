package cc.ghast.diamondbank.commands;

import cc.ghast.diamondbank.DiamondBank;
import cc.ghast.diamondbank.api.command.ACommand;
import cc.ghast.diamondbank.api.sqlite.Schema;
import cc.ghast.diamondbank.api.vault.Transaction;
import cc.ghast.diamondbank.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Ghast
 * @since 14-Apr-20
 */
public class PayCommand extends ACommand {
    private final DiamondBank plugin;

    public PayCommand(DiamondBank plugin) {
        super("pay", "Pay an individual x diamonds!", "diamondbank.pay", false, plugin);
        this.plugin = plugin;
        setPlayerOnly();
    }

    @Override
    public boolean run(CommandSender executor, DiamondBank artemis, String[] args) {
        Player player = (Player) executor;
        switch (args.length){
            case 2: {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null){
                    player.sendMessage(Chat.translate("&7(&c&l!&7)&c The desired player may be offline or the name is invalid!"));
                    return true;
                }

                int amount;
                try {
                    amount = Integer.parseInt(args[1]);
                } catch (NumberFormatException e){
                    player.sendMessage(Chat.translate("&7(&c&l!&7)&c Invalid amount! Make sure you are using digits only"));
                    return true;
                }
                long balance = plugin.getEconomyManager().getDiamonds(player);
                if (balance < amount){
                    player.sendMessage(Chat.translate("&7(&c&l!&7)&c You only have &e" + balance + "&6 diamonds!"));
                    return true;
                }

                plugin.getEconomyManager().removeDiamonds(player, amount);
                plugin.getEconomyManager().addDiamonds(target, amount);
                Schema.createTransaction(new Transaction(amount, player.getUniqueId(), target.getUniqueId())).execute();
                player.sendMessage(Chat.translate("&7(&c&l!&7)&a Successfully sent &e" + amount + "&a diamonds to &e" + target.getDisplayName()));
                target.sendMessage(Chat.translate("&7(&c&l!&7)&a You've received &e" + amount + " &adiamonds from " + player.getDisplayName()));
                return true;
            }
            default: {
                player.sendMessage(Chat.translate("&7(&c&l!&7)&c Invalid usage! Do &e/&6pay &e<&6player&e> <&6amount&e>"));
                return true;
            }
        }
    }
}
