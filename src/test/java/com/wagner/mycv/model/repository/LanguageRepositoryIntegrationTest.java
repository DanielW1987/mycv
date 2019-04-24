package com.wagner.mycv.model.repository;

import com.wagner.mycv.model.entity.Language;
import com.wagner.mycv.testutil.LanguageTestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class LanguageRepositoryIntegrationTest {

  private final Language language = LanguageTestUtil.createTestEntity();

  @Autowired private DataSource dataSource;
  @Autowired private JdbcTemplate jdbcTemplate;
  @Autowired private EntityManager entityManager;
  @Autowired private LanguageRepository languageRepository;

  @Test
  void injectedComponentsAreNotNull(){
    assertThat(dataSource).isNotNull();
    assertThat(jdbcTemplate).isNotNull();
    assertThat(entityManager).isNotNull();
    assertThat(languageRepository).isNotNull();
  }

  @Test
  void test_find_language() {
    entityManager.persist(language);
    Optional<Language> result = languageRepository.findById(1L);
    assertThat(result.isPresent()).isTrue();

    //noinspection OptionalGetWithoutIsPresent
    Language languageResponse = result.get();

    assertThat(languageResponse).isNotNull();
    assertThat(languageResponse.getId()).isEqualTo(1L);
    assertThat(languageResponse.getLevel()).isEqualTo(language.getLevel());
    assertThat(languageResponse.getName()).isEqualTo(language.getName());
    assertThat(languageResponse.getUserId()).isEqualTo(language.getUserId());
  }

}