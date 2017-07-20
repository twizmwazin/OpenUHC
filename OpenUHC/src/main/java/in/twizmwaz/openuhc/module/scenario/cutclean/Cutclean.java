package in.twizmwaz.openuhc.module.scenario.cutclean;

import in.twizmwaz.openuhc.OpenUHC;
import in.twizmwaz.openuhc.module.IModule;
import in.twizmwaz.openuhc.module.LifeCycle;
import in.twizmwaz.openuhc.module.Module;
import in.twizmwaz.openuhc.module.Scenario;
import in.twizmwaz.openuhc.module.blockdrop.BlockDrop;
import in.twizmwaz.openuhc.module.blockdrop.BlockDropModule;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

@Module(lifeCycle = LifeCycle.GAME)
@Scenario(name = "Cutclean", desc = "Resources are harvested in their smelted form.")
public class Cutclean implements IModule, Listener {

  @Override
  public void onEnable() {
    OpenUHC.registerEvents(this);
  }

  @Override
  public void onDisable() {
    HandlerList.unregisterAll(this);
  }

  /**
   * Drops the smelted form of items that spawn when a block is broken.
   * @param event The event
   */
  @EventHandler(ignoreCancelled = true)
  public void onBlockBreak(BlockBreakEvent event) {
    Block block = event.getBlock();
    Material type = block.getType();

    BlockDropModule module = OpenUHC.getCurrentGame().getModuleHandler().getModule(BlockDropModule.class);
    if (type.equals(Material.IRON_ORE)) {
      module.addBlockDrop(block, new BlockDrop(new ItemStack(Material.IRON_INGOT), true));
      event.setExpToDrop(4);
    } else if (type.equals(Material.GOLD_ORE)) {
      module.addBlockDrop(block, new BlockDrop(new ItemStack(Material.GOLD_INGOT), true));
      event.setExpToDrop(8);
    } else if (type.equals(Material.POTATO)) {
      module.addBlockDrop(block, new BlockDrop(new ItemStack(Material.BAKED_POTATO), true));
      event.setExpToDrop(2);
    }
  }

  /**
   * Drops the smelted form of items that spawn when a mob is killed.
   * @param event The event
   */
  @EventHandler
  public void onEntityDeath(EntityDeathEvent event) {
    Entity entity = event.getEntity();
    if (entity instanceof Chicken) {
      for (ItemStack item : event.getDrops()) {
        if (item.getType().equals(Material.RAW_CHICKEN)) {
          item.setType(Material.COOKED_CHICKEN);
        }
      }
    } else if (entity instanceof Cow) {
      for (ItemStack drop : event.getDrops()) {
        if (drop.getType().equals(Material.RAW_BEEF)) {
          drop.setType(Material.COOKED_BEEF);
        }
      }
    } else if (entity instanceof Pig) {
      for (ItemStack item : event.getDrops()) {
        if (item.getType().equals(Material.PORK)) {
          item.setType(Material.GRILLED_PORK);
        }
      }
    } else if (entity instanceof Rabbit) {
      for (ItemStack item : event.getDrops()) {
        if (item.getType().equals(Material.RABBIT)) {
          item.setType(Material.COOKED_RABBIT);
        }
      }
    } else if (entity instanceof Sheep) {
      for (ItemStack item : event.getDrops()) {
        if (item.getType().equals(Material.MUTTON)) {
          item.setType(Material.COOKED_MUTTON);
        }
      }
    }
  }

  /**
   * Drops cooked fish whenever a player is fishing and receives raw fish.
   * @param event The event
   */
  @EventHandler(ignoreCancelled = true)
  public void onPlayerFish(PlayerFishEvent event) {
    Entity caught = event.getCaught();
    if (caught instanceof ItemStack) {
      ItemStack fish = (ItemStack) caught;
      if (fish.getType().equals(Material.RAW_FISH)) {
        fish.setType(Material.COOKED_FISH);
      }
    }
  }

}
