package in.twizmwaz.openuhc.module.scenario.goldrush;

import in.twizmwaz.openuhc.OpenUHC;
import in.twizmwaz.openuhc.module.Lifecycle;
import in.twizmwaz.openuhc.module.Module;
import in.twizmwaz.openuhc.module.ModuleInfo;
import in.twizmwaz.openuhc.module.ScenarioInfo;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

@ModuleInfo(lifecycle = Lifecycle.GAME)
@ScenarioInfo(name = "Gold Rush", desc = "Players may not craft leather nor iron armor.")
public class GoldRush implements Module, Listener {

  @Override
  public void onEnable() {
    OpenUHC.registerEvents(this);
  }

  @Override
  public void onDisable() {
    HandlerList.unregisterAll(this);
  }

  @EventHandler(ignoreCancelled = true)
  public void onCraftItem(CraftItemEvent event) {
    Material result = event.getRecipe().getResult().getType();
    if (result.equals(Material.LEATHER_HELMET)
        || result.equals(Material.LEATHER_CHESTPLATE)
        || result.equals(Material.LEATHER_LEGGINGS)
        || result.equals(Material.LEATHER_BOOTS)) {
      event.setCancelled(true);
      event.getWhoClicked().sendMessage(ChatColor.RED + "You may not craft leather armor!");
    } else if (result.equals(Material.IRON_HELMET)
        || result.equals(Material.IRON_CHESTPLATE)
        || result.equals(Material.IRON_LEGGINGS)
        || result.equals(Material.IRON_BOOTS)) {
      event.setCancelled(true);
      event.getWhoClicked().sendMessage(ChatColor.RED + "You may not craft iron armor!");
    }
  }

}
