package com.wagner.mycv.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"}) // Util class
public final class CollectionUtil {

  private static final String DEFAULT_SEPARATOR = ", ";

  private CollectionUtil() {
    // no instances allowed for util class
  }

  public static Iterable<String> characterSeparatedStringToList(String value, String separatorRegex) {
    if (value == null || value.isEmpty()) {
      return Collections.emptyList();
    }

    if (separatorRegex == null || separatorRegex.isEmpty()) {
      return Collections.singletonList(value);
    }

    String[] splittedString = value.split(separatorRegex);
    return Arrays.asList(splittedString);
  }

  public static Iterable<String> characterSeparatedStringToList(String value) {
    return characterSeparatedStringToList(value, DEFAULT_SEPARATOR);
  }

  public static <T> T getFromListOrDefault(List<T> list, int index, T defaultValue) {
    if (list != null && index < list.size() && index >= 0) {
      return list.get(index);
    }

    return defaultValue;
  }

  public static <T> T getFromListOrDefault(List<T> list, int index) {
    return getFromListOrDefault(list, index, null);
  }

}
