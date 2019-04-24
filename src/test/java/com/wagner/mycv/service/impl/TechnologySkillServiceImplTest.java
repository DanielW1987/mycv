package com.wagner.mycv.service.impl;

import com.wagner.mycv.model.entity.TechnologySkill;
import com.wagner.mycv.model.repository.TechnologySkillRepository;
import com.wagner.mycv.testutil.TechnologySkillTestUtil;
import com.wagner.mycv.web.dto.TechnologySkillDto;
import com.wagner.mycv.web.dto.request.TechnologySkillRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class TechnologySkillServiceImplTest {

  private TechnologySkill programmingSkillEntity;
  private TechnologySkill testsSkillEntity;

  private TechnologySkillRequestDto programmingRequestDto;
  private TechnologySkillRequestDto testsRequestDto;

  @InjectMocks
  private TechnologySkillServiceImpl technologySkillService;

  @Mock
  private TechnologySkillRepository technologySkillRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    programmingSkillEntity = TechnologySkillTestUtil.createTestEntity("Programmierung",
            Arrays.asList("Java", "Spring / Spring Boot"));

    testsSkillEntity      = TechnologySkillTestUtil.createTestEntity("Tests", Arrays.asList("JUnit", "Mockito", "Rest Assured"));
    programmingRequestDto = TechnologySkillTestUtil.createProgrammingSkillRequestDto();
    testsRequestDto       = TechnologySkillTestUtil.createTestsSkillRequestDto();
  }

  @Test
  void test_findAll() {
    // given
    List<TechnologySkill> technologySkills = Arrays.asList(programmingSkillEntity, testsSkillEntity);
    when(technologySkillRepository.findAll()).thenReturn(technologySkills);

    // when
    List<TechnologySkillDto> actualTechnologySkillList = technologySkillService.findAll();

    // then
    assertEquals(2, actualTechnologySkillList.size());
    verify(technologySkillRepository, times(1)).findAll();

    List<TechnologySkillDto> expectedTechnologySkillList = technologySkills.stream()
            .map(entity -> new ModelMapper().map(entity, TechnologySkillDto.class))
            .collect(Collectors.toList());

    for (int index = 0; index < expectedTechnologySkillList.size(); index++) {
      TechnologySkillDto actual   = actualTechnologySkillList.get(index);
      TechnologySkillDto expected = expectedTechnologySkillList.get(index);
      assertEquals(expected.getCategory(), actual.getCategory());
      assertEquals(expected.getSkillNames(), actual.getSkillNames());
      assertEquals(expected.getUserId(), actual.getUserId());
    }
  }

  @Test
  void test_find() {
    // given
    when(technologySkillRepository.findById(anyLong())).thenReturn(Optional.of(programmingSkillEntity));

    // when
    Optional<TechnologySkillDto> technologySkillDto = technologySkillService.find(1);

    // then
    assertTrue(technologySkillDto.isPresent());
    assertThatDtoAndEntityAreEqual(programmingSkillEntity, technologySkillDto.get());
    verify(technologySkillRepository, times(1)).findById(anyLong());
  }

  @Test
  void test_find_return_empty_optional() {
    // given
    when(technologySkillRepository.findById(anyLong())).thenReturn(Optional.empty());

    // when
    Optional<TechnologySkillDto> technologySkillDto = technologySkillService.find(1);

    // then
    assertFalse(technologySkillDto.isPresent());
    verify(technologySkillRepository, times(1)).findById(anyLong());
  }

  @Test
  void test_create() {
    // given
    when(technologySkillRepository.save(any(TechnologySkill.class))).thenReturn(programmingSkillEntity);

    // when
    TechnologySkillDto technologySkillDto = technologySkillService.create(programmingRequestDto);

    // then
    assertNotNull(technologySkillDto);
    assertThatRequestDtoAndDtoAreEqual(programmingRequestDto, technologySkillDto);
    verify(technologySkillRepository, times(1)).save(any(TechnologySkill.class));
  }

  @Test
  void test_create_all() {
    // given
    when(technologySkillRepository.saveAll(any())).thenReturn(Arrays.asList(programmingSkillEntity, testsSkillEntity));

    // when
    List<TechnologySkillRequestDto> technologySkillRequestDtos = Arrays.asList(programmingRequestDto, testsRequestDto);
    List<TechnologySkillDto> technologySkillDtos = technologySkillService.createAll(technologySkillRequestDtos);

    // then
    assertEquals(2, technologySkillDtos.size());
    verify(technologySkillRepository, times(1)).saveAll(any());

    for (int index = 0; index < technologySkillRequestDtos.size(); index++) {
      assertThatRequestDtoAndDtoAreEqual(technologySkillRequestDtos.get(index), technologySkillDtos.get(index));
    }
  }

  @Test
  void test_update() {
    // given
    when(technologySkillRepository.findById(1L)).thenReturn(Optional.of(programmingSkillEntity));
    when(technologySkillRepository.save(any(TechnologySkill.class))).thenReturn(programmingSkillEntity);

    // when
    Optional<TechnologySkillDto> technologySkillDto = technologySkillService.update(1, programmingRequestDto);

    // then
    assertNotNull(technologySkillDto);
    assertTrue(technologySkillDto.isPresent());
    assertThatRequestDtoAndDtoAreEqual(programmingRequestDto, technologySkillDto.get());
  }

  @Test
  void test_update_not_existing_entity() {
    // given
    when(technologySkillRepository.findById(1L)).thenReturn(Optional.empty());

    // when
    Optional<TechnologySkillDto> technologySkillDto = technologySkillService.update(1, programmingRequestDto);

    // then
    assertNotNull(technologySkillDto);
    assertFalse(technologySkillDto.isPresent());
  }

  @Test
  void test_delete_existing() {
    // given
    when(technologySkillRepository.existsById(1L)).thenReturn(true);

    // when
    boolean actual = technologySkillService.delete(1L);

    // then
    assertTrue(actual);
  }

  @Test
  void test_delete_not_existing() {
    // given
    when(technologySkillRepository.existsById(1L)).thenReturn(false);

    // when
    boolean actual = technologySkillService.delete(1L);

    // then
    assertFalse(actual);
  }

  private void assertThatDtoAndEntityAreEqual(TechnologySkill entity, TechnologySkillDto dto) {
    assertEquals(entity.getId(), dto.getId());
    assertEquals(entity.getCategory(), dto.getCategory());
    assertEquals(entity.getSkillNames(), dto.getSkillNames());
    assertEquals(entity.getUserId(), dto.getUserId());
  }

  private void assertThatRequestDtoAndDtoAreEqual(TechnologySkillRequestDto requestDto, TechnologySkillDto dto) {
    assertEquals(requestDto.getCategory(), dto.getCategory());
    assertEquals(requestDto.getSkillNames(), dto.getSkillNames());
  }
}