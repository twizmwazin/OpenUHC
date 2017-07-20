package in.twizmwaz.openuhc.module.blockdrop;

import in.twizmwaz.openuhc.OpenUHC;
import in.twizmwaz.openuhc.module.Lifecycle;
import in.twizmwaz.openuhc.module.Module;
import in.twizmwaz.openuhc.module.ModuleInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

@ModuleInfo(lifecycle = Lifecycle.SERVER)
public class BlockDropModule implements Module, Listener {

  private Map<Block, List<BlockDrop>> blockDrops = new HashMap<>();

  @Override
  public void onEnable() {
    OpenUHC.registerEvents(this);
  }

  @Override
  public void onDisable() {
    HandlerList.unregisterAll(this);
  }

  public void addBlockDrop(Block block, BlockDrop blockDrop) {
    blockDrops.putIfAbsent(block, new ArrayList<>());
    blockDrops.get(block).add(blockDrop);
  }

  @EventHandler(ignoreCancelled = true)
  public void onBlockBreak(BlockBreakEvent event) {
    Block block = event.getBlock();
    if (blockDrops.containsKey(block)) {
      List<BlockDrop> blockDrops = this.blockDrops.get(block);

      Location location = block.getLocation().add(0.5, 0.5, 0.5);
      World world = location.getWorld();
      boolean override = false;
      for (BlockDrop blockDrop : blockDrops) {
        world.dropItem(location, blockDrop.getItem());
        if (blockDrop.isOverride()) {
          override = true;
        }
      }
      if (override) {
        event.setCancelled(true);
        block.setType(Material.AIR);

        //Any experience that was previously going to be dropped will still be dropped, despite the event being
        //cancelled
        int xp = event.getExpToDrop();
        if (xp != 0) {
          ExperienceOrb xpOrb = world.spawn(location, ExperienceOrb.class);
          xpOrb.setExperience(xp);
        }
      }
    }
  }

  //TODO: Implement block drops for when the block is broken due to an entity explosion (creeper, TNT, etc.)

}
