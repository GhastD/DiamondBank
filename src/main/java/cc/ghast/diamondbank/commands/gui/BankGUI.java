package cc.ghast.diamondbank.commands.gui;

import cc.ghast.diamondbank.DiamondBank;
import cc.ghast.diamondbank.utils.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.InventoryManager;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * @author Ghast
 * @since 13-Apr-20
 */
public class BankGUI implements InventoryProvider {

    public static SmartInventory inventory;
    private DiamondBank plugin;

    public BankGUI(DiamondBank plugin, InventoryManager manager) {
        this.plugin = plugin;
        inventory = SmartInventory.builder()
                .closeable(true)
                .provider(this)
                .manager(manager)
                .title("Bank")
                .id("bank_gui")
                .build();
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();

        SlotIterator iterator = contents.newIterator(SlotIterator.Type.HORIZONTAL,1, 0)
                .allowOverride(true);
        pagination.setItemsPerPage(45);


        long diamonds = plugin.getEconomyManager().getDiamonds(player);
        int stacks = Math.floorDiv((int) diamonds, 64);
        int leftover = (int) diamonds % 64;

        int stackRows = (int) Math.ceil((float) stacks / 9f);

        ClickableItem[] array = new ClickableItem[stacks + (leftover > 0 ? 1 : 0)];

        for (int i = 0; i < stacks; i++){
            array[i] = ClickableItem.of(new ItemBuilder(Material.DIAMOND).name("&bStack o' Diamond").amount(64).build(), e -> {
                if (e.isLeftClick()){
                    giveDiamonds(player, 64);
                    inventory.open(player);
                }
            });
        }
        if (leftover > 0){
            array[array.length - 1] = ClickableItem.of(new ItemBuilder(Material.DIAMOND).name("&bStack o' Diamond")
                    .amount(leftover).build(), e -> {
                if (e.isLeftClick()){
                    giveDiamonds(player, leftover);
                    inventory.open(player);
                }
            });
        }
        pagination.setItems(array);
        pagination.addToIterator(iterator);
        contents.fillRow(0, ClickableItem.empty(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name("").build()));
        contents.fillRow(5, ClickableItem.empty(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name("").build()));
        contents.set(5, 3, ClickableItem.of(new ItemBuilder(Material.ARROW).name("&6Previous page").build(),
                e -> inventory.open(player, pagination.previous().getPage())));
        contents.set(5,4, ClickableItem.of(new ItemBuilder(Material.CAULDRON).name("&6Deposit Funds").build(),
                e -> DepositGUI.inventory.open(player)));
        contents.set(5, 5, ClickableItem.of(new ItemBuilder(Material.ARROW).name("&6Next page").build(),
                e -> inventory.open(player, pagination.next().getPage())));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    private void giveDiamonds(Player player, int amount){
        if (hasAvailableSlot(player)){
            player.getInventory().addItem(new ItemBuilder(Material.DIAMOND).amount(amount).build());
            plugin.getEconomyManager().removeDiamonds(player, amount);
        }
    }

    private boolean hasAvailableSlot(Player player){
        Inventory inv = player.getInventory();
        for (ItemStack item: inv.getContents()) {
            if(item == null) {
                return true;
            }
        }
        return false;
    }
}
