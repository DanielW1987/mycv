package com.wagner.mycv.service.impl;

import com.wagner.mycv.model.entity.Education;
import com.wagner.mycv.model.repository.EducationRepository;
import com.wagner.mycv.testutil.EducationTestUtil;
import com.wagner.mycv.web.dto.EducationDto;
import com.wagner.mycv.web.dto.request.EducationRequestDto;
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

class EducationServiceImplTest {

  private Education bachelorEducationEntity;
  private Education masterEducationEntity;

  private EducationRequestDto bachelorEducationRequestDto;
  private EducationRequestDto masterEducationRequestDto;

  @InjectMocks
  private EducationServiceImpl educationService;

  @Mock
  private EducationRepository educationRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    bachelorEducationEntity     = EducationTestUtil.createTestEntity("Hochschule für Technik und Wirtschaft Berlin", "B. Sc. Wirtschaftsinformatik");
    masterEducationEntity       = EducationTestUtil.createTestEntity("Hochschule für Technik und Wirtschaft Berlin", "M. Sc. Wirtschaftsinformatik");
    bachelorEducationRequestDto = EducationTestUtil.createBachelorEducationRequestDto();
    masterEducationRequestDto   = EducationTestUtil.createMasterEducationRequestDto();
  }

  @Test
  void test_findAll() {
    // given
    List<Education> educations = Arrays.asList(bachelorEducationEntity, masterEducationEntity);
    when(educationRepository.findAll(any(Sort.class))).thenReturn(educations);

    // when
    List<EducationDto> actualEducationList = educationService.findAll();

    // then
    assertEquals(2, actualEducationList.size());
    verify(educationRepository, times(1)).findAll(any(Sort.class));

    List<EducationDto> expectedEducationList = educations.stream()
            .map(entity -> new ModelMapper().map(entity, EducationDto.class))
            .collect(Collectors.toList());

    for (int index = 0; index < expectedEducationList.size(); index++) {
      assertEquals(expectedEducationList.get(index), actualEducationList.get(index));
    }
  }

  @Test
  void test_find() {
    // given
    when(educationRepository.findById(anyLong())).thenReturn(Optional.of(bachelorEducationEntity));

    // when
    Optional<EducationDto> educationDto = educationService.find(1);

    // then
    assertTrue(educationDto.isPresent());
    assertThatDtoAndEntityAreEqual(bachelorEducationEntity, educationDto.get());
    verify(educationRepository, times(1)).findById(anyLong());
  }

  @Test
  void test_find_return_empty_optional() {
    // given
    when(educationRepository.findById(anyLong())).thenReturn(Optional.empty());

    // when
    Optional<EducationDto> educationDto = educationService.find(1);

    // then
    assertFalse(educationDto.isPresent());
    verify(educationRepository, times(1)).findById(anyLong());
  }

  @Test
  void test_create() {
    // given
    when(educationRepository.save(any(Education.class))).thenReturn(bachelorEducationEntity);

    // when
    EducationDto educationDto = educationService.create(bachelorEducationRequestDto);

    // then
    assertNotNull(educationDto);
    assertThatRequestDtoAndDtoAreEqual(bachelorEducationRequestDto, educationDto);
    verify(educationRepository, times(1)).save(any(Education.class));
  }

  @Test
  void test_create_all() {
    // given
    when(educationRepository.saveAll(any())).thenReturn(Arrays.asList(bachelorEducationEntity, masterEducationEntity));

    // when
    List<EducationRequestDto> educationRequestDtos = Arrays.asList(bachelorEducationRequestDto, masterEducationRequestDto);
    List<EducationDto> educationDtos = educationService.createAll(educationRequestDtos);

    // then
    assertEquals(2, educationDtos.size());
    verify(educationRepository, times(1)).saveAll(any());

    for (int index = 0; index < educationRequestDtos.size(); index++) {
      assertThatRequestDtoAndDtoAreEqual(educationRequestDtos.get(index), educationDtos.get(index));
    }
  }

  @Test
  void test_update() {
    // given
    when(educationRepository.findById(1L)).thenReturn(Optional.of(bachelorEducationEntity));
    when(educationRepository.save(any(Education.class))).thenReturn(bachelorEducationEntity);

    // when
    Optional<EducationDto> educationDto = educationService.update(1, bachelorEducationRequestDto);

    // then
    assertNotNull(educationDto);
    assertTrue(educationDto.isPresent());
    assertThatRequestDtoAndDtoAreEqual(bachelorEducationRequestDto, educationDto.get());
  }

  @Test
  void test_update_not_existing_entity() {
    // given
    when(educationRepository.findById(1L)).thenReturn(Optional.empty());

    // when
    Optional<EducationDto> educationDto = educationService.update(1, bachelorEducationRequestDto);

    // then
    assertNotNull(educationDto);
    assertFalse(educationDto.isPresent());
  }

  @Test
  void test_delete_existing() {
    // given
    when(educationRepository.existsById(1L)).thenReturn(true);

    // when
    boolean actual = educationService.delete(1L);

    // then
    assertTrue(actual);
  }

  @Test
  void test_delete_not_existing() {
    // given
    when(educationRepository.existsById(1L)).thenReturn(false);

    // when
    boolean actual = educationService.delete(1L);

    // then
    assertFalse(actual);
  }

  private void assertThatDtoAndEntityAreEqual(Education entity, EducationDto dto) {
    assertEquals(entity.getId(), dto.getId());
    assertEquals(entity.getFacility(), dto.getFacility());
    assertEquals(entity.getGraduation(), dto.getGraduation());
    assertEquals(entity.getBegin(), dto.getBegin());
    assertEquals(entity.getEnd(), dto.getEnd());
    assertEquals(entity.getUserId(), dto.getUserId());
  }

  private void assertThatRequestDtoAndDtoAreEqual(EducationRequestDto requestDto, EducationDto dto) {
    assertEquals(requestDto.getFacility(), dto.getFacility());
    assertEquals(requestDto.getGraduation(), dto.getGraduation());
    assertEquals(requestDto.getBegin(), dto.getBegin());
    assertEquals(requestDto.getEnd(), dto.getEnd());
  }
}