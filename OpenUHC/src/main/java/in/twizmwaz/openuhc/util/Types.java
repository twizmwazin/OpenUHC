package in.twizmwaz.openuhc.util;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Types {

  /**
   * Attempts to parse nearly any type of object. All primitives and strings, as well as char[], are supported.
   *
   * @param type The type of the class to parse
   * @param value The string representation of the object
   * @return The parsed object, or null if invalid
   */
  public static Object parseObject(@NonNull final Class<?> type, @NonNull final String value) {
    if (type == Boolean.class || type == boolean.class) {
      return value.equalsIgnoreCase("on") || value.equalsIgnoreCase("enabled") || Boolean.parseBoolean(value);
    } else if (type == Byte.class || type == byte.class) {
      return Byte.parseByte(value);
    } else if (type == Character.class || type == char.class) {
      return value.length() > 0 ? value.charAt(0) : '\0';
    } else if (type == Short.class || type == short.class) {
      return Short.parseShort(value);
    } else if (type == Integer.class || type == int.class) {
      return Integer.parseInt(value);
    } else if (type == Long.class || type == long.class) {
      return Long.parseLong(value);
    } else if (type == Float.class || type == float.class) {
      return Float.parseFloat(value);
    } else if (type == Double.class || type == double.class) {
      return Double.parseDouble(value);
    } else if (type == char[].class) {
      return value.toCharArray();
    } else if (type == String.class) {
      return value;
    } else {
      return null;
    }
  }
}
