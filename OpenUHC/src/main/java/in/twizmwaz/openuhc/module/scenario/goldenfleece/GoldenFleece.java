package in.twizmwaz.openuhc.module.scenario.goldenfleece;

import in.twizmwaz.openuhc.OpenUHC;
import in.twizmwaz.openuhc.module.Lifecycle;
import in.twizmwaz.openuhc.module.Module;
import in.twizmwaz.openuhc.module.ModuleInfo;
import in.twizmwaz.openuhc.module.ScenarioInfo;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

@ModuleInfo(lifecycle = Lifecycle.GAME)
@ScenarioInfo(
    name = "Golden Fleece",
    desc = "When a sheep is killed, either resources will be dropped, or a skeleton in gold armor will be spawned."
)
public class GoldenFleece implements Module, Listener {

  @Override
  public void onEnable() {
    OpenUHC.registerEvents(this);
  }

  @Override
  public void onDisable() {
    HandlerList.unregisterAll(this);
  }

  @EventHandler
  public void onEntityDeath(EntityDeathEvent event) {
    Entity entity = event.getEntity();
    if (entity instanceof Sheep) {
      double chance = Math.random();
      if (0.25 > chance) {
        Skeleton skeleton = entity.getWorld().spawn(entity.getLocation(), Skeleton.class);
        skeleton.getEquipment().setArmorContents(
            new ItemStack[]{
                new ItemStack(Material.GOLD_HELMET),
                new ItemStack(Material.GOLD_CHESTPLATE),
                new ItemStack(Material.GOLD_LEGGINGS),
                new ItemStack(Material.GOLD_BOOTS)
            }
        );
      } else if (0.5 > chance) {
        event.getDrops().add(new ItemStack(Material.IRON_INGOT));
      } else if (0.75 > chance) {
        event.getDrops().add(new ItemStack(Material.GOLD_INGOT));
      } else {
        event.getDrops().add(new ItemStack(Material.DIAMOND));
      }
    }
  }

}
