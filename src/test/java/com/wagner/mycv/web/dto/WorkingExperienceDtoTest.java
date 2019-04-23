package com.wagner.mycv.web.dto;

import com.wagner.mycv.testutil.WorkingExperienceTestUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkingExperienceDtoTest {

  @Test
  void test_ui_sequence_comparator() {
    // given
    Comparator<WorkingExperienceDto> comparator = new WorkingExperienceDto.UISequenceComparator();
    WorkingExperienceDto w1 = WorkingExperienceTestUtil.createDummyDto(1, LocalDate.of(2018, 1, 1), null);
    WorkingExperienceDto w2 = WorkingExperienceTestUtil.createDummyDto(2, LocalDate.of(2018, 6, 1), null);
    WorkingExperienceDto w3 = WorkingExperienceTestUtil.createDummyDto(3, LocalDate.of(2019, 1, 1), null);
    WorkingExperienceDto w4 = WorkingExperienceTestUtil.createDummyDto(4, LocalDate.of(2018, 1, 1), LocalDate.of(2018, 12, 31));
    WorkingExperienceDto w5 = WorkingExperienceTestUtil.createDummyDto(5, LocalDate.of(2019, 1, 1), LocalDate.now());

    // when
    List<WorkingExperienceDto> workingExperiences = Arrays.asList(w1, w2, w3, w4, w5);
    Collections.shuffle(workingExperiences);
    workingExperiences.sort(comparator.reversed());

    // then
    List<WorkingExperienceDto> expected = Arrays.asList(w3, w2, w1, w5, w4);
    assertEquals(expected, workingExperiences);
  }

}