package com.wagner.mycv.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test util class 'CollectionUtil'")
class CollectionUtilTest {

  private static final List<String> SAMPLE_VALUES = Arrays.asList("zero", "one", "two", "three", "four", "five");
  private static final String       DEFAULT_VALUE = "Default";

  @DisplayName("Test method 'characterSeparatedStringToList' with default separator")
  @ParameterizedTest(name = "{index} => string value is {0} | expected is {1}")
  @MethodSource("characterSeparatedStringToListWithDefaultSeparatorProvider")
  void test_characterSeparatedStringToList_with_default_separator(String value, Iterable<String> expected) {
    Iterable<String> actual = CollectionUtil.characterSeparatedStringToList(value);
    assertEquals(expected, actual);
  }

  @DisplayName("Test method 'characterSeparatedStringToList' with custom separator")
  @ParameterizedTest(name = "{index} => string value is {0} | separator is {1} | expected is {2}")
  @MethodSource("characterSeparatedStringToListWithCustomSeparatorProvider")
  void test_characterSeparatedStringToList_robustness(String value, String separator, Iterable<String> expected) {
    Iterable<String> actual = CollectionUtil.characterSeparatedStringToList(value, separator);
    assertEquals(expected, actual);
  }

  @DisplayName("Test method 'getOrDefault")
  @ParameterizedTest(name = "{index} => values are {0} | index is {1} | default value is {2} | expected is {3}")
  @MethodSource("getFromListOrDefaultProvider")
  void test_getFromListOrDefault(List<String> values, int index, String defaultValue, String expected) {
    String actual = CollectionUtil.getOrDefault(values, index, defaultValue);
    assertEquals(expected, actual);
  }

  private static Stream<Arguments> characterSeparatedStringToListWithDefaultSeparatorProvider() {
    return Stream.of(
            Arguments.of("zero, one, two, three, four, five", Arrays.asList("zero", "one", "two", "three", "four", "five")),
            Arguments.of("zero; one; two; three; four; five", Collections.singletonList("zero; one; two; three; four; five")),
            Arguments.of("zero", Collections.singletonList("zero")),
            Arguments.of("", Collections.emptyList()),
            Arguments.of(null, Collections.emptyList())
    );
  }

  private static Stream<Arguments> characterSeparatedStringToListWithCustomSeparatorProvider() {
    return Stream.of(
            Arguments.of("zero, one, two, three, four, five", ", ", Arrays.asList("zero", "one", "two", "three", "four", "five")),
            Arguments.of("zero; one; two; three; four; five", "; ", Arrays.asList("zero", "one", "two", "three", "four", "five")),
            Arguments.of("zero | one | two | three | four | five", "; ", Collections.singletonList("zero | one | two | three | four | five")),
            Arguments.of("zero; one; two; three; four; five", "", Collections.singletonList("zero; one; two; three; four; five")),
            Arguments.of("zero; one; two; three; four; five", null, Collections.singletonList("zero; one; two; three; four; five"))

    );
  }

  private static Stream<Arguments> getFromListOrDefaultProvider() {
    return Stream.of(
            Arguments.of(SAMPLE_VALUES, 0, null, SAMPLE_VALUES.get(0)),
            Arguments.of(SAMPLE_VALUES, 1, null, SAMPLE_VALUES.get(1)),
            Arguments.of(SAMPLE_VALUES, -1, DEFAULT_VALUE, DEFAULT_VALUE),
            Arguments.of(SAMPLE_VALUES, Integer.MAX_VALUE, null, null),
            Arguments.of(null, 1, DEFAULT_VALUE, DEFAULT_VALUE)
    );
  }

}