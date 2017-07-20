package in.twizmwaz.openuhc.module.scenario.inventors;

import in.twizmwaz.openuhc.OpenUHC;
import in.twizmwaz.openuhc.module.IModule;
import in.twizmwaz.openuhc.module.LifeCycle;
import in.twizmwaz.openuhc.module.Module;
import in.twizmwaz.openuhc.module.Scenario;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

@Module(lifeCycle = LifeCycle.GAME)
@Scenario(name = "Inventors", desc = "When an item is crafted for the first time, it is announced in chat.")
public class Inventors implements IModule, Listener {

  private final List<Material> invented = new ArrayList<>();

  @Override
  public void onEnable() {
    OpenUHC.registerEvents(this);
  }

  @Override
  public void onDisable() {
    HandlerList.unregisterAll(this);
  }

  /**
   * Checks if a player has crafted an item for the first time and announces it if this is the case.
   * @param event The event
   */
  @EventHandler(ignoreCancelled = true)
  public void onCraftItem(CraftItemEvent event) {
    Material type = event.getRecipe().getResult().getType();
    if (!invented.contains(type)) {
      invented.add(type);
      Bukkit.broadcastMessage(
          event.getWhoClicked().getName() + ChatColor.GRAY + " has invented the " + ChatColor.AQUA + type.name()
      );
    }
  }

}
