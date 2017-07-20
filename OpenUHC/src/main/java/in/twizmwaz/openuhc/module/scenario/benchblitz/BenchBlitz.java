package in.twizmwaz.openuhc.module.scenario.benchblitz;

import in.twizmwaz.openuhc.OpenUHC;
import in.twizmwaz.openuhc.module.IModule;
import in.twizmwaz.openuhc.module.LifeCycle;
import in.twizmwaz.openuhc.module.Module;
import in.twizmwaz.openuhc.module.Scenario;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Module(lifeCycle = LifeCycle.GAME)
@Scenario(name = "Bench Blitz", desc = "Players may only have one crafting table.")
public class BenchBlitz implements Listener, IModule {

  private final List<UUID> craftedWorkbench = new ArrayList<>();

  @Override
  public void onEnable() {
    OpenUHC.registerEvents(this);
  }

  @Override
  public void onDisable() {
    HandlerList.unregisterAll(this);
  }

  /**
   * Limits the player to one workbench by means of crafting.
   * @param event The event
   */
  @EventHandler(ignoreCancelled = true)
  public void onCraftItem(CraftItemEvent event) {
    if (event.getRecipe().getResult().getType().equals(Material.WORKBENCH)) {
      Player player = (Player) event.getWhoClicked();
      UUID uuid = player.getUniqueId();
      if (craftedWorkbench.contains(uuid)) {
        player.sendMessage(ChatColor.RED + "You may not craft another workbench!");
      } else {
        craftedWorkbench.add(uuid);
        limitWorkbenches(player);

        player.sendMessage(ChatColor.YELLOW + "You have crafted a workbench. You may not have another one.");
      }
    }
  }

  /**
   * Limits the player to one workbench in their inventory at a time, by means of picking up an item.
   * @param event The event
   */
  @EventHandler(ignoreCancelled = true)
  public void onPlayerPickupItem(PlayerPickupItemEvent event) {
    Item item = event.getItem();
    Player player = event.getPlayer();
    if (item.getItemStack().getType().equals(Material.WORKBENCH)
        && player.getInventory().contains(Material.WORKBENCH)) {
      event.setCancelled(true);
      item.setPickupDelay(20);

      player.sendMessage(ChatColor.RED + "You may not pick up another workbench!");
    }
  }

  /**
   * Limits the player to one workbench in their inventory at a time, by means of taking the item from another
   * inventory.
   * @param event The event
   */
  @EventHandler(ignoreCancelled = true)
  public void onInventoryClick(InventoryClickEvent event) {
    Player player = (Player) event.getWhoClicked();
    if (event.getCurrentItem().getType().equals(Material.WORKBENCH)
        && player.getInventory().contains(Material.WORKBENCH)) {
      event.setCancelled(true);

      player.sendMessage(ChatColor.RED + "You may not take another workbench!");
    }
  }

  private void limitWorkbenches(Player player) {
    boolean workbench = false;
    Inventory inventory = player.getInventory();
    for (int slot = 0; slot < inventory.getSize(); ++slot) {
      ItemStack item = inventory.getItem(slot);
      if (item != null && item.getType().equals(Material.WORKBENCH)) {
        if (workbench) {
          inventory.setItem(slot, null);
        } else {
          workbench = true;
          item.setAmount(1);
          //TODO: Ensure that the amount of workbenches in the inventory updates on the client
        }
      }
    }
  }

}
