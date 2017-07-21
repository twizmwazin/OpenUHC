package in.twizmwaz.openuhc.module.scenario.infiniteenchanter;

import in.twizmwaz.openuhc.OpenUHC;
import in.twizmwaz.openuhc.event.player.PlayerInitEvent;
import in.twizmwaz.openuhc.module.IModule;
import in.twizmwaz.openuhc.module.LifeCycle;
import in.twizmwaz.openuhc.module.Module;
import in.twizmwaz.openuhc.module.Scenario;

import in.twizmwaz.openuhc.module.Setting;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Module(lifeCycle = LifeCycle.GAME)
@Scenario(name = "Infinite Enchanter", desc = "Players spawn with resources necessary to enchant.")
public class InfiniteEnchanter implements IModule, Listener {

  @Setting("Give players an anvil")
  private boolean anvils = false;

  @Override
  public void onEnable() {
    OpenUHC.registerEvents(this);
  }

  @Override
  public void onDisable() {
    HandlerList.unregisterAll(this);
  }

  /**
   * Gives players the resources necessary to enchant when they start playing in the game.
   *
   * @param event The event
   */
  @EventHandler
  public void onPlayerInit(PlayerInitEvent event) {
    Player player = event.getPlayer();

    Inventory inventory = player.getInventory();
    inventory.addItem(
        new ItemStack(Material.ENCHANTMENT_TABLE, 64),
        new ItemStack(Material.BOOKSHELF, 64),
        new ItemStack(Material.BOOKSHELF, 64),
        new ItemStack(Material.LAPIS_BLOCK, 64)
    );
    if (anvils) {
      inventory.addItem(new ItemStack(Material.ANVIL, 64));
    }
    player.setLevel(30);

    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
  }

  @EventHandler(ignoreCancelled = true)
  public void onEnchantItem(EnchantItemEvent event) {
    Bukkit.getScheduler().runTask(OpenUHC.getInstance(), () -> event.getEnchanter().setLevel(30));
  }

}
