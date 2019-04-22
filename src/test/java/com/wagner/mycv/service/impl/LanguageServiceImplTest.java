package com.wagner.mycv.service.impl;

import com.wagner.mycv.model.entity.Language;
import com.wagner.mycv.model.repository.LanguageRepository;
import com.wagner.mycv.testutil.LanguageTestUtil;
import com.wagner.mycv.web.dto.LanguageDto;
import com.wagner.mycv.web.dto.request.LanguageRequestDto;
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

class LanguageServiceImplTest {

  private Language germanLanguageEntity;
  private Language englishLanguageEntity;

  private LanguageRequestDto germanRequestDto;
  private LanguageRequestDto englishRequestDto;

  @InjectMocks
  private LanguageServiceImpl languageService;

  @Mock
  private LanguageRepository languageRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    germanLanguageEntity   = LanguageTestUtil.createTestEntity("Deutsch", (byte) 100);
    englishLanguageEntity  = LanguageTestUtil.createTestEntity("Englisch", (byte) 60);
    germanRequestDto       = LanguageTestUtil.createGermanLanguageRequestDto();
    englishRequestDto      = LanguageTestUtil.createEnglishLanguageRequestDto();
  }

  @Test
  void test_findAll() {
    // given
    List<Language> languages = Arrays.asList(germanLanguageEntity, englishLanguageEntity);
    when(languageRepository.findAll()).thenReturn(languages);

    // when
    List<LanguageDto> actualLanguageList = languageService.findAll();

    // then
    assertEquals(2, actualLanguageList.size());
    verify(languageRepository, times(1)).findAll();

    List<LanguageDto> expectedLanguageList = languages.stream()
            .map(entity -> new ModelMapper().map(entity, LanguageDto.class))
            .collect(Collectors.toList());

    for (int index = 0; index < expectedLanguageList.size(); index++) {
      assertEquals(expectedLanguageList.get(index), actualLanguageList.get(index));
    }
  }

  @Test
  void test_find() {
    // given
    when(languageRepository.findById(anyLong())).thenReturn(Optional.of(germanLanguageEntity));

    // when
    Optional<LanguageDto> languageDto = languageService.find(1);

    // then
    assertTrue(languageDto.isPresent());
    assertThatDtoAndEntityAreEqual(germanLanguageEntity, languageDto.get());
    verify(languageRepository, times(1)).findById(anyLong());
  }

  @Test
  void test_find_return_empty_optional() {
    // given
    when(languageRepository.findById(anyLong())).thenReturn(Optional.empty());

    // when
    Optional<LanguageDto> languageDto = languageService.find(1);

    // then
    assertFalse(languageDto.isPresent());
    verify(languageRepository, times(1)).findById(anyLong());
  }

  @Test
  void test_create() {
    // given
    when(languageRepository.save(any(Language.class))).thenReturn(germanLanguageEntity);

    // when
    LanguageDto languageDto = languageService.create(germanRequestDto);

    // then
    assertNotNull(languageDto);
    assertThatRequestDtoAndDtoAreEqual(germanRequestDto, languageDto);
    verify(languageRepository, times(1)).save(any(Language.class));
  }

  @Test
  void test_create_all() {
    // given
    when(languageRepository.saveAll(any())).thenReturn(Arrays.asList(germanLanguageEntity, englishLanguageEntity));

    // when
    List<LanguageRequestDto> languageRequestDtos = Arrays.asList(germanRequestDto, englishRequestDto);
    List<LanguageDto> languageDtos = languageService.createAll(languageRequestDtos);

    // then
    assertEquals(2, languageDtos.size());
    verify(languageRepository, times(1)).saveAll(any());

    for (int index = 0; index < languageRequestDtos.size(); index++) {
      assertThatRequestDtoAndDtoAreEqual(languageRequestDtos.get(index), languageDtos.get(index));
    }
  }

  @Test
  void test_update() {
    // given
    when(languageRepository.findById(1L)).thenReturn(Optional.of(germanLanguageEntity));
    when(languageRepository.save(any(Language.class))).thenReturn(germanLanguageEntity);

    // when
    Optional<LanguageDto> languageDto = languageService.update(1, germanRequestDto);

    // then
    assertNotNull(languageDto);
    assertTrue(languageDto.isPresent());
    assertThatRequestDtoAndDtoAreEqual(germanRequestDto, languageDto.get());
  }

  @Test
  void test_update_not_existing_entity() {
    // given
    when(languageRepository.findById(1L)).thenReturn(Optional.empty());

    // when
    Optional<LanguageDto> languageDto = languageService.update(1, germanRequestDto);

    // then
    assertNotNull(languageDto);
    assertFalse(languageDto.isPresent());
  }

  @Test
  void test_delete_existing() {
    // given
    when(languageRepository.existsById(1L)).thenReturn(true);

    // when
    boolean actual = languageService.delete(1L);

    // then
    assertTrue(actual);
  }

  @Test
  void test_delete_not_existing() {
    // given
    when(languageRepository.existsById(1L)).thenReturn(false);

    // when
    boolean actual = languageService.delete(1L);

    // then
    assertFalse(actual);
  }

  private void assertThatDtoAndEntityAreEqual(Language entity, LanguageDto dto) {
    assertEquals(entity.getId(), dto.getId());
    assertEquals(entity.getName(), dto.getName());
    assertEquals(entity.getLevel(), dto.getLevel());
    assertEquals(entity.getUserId(), dto.getUserId());
  }

  private void assertThatRequestDtoAndDtoAreEqual(LanguageRequestDto requestDto, LanguageDto dto) {
    assertEquals(requestDto.getName(), dto.getName());
    assertEquals(requestDto.getLevel(), dto.getLevel());
    assertEquals(requestDto.getUserId(), dto.getUserId());
  }
}