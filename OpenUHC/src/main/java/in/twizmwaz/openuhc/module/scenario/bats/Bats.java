package in.twizmwaz.openuhc.module.scenario.bats;

import in.twizmwaz.openuhc.OpenUHC;
import in.twizmwaz.openuhc.module.IModule;
import in.twizmwaz.openuhc.module.LifeCycle;
import in.twizmwaz.openuhc.module.Module;
import in.twizmwaz.openuhc.module.Scenario;

import org.bukkit.Material;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

@Module(lifeCycle = LifeCycle.GAME)
@Scenario(
    name = "Bats",
    desc = "When a bat is killed, there is a small chance that the player is instantly killed, but otherwise receives a"
        + " golden apple."
)
public class Bats implements IModule, Listener {

  @Override
  public void onEnable() {
    OpenUHC.registerEvents(this);
  }

  @Override
  public void onDisable() {
    HandlerList.unregisterAll(this);
  }

  /**
   * Upon a bat's death, drops a golden apple the majority of the time, with a small chance of instantly killing the
   * player.
   * @param event The event
   */
  @EventHandler
  public void onEntityDeath(EntityDeathEvent event) {
    Entity entity = event.getEntity();
    Player player = event.getEntity().getKiller();
    if (entity instanceof Bat && player != null) {
      if (0.01 > Math.random()) {
        player.damage(Integer.MAX_VALUE, entity);
      } else {
        entity.getWorld().dropItem(entity.getLocation(), new ItemStack(Material.GOLDEN_APPLE));
      }
    }
  }

}
