package in.twizmwaz.openuhc.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum GameState {

  NEW(0),
  GENERATED(1),
  SCATTERED(2),
  PLAYING(3),
  COMPLETED(4),
  ;

  private final int id;

}
