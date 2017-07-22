package in.twizmwaz.openuhc.game.exception;

import in.twizmwaz.openuhc.game.GameState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class InvalidGameStateException extends RuntimeException {

  private final GameState current;
  private final GameState expected;

}
