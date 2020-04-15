package cc.ghast.diamondbank.managers;

import cc.ghast.diamondbank.DiamondBank;
import cc.ghast.diamondbank.api.manager.Manager;
import cc.ghast.diamondbank.api.sqlite.SQLite;
import cc.ghast.diamondbank.api.sqlite.Schema;
import cc.ghast.diamondbank.api.vault.Bank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * @author Ghast
 * @since 13-Apr-20
 */
public class EconomyManager implements Manager {

    private DiamondBank plugin;
    private final Map<Player, Bank> cachedBanks = new HashMap<>();
    private SQLite sqLite;

    public EconomyManager(DiamondBank plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {
        this.sqLite = new SQLite();
        sqLite.connect(plugin);
        Bukkit.getOnlinePlayers().forEach(this::cachePlayer);
    }

    @Override
    public void disinit() {
        for (Map.Entry<Player, Bank> bankEntry : cachedBanks.entrySet()){
            Schema.updateBalance(bankEntry.getKey().getUniqueId(), bankEntry.getValue().getBalance()).execute();
        }
        sqLite.stop();
        this.sqLite = null;
        this.cachedBanks.clear();
    }

    public void cachePlayer(Player player){
        if (cachedBanks.containsKey(player)) return;
        if (!Schema.accountExist(player.getUniqueId())){
            Schema.createData(player).execute();
            cachedBanks.put(player, new Bank(player.getUniqueId(), 0));
            return;
        }
        cachedBanks.put(player, new Bank(player.getUniqueId(), Schema.getBalance(player.getUniqueId())));
    }

    public void unCachePlayer(Player player){
        if (!(cachedBanks.containsKey(player))) return;
        Bank bank = cachedBanks.get(player);
        Schema.updateBalance(player.getUniqueId(), bank.getBalance()).execute();
        cachedBanks.remove(player);
    }

    public long getDiamonds(Player player){
        return cachedBanks.get(player).getBalance();
    }

    public void removeDiamonds(Player player, int amount){
       cachedBanks.get(player).removeBalance(amount);
    }

    public void addDiamonds(Player player, int amount){
        cachedBanks.get(player).addBalance(amount);
    }
}
