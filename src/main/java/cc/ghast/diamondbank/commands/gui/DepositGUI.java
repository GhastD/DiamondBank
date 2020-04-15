package cc.ghast.diamondbank.commands.gui;

import cc.ghast.diamondbank.DiamondBank;
import cc.ghast.diamondbank.utils.Chat;
import cc.ghast.diamondbank.utils.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.InventoryManager;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.SlotPos;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * @author Ghast
 * @since 14-Apr-20
 */
public class DepositGUI implements InventoryProvider {
    public static SmartInventory inventory;
    private DiamondBank plugin;

    public DepositGUI(DiamondBank plugin, InventoryManager manager) {
        this.plugin = plugin;
        inventory = SmartInventory.builder()
                .size(3,9)
                .id("deposit")
                .title("Deposit GUI")
                .provider(this)
                .manager(manager)
                .build();
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.fillBorders(ClickableItem.empty(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name("").build()));
        ClickableItem oneD = ClickableItem.of(new ItemBuilder(Material.DIAMOND).amount(1).name("&bDeposit &6&lone&b diamond").build(),
                e -> depositDiamond(player, 1));
        ClickableItem eightD = ClickableItem.of(new ItemBuilder(Material.DIAMOND).amount(8).name("&bDeposit &6&leight&b diamond").build(),
                e -> depositDiamond(player, 8));
        ClickableItem sixteenD = ClickableItem.of(new ItemBuilder(Material.DIAMOND).amount(16).name("&bDeposit &6&lsixteen&b diamond").build(),
                e -> depositDiamond(player, 16));
        ClickableItem thirtysixD = ClickableItem.of(new ItemBuilder(Material.DIAMOND).amount(32).name("&bDeposit &6&lthirty-six&b diamond").build(),
                e -> depositDiamond(player, 32));
        ClickableItem sixtyFourD = ClickableItem.of(new ItemBuilder(Material.DIAMOND).amount(64).name("&bDeposit &6&lsixty-four&b diamond").build(),
                e -> depositDiamond(player, 64));

        contents.set(SlotPos.of(1,2), oneD)
                .set(SlotPos.of(1,3), eightD)
                .set(SlotPos.of(1,4), sixteenD)
                .set(SlotPos.of(1,5), thirtysixD)
                .set(SlotPos.of(1,6), sixtyFourD);

    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    private void depositDiamond(Player player, int amount) {
        if (getDiamonds(player) >= amount){
            removeDiamonds(player, amount);
            plugin.getEconomyManager().addDiamonds(player, amount);
            player.sendMessage(Chat.translate("&aSuccesfully deposited &b" + amount + " diamonds&a!"));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_TRADE, 10, 1);
        } else {
            player.closeInventory();
            player.sendMessage(Chat.translate("&cYou need " + amount + " diamonds to deposit it!"));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10, 1);
        }
    }

    private void removeDiamonds(Player player, int amount){
        Inventory inv = player.getInventory();
        int count = amount;

        for (ItemStack item: inv.getContents()) {
            if (count == 0) return;
            if (item == null || item.getType() == null) continue;
            if(item.getType().equals(Material.DIAMOND)) {
                if (count >= item.getAmount()){
                    count -= item.getAmount();
                    inv.removeItem(item);
                } else if (count < item.getAmount()){
                    int remaining = item.getAmount() - count;
                    item.setAmount(remaining);
                    count = 0;
                }
            }
        }
    }

    private int getDiamonds(Player player){
        Inventory inv = player.getInventory();
        int count = 0;
        for (ItemStack item: inv.getContents()) {
            if (item == null || item.getType() == null) continue;
            if (item.getType().equals(Material.DIAMOND)) {
                count += item.getAmount();
            }
        }
        return count;
    }
}
