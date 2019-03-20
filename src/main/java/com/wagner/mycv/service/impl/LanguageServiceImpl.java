package com.wagner.mycv.service.impl;

import com.wagner.mycv.model.entity.Language;
import com.wagner.mycv.model.repository.LanguageRepository;
import com.wagner.mycv.service.LanguageService;
import com.wagner.mycv.web.dto.request.LanguageRequestDto;
import com.wagner.mycv.web.dto.LanguageDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LanguageServiceImpl implements LanguageService {

  private final LanguageRepository languageRepository;
  private final ModelMapper        modelMapper;

  @Autowired
  public LanguageServiceImpl(LanguageRepository languageRepository) {
    this.languageRepository = languageRepository;
    this.modelMapper        = new ModelMapper();
  }

  @Override
  public List<LanguageDto> findAll() {
    return languageRepository.findAll()
            .stream()
            .map(language -> modelMapper.map(language, LanguageDto.class))
            .collect(Collectors.toList());
  }

  @Override
  public Optional<LanguageDto> find(long id) {
    Optional<Language> language = languageRepository.findById(id);

    return language.map(value -> modelMapper.map(value, LanguageDto.class));
  }

  @Override
  public LanguageDto create(LanguageRequestDto request) {
    Language language = modelMapper.map(request, Language.class);
    languageRepository.save(language);

    return modelMapper.map(language, LanguageDto.class);
  }

  @Override
  public List<LanguageDto> createAll(Iterable<LanguageRequestDto> request) {
    List<Language> languages = new ArrayList<>();
    request.forEach(languageRequestDto -> languages.add(modelMapper.map(languageRequestDto, Language.class)));

    languageRepository.saveAll(languages);

    return languages.stream()
            .map(language -> modelMapper.map(language, LanguageDto.class))
            .collect(Collectors.toList());
  }

  @Override
  public Optional<LanguageDto> update(long id, LanguageRequestDto request) {
    Optional<Language> languageOptional = languageRepository.findById(id);
    LanguageDto languageResponse        = null;

    if (languageOptional.isPresent()) {
      Language language = languageOptional.get();
      modelMapper.map(request, language);

      languageRepository.save(language);
      languageResponse = modelMapper.map(language, LanguageDto.class);
    }

    return Optional.ofNullable(languageResponse);
  }

  @Override
  public boolean delete(long id) {
    if (languageRepository.existsById(id)) {
      languageRepository.deleteById(id);
      return true;
    }

    // entity doesn't exist
    return false;
  }
}
