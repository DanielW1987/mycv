package com.wagner.mycv.service.impl;

import com.wagner.mycv.model.entity.WorkingExperience;
import com.wagner.mycv.model.repository.WorkingExperienceRepository;
import com.wagner.mycv.testutil.WorkingExperienceTestUtil;
import com.wagner.mycv.web.dto.WorkingExperienceDto;
import com.wagner.mycv.web.dto.request.WorkingExperienceRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class WorkingExperienceServiceImplTest {

  private WorkingExperience javaConsultantWorkingExperienceEntity;
  private WorkingExperience javaDeveloperWorkingExperienceEntity;

  private WorkingExperienceRequestDto javaConsultantRequestDto;
  private WorkingExperienceRequestDto javaDeveloperRequestDto;

  @InjectMocks
  private WorkingExperienceServiceImpl workingExperienceService;

  @Mock
  private WorkingExperienceRepository workingExperienceRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    javaConsultantWorkingExperienceEntity = WorkingExperienceTestUtil.createTestEntity("John Doe Company", "Java Consultant");
    javaDeveloperWorkingExperienceEntity  = WorkingExperienceTestUtil.createTestEntity("John Doe Company", "Java Developer");
    javaConsultantRequestDto              = WorkingExperienceTestUtil.createJavaConsultantRequestDto();
    javaDeveloperRequestDto               = WorkingExperienceTestUtil.createJavaDeveloperRequestDto();
  }

  @Test
  void test_findAll() {
    // given
    List<WorkingExperience> workingExperiences = Arrays.asList(javaConsultantWorkingExperienceEntity, javaDeveloperWorkingExperienceEntity);
    when(workingExperienceRepository.findAll()).thenReturn(workingExperiences);

    // when
    List<WorkingExperienceDto> actualWorkingExperienceList = workingExperienceService.findAll();

    // then
    assertEquals(2, actualWorkingExperienceList.size());
    verify(workingExperienceRepository, times(1)).findAll();

    List<WorkingExperienceDto> expectedWorkingExperienceList = workingExperiences.stream()
            .map(entity -> new ModelMapper().map(entity, WorkingExperienceDto.class))
            .collect(Collectors.toList());

    for (int index = 0; index < expectedWorkingExperienceList.size(); index++) {
      assertEquals(expectedWorkingExperienceList.get(index), actualWorkingExperienceList.get(index));
    }
  }

  @Test
  void test_find() {
    // given
    when(workingExperienceRepository.findById(anyLong())).thenReturn(Optional.of(javaConsultantWorkingExperienceEntity));

    // when
    Optional<WorkingExperienceDto> workingExperienceDto = workingExperienceService.find(1);

    // then
    assertTrue(workingExperienceDto.isPresent());
    assertThatDtoAndEntityAreEqual(javaConsultantWorkingExperienceEntity, workingExperienceDto.get());
    verify(workingExperienceRepository, times(1)).findById(anyLong());
  }

  @Test
  void test_find_return_empty_optional() {
    // given
    when(workingExperienceRepository.findById(anyLong())).thenReturn(Optional.empty());

    // when
    Optional<WorkingExperienceDto> workingExperienceDto = workingExperienceService.find(1);

    // then
    assertFalse(workingExperienceDto.isPresent());
    verify(workingExperienceRepository, times(1)).findById(anyLong());
  }

  @Test
  void test_create() {
    // given
    when(workingExperienceRepository.save(any(WorkingExperience.class))).thenReturn(javaConsultantWorkingExperienceEntity);

    // when
    WorkingExperienceDto workingExperienceDto = workingExperienceService.create(javaConsultantRequestDto);

    // then
    assertNotNull(workingExperienceDto);
    assertThatRequestDtoAndDtoAreEqual(javaConsultantRequestDto, workingExperienceDto);
    verify(workingExperienceRepository, times(1)).save(any(WorkingExperience.class));
  }

  @Test
  void test_create_all() {
    // given
    when(workingExperienceRepository.saveAll(any())).thenReturn(Arrays.asList(javaConsultantWorkingExperienceEntity, javaDeveloperWorkingExperienceEntity));

    // when
    List<WorkingExperienceRequestDto> workingExperienceRequestDtos = Arrays.asList(javaConsultantRequestDto, javaDeveloperRequestDto);
    List<WorkingExperienceDto> workingExperienceDtos = workingExperienceService.createAll(workingExperienceRequestDtos);

    // then
    assertEquals(2, workingExperienceDtos.size());
    verify(workingExperienceRepository, times(1)).saveAll(any());

    for (int index = 0; index < workingExperienceRequestDtos.size(); index++) {
      assertThatRequestDtoAndDtoAreEqual(workingExperienceRequestDtos.get(index), workingExperienceDtos.get(index));
    }
  }

  @Test
  void test_update() {
    // given
    when(workingExperienceRepository.findById(1L)).thenReturn(Optional.of(javaConsultantWorkingExperienceEntity));
    when(workingExperienceRepository.save(any(WorkingExperience.class))).thenReturn(javaConsultantWorkingExperienceEntity);

    // when
    Optional<WorkingExperienceDto> workingExperienceDto = workingExperienceService.update(1, javaConsultantRequestDto);

    // then
    assertNotNull(workingExperienceDto);
    assertTrue(workingExperienceDto.isPresent());
    assertThatRequestDtoAndDtoAreEqual(javaConsultantRequestDto, workingExperienceDto.get());
  }

  @Test
  void test_update_not_existing_entity() {
    // given
    when(workingExperienceRepository.findById(1L)).thenReturn(Optional.empty());

    // when
    Optional<WorkingExperienceDto> workingExperienceDto = workingExperienceService.update(1, javaConsultantRequestDto);

    // then
    assertNotNull(workingExperienceDto);
    assertFalse(workingExperienceDto.isPresent());
  }

  @Test
  void test_delete_existing() {
    // given
    when(workingExperienceRepository.existsById(1L)).thenReturn(true);

    // when
    boolean actual = workingExperienceService.delete(1L);

    // then
    assertTrue(actual);
  }

  @Test
  void test_delete_not_existing() {
    // given
    when(workingExperienceRepository.existsById(1L)).thenReturn(false);

    // when
    boolean actual = workingExperienceService.delete(1L);

    // then
    assertFalse(actual);
  }

  private void assertThatDtoAndEntityAreEqual(WorkingExperience entity, WorkingExperienceDto dto) {
    assertEquals(entity.getId(), dto.getId());
    assertEquals(entity.getCompany(), dto.getCompany());
    assertEquals(entity.getJobTitle(), dto.getJobTitle());
    assertEquals(entity.getPlaceOfWork(), dto.getPlaceOfWork());
    assertEquals(entity.getBegin(), dto.getBegin());
    assertEquals(entity.getEnd(), dto.getEnd());
    assertEquals(entity.getFocalPoints(), dto.getFocalPoints());
    assertEquals(entity.getUserId(), dto.getUserId());
  }

  private void assertThatRequestDtoAndDtoAreEqual(WorkingExperienceRequestDto requestDto, WorkingExperienceDto dto) {
    assertEquals(requestDto.getCompany(), dto.getCompany());
    assertEquals(requestDto.getJobTitle(), dto.getJobTitle());
    assertEquals(requestDto.getPlaceOfWork(), dto.getPlaceOfWork());
    assertEquals(requestDto.getBegin(), dto.getBegin());
    assertEquals(requestDto.getEnd(), dto.getEnd());
    assertEquals(requestDto.getFocalPoints(), dto.getFocalPoints());
  }
}