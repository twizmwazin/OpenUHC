package in.twizmwaz.openuhc.util;

import java.util.Random;

public class Numbers {

  public static final Random RANDOM = new Random();

  /**
   * Produces a random number in between {@param min} and {@param max}, inclusive.
   * @param min The minimum bound for the random number
   * @param max The maximum bound for the random number
   * @return The random number
   */
  public static int random(int min, int max) {
    return RANDOM.nextInt(max - min + 1) + min;
  }

}
