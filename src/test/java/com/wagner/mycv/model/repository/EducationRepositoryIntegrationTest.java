package com.wagner.mycv.model.repository;

import com.wagner.mycv.model.entity.Education;
import com.wagner.mycv.testutil.EducationTestUtil;
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
class EducationRepositoryIntegrationTest {

  private final Education education = EducationTestUtil.createTestEntity();

  @Autowired private DataSource dataSource;
  @Autowired private JdbcTemplate jdbcTemplate;
  @Autowired private EntityManager entityManager;
  @Autowired private EducationRepository educationRepository;

  @Test
  void injectedComponentsAreNotNull(){
    assertThat(dataSource).isNotNull();
    assertThat(jdbcTemplate).isNotNull();
    assertThat(entityManager).isNotNull();
    assertThat(educationRepository).isNotNull();
  }

  @Test
  void test_find_education() {
    entityManager.persist(education);
    Optional<Education> result = educationRepository.findById(1L);
    assertThat(result.isPresent()).isTrue();

    //noinspection OptionalGetWithoutIsPresent
    Education educationResponse = result.get();

    assertThat(educationResponse).isNotNull();
    assertThat(educationResponse.getId()).isEqualTo(1L);
    assertThat(educationResponse.getBegin()).isEqualTo(education.getBegin());
    assertThat(educationResponse.getEnd()).isEqualTo(education.getEnd());
    assertThat(educationResponse.getFacility()).isEqualTo(education.getFacility());
    assertThat(educationResponse.getGraduation()).isEqualTo(education.getGraduation());
    assertThat(educationResponse.getUserId()).isEqualTo(education.getUserId());
  }

}