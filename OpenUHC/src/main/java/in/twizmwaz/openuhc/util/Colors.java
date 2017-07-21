package in.twizmwaz.openuhc.util;

import org.bukkit.ChatColor;

public class Colors {

  /**
   * Removes any unnecessary color codes from a string.
   *
   * @param str The string to condense
   * @return The condensed string
   */
  public static String condense(String str) {
    StringBuilder result = new StringBuilder();

    boolean nextColor = false;
    ChatColor color = null;
    for (char c : str.toCharArray()) {
      if (c == '§') {
        nextColor = true;
      } else if (nextColor) {
        ChatColor currentColor = ChatColor.getByChar(c);
        //Append this color to the condensed string if it actually changes the color and appearance of the original
        if (currentColor != null && !currentColor.equals(color)) {
          color = currentColor;
          result.append(currentColor);
        }
      } else {
        result.append(c);
      }
    }

    return result.toString();
  }

}
