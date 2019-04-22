package com.wagner.mycv.service.impl;

import com.wagner.mycv.model.entity.ProgrammingProject;
import com.wagner.mycv.model.repository.ProgrammingProjectRepository;
import com.wagner.mycv.testutil.ProgrammingProjectTestUtil;
import com.wagner.mycv.utils.CollectionUtil;
import com.wagner.mycv.web.dto.ProgrammingProjectDto;
import com.wagner.mycv.web.dto.request.ProgrammingProjectRequestDto;
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

class ProgrammingProjectServiceImplTest {

  private ProgrammingProject myCvProjectEntity;
  private ProgrammingProject examedyProjectEntity;

  private ProgrammingProjectRequestDto myCvProjectRequestDto;
  private ProgrammingProjectRequestDto examedyRequestDto;

  @InjectMocks
  private ProgrammingProjectServiceImpl programmingProjectService;

  @Mock
  private ProgrammingProjectRepository programmingProjectRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    myCvProjectEntity     = ProgrammingProjectTestUtil.createTestEntity("My Beautiful CV", Arrays.asList("Spring Boot", "Angular"));
    examedyProjectEntity  = ProgrammingProjectTestUtil.createTestEntity("Examedy", Arrays.asList("Spring Boot", "Angular"));
    myCvProjectRequestDto = ProgrammingProjectTestUtil.createMyCvProjectRequestDto();
    examedyRequestDto     = ProgrammingProjectTestUtil.createExamedyProjectRequestDto();
  }

  @Test
  void test_findAll() {
    // given
    List<ProgrammingProject> programmingProjects = Arrays.asList(myCvProjectEntity, examedyProjectEntity);
    when(programmingProjectRepository.findAll()).thenReturn(programmingProjects);

    // when
    List<ProgrammingProjectDto> actualProgrammingProjectList = programmingProjectService.findAll();

    // then
    assertEquals(2, actualProgrammingProjectList.size());
    verify(programmingProjectRepository, times(1)).findAll();

    List<ProgrammingProjectDto> expectedProgrammingProjectList = programmingProjects.stream()
            .map(entity -> new ModelMapper().map(entity, ProgrammingProjectDto.class))
            .collect(Collectors.toList());

    for (int index = 0; index < expectedProgrammingProjectList.size(); index++) {
      assertEquals(expectedProgrammingProjectList.get(index), actualProgrammingProjectList.get(index));
    }
  }

  @Test
  void test_find() {
    // given
    when(programmingProjectRepository.findById(anyLong())).thenReturn(Optional.of(myCvProjectEntity));

    // when
    Optional<ProgrammingProjectDto> programmingProjectDto = programmingProjectService.find(1);

    // then
    assertTrue(programmingProjectDto.isPresent());
    assertThatDtoAndEntityAreEqual(myCvProjectEntity, programmingProjectDto.get());
    verify(programmingProjectRepository, times(1)).findById(anyLong());
  }

  @Test
  void test_find_return_empty_optional() {
    // given
    when(programmingProjectRepository.findById(anyLong())).thenReturn(Optional.empty());

    // when
    Optional<ProgrammingProjectDto> programmingProjectDto = programmingProjectService.find(1);

    // then
    assertFalse(programmingProjectDto.isPresent());
    verify(programmingProjectRepository, times(1)).findById(anyLong());
  }

  @Test
  void test_create() {
    // given
    when(programmingProjectRepository.save(any(ProgrammingProject.class))).thenReturn(myCvProjectEntity);

    // when
    ProgrammingProjectDto programmingProjectDto = programmingProjectService.create(myCvProjectRequestDto);

    // then
    assertNotNull(programmingProjectDto);
    assertThatRequestDtoAndDtoAreEqual(myCvProjectRequestDto, programmingProjectDto);
    verify(programmingProjectRepository, times(1)).save(any(ProgrammingProject.class));
  }

  @Test
  void test_create_all() {
    // given
    when(programmingProjectRepository.saveAll(any())).thenReturn(Arrays.asList(myCvProjectEntity, examedyProjectEntity));

    // when
    List<ProgrammingProjectRequestDto> programmingProjectRequestDtos = Arrays.asList(myCvProjectRequestDto, examedyRequestDto);
    List<ProgrammingProjectDto> programmingProjectDtos = programmingProjectService.createAll(programmingProjectRequestDtos);

    // then
    assertEquals(2, programmingProjectDtos.size());
    verify(programmingProjectRepository, times(1)).saveAll(any());

    for (int index = 0; index < programmingProjectRequestDtos.size(); index++) {
      assertThatRequestDtoAndDtoAreEqual(programmingProjectRequestDtos.get(index), programmingProjectDtos.get(index));
    }
  }

  @Test
  void test_update() {
    // given
    when(programmingProjectRepository.findById(1L)).thenReturn(Optional.of(myCvProjectEntity));
    when(programmingProjectRepository.save(any(ProgrammingProject.class))).thenReturn(myCvProjectEntity);

    // when
    Optional<ProgrammingProjectDto> programmingProjectDto = programmingProjectService.update(1, myCvProjectRequestDto);

    // then
    assertNotNull(programmingProjectDto);
    assertTrue(programmingProjectDto.isPresent());
    assertThatRequestDtoAndDtoAreEqual(myCvProjectRequestDto, programmingProjectDto.get());
  }

  @Test
  void test_update_not_existing_entity() {
    // given
    when(programmingProjectRepository.findById(1L)).thenReturn(Optional.empty());

    // when
    Optional<ProgrammingProjectDto> programmingProjectDto = programmingProjectService.update(1, myCvProjectRequestDto);

    // then
    assertNotNull(programmingProjectDto);
    assertFalse(programmingProjectDto.isPresent());
  }

  @Test
  void test_delete_existing() {
    // given
    when(programmingProjectRepository.existsById(1L)).thenReturn(true);

    // when
    boolean actual = programmingProjectService.delete(1L);

    // then
    assertTrue(actual);
  }

  @Test
  void test_delete_not_existing() {
    // given
    when(programmingProjectRepository.existsById(1L)).thenReturn(false);

    // when
    boolean actual = programmingProjectService.delete(1L);

    // then
    assertFalse(actual);
  }

  private void assertThatDtoAndEntityAreEqual(ProgrammingProject entity, ProgrammingProjectDto dto) {
    assertEquals(entity.getId(), dto.getId());
    assertEquals(entity.getName(), dto.getName());
    assertEquals(entity.getTechnologiesUsed(), dto.getTechnologiesUsed());
    assertEquals(entity.getDescription(), dto.getDescription());
    assertEquals(entity.getVcsUrl(), dto.getVcsUrl());
    assertEquals(entity.getUserId(), dto.getUserId());
  }

  private void assertThatRequestDtoAndDtoAreEqual(ProgrammingProjectRequestDto requestDto, ProgrammingProjectDto dto) {
    assertEquals(requestDto.getName(), dto.getName());
    assertEquals(requestDto.getTechnologiesUsed(), dto.getTechnologiesUsed());
    assertEquals(requestDto.getDescription(), dto.getDescription());
    assertEquals(requestDto.getVcsUrl(), dto.getVcsUrl());
    assertEquals(requestDto.getUserId(), dto.getUserId());
  }
}