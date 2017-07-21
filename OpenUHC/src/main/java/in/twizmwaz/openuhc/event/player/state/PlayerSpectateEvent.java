package in.twizmwaz.openuhc.event.player.state;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

@Getter
@Setter
public class PlayerSpectateEvent extends PlayerEvent implements Cancellable {

  @Getter private static final HandlerList handlerList = new HandlerList();

  private boolean cancelled;

  public PlayerSpectateEvent(Player player) {
    super(player);
  }

  @Override
  public HandlerList getHandlers() {
    return handlerList;
  }

}
