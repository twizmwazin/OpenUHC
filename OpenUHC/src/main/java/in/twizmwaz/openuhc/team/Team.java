package in.twizmwaz.openuhc.team;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Team {

  @Getter
  private final List<UUID> players = new ArrayList<>();
  @Getter
  private final String prefix;

}
