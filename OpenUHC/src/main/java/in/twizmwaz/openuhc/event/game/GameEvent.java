package in.twizmwaz.openuhc.event.game;

import in.twizmwaz.openuhc.game.Game;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;

@Getter
@RequiredArgsConstructor
public abstract class GameEvent extends Event {

  private final Game game;

}
