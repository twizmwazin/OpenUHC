package in.twizmwaz.openuhc.event.player;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

@Getter
public class PlayerInitEvent extends PlayerEvent {

  @Getter private static final HandlerList handlerList = new HandlerList();

  private final boolean late;

  public PlayerInitEvent(Player player, boolean late) {
    super(player);
    this.late = late;
  }

  @Override
  public HandlerList getHandlers() {
    return handlerList;
  }

}
