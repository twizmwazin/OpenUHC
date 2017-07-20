package in.twizmwaz.openuhc.module.scenario.bowless;

import in.twizmwaz.openuhc.OpenUHC;
import in.twizmwaz.openuhc.module.IModule;
import in.twizmwaz.openuhc.module.LifeCycle;
import in.twizmwaz.openuhc.module.Module;
import in.twizmwaz.openuhc.module.Scenario;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

@Module(lifeCycle = LifeCycle.GAME)
@Scenario(name = "Bowless", desc = "Players may not use bows.")
public class Bowless implements IModule, Listener {

  @Override
  public void onEnable() {
    OpenUHC.registerEvents(this);
  }

  @Override
  public void onDisable() {
    HandlerList.unregisterAll(this);
  }

  /**
   * Prevents a player from crafting a bow.
   * @param event The event
   */
  @EventHandler(ignoreCancelled = true)
  public void onCraftItem(CraftItemEvent event) {
    if (event.getRecipe().getResult().getType().equals(Material.BOW)) {
      event.setCancelled(true);
      event.getWhoClicked().sendMessage(ChatColor.RED + "You may not craft a bow!");
    }
  }

  /**
   * Prevents a player from picking up a bow.
   * @param event The event
   */
  @EventHandler(ignoreCancelled = true)
  public void onPlayerPickupItem(PlayerPickupItemEvent event) {
    Item item = event.getItem();
    if (item.getItemStack().getType().equals(Material.BOW)) {
      event.setCancelled(true);
      item.setPickupDelay(20);
      
      event.getPlayer().sendMessage(ChatColor.RED + "You may not pick up a bow!");
    }
  }

  /**
   * Prevents a player from taking a bow from another inventory.
   * @param event The event
   */
  @EventHandler(ignoreCancelled = true)
  public void onInventoryClick(InventoryClickEvent event) {
    if (event.getCurrentItem().getType().equals(Material.BOW)) {
      event.setCancelled(true);
      event.getWhoClicked().sendMessage(ChatColor.RED + "You may not take a bow!");
    }
  }

}
