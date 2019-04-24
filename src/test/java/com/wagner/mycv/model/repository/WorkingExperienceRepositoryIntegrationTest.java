package com.wagner.mycv.model.repository;

import com.wagner.mycv.model.entity.WorkingExperience;
import com.wagner.mycv.testutil.WorkingExperienceTestUtil;
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
class WorkingExperienceRepositoryIntegrationTest {

  private final WorkingExperience workingExperience = WorkingExperienceTestUtil.createTestEntity();

  @Autowired private DataSource dataSource;
  @Autowired private JdbcTemplate jdbcTemplate;
  @Autowired private EntityManager entityManager;
  @Autowired private WorkingExperienceRepository workingExperienceRepository;

  @Test
  void injectedComponentsAreNotNull(){
    assertThat(dataSource).isNotNull();
    assertThat(jdbcTemplate).isNotNull();
    assertThat(entityManager).isNotNull();
    assertThat(workingExperienceRepository).isNotNull();
  }

  @Test
  void test_find_working_experience() {
    entityManager.persist(workingExperience);
    Optional<WorkingExperience> result = workingExperienceRepository.findById(1L);
    assertThat(result.isPresent()).isTrue();

    //noinspection OptionalGetWithoutIsPresent
    WorkingExperience workingExperienceResponse = result.get();

    assertThat(workingExperienceResponse).isNotNull();
    assertThat(workingExperienceResponse.getId()).isEqualTo(1L);
    assertThat(workingExperienceResponse.getBegin()).isEqualTo(workingExperience.getBegin());
    assertThat(workingExperienceResponse.getEnd()).isEqualTo(workingExperience.getEnd());
    assertThat(workingExperienceResponse.getCompany()).isEqualTo(workingExperience.getCompany());
    assertThat(workingExperienceResponse.getJobTitle()).isEqualTo(workingExperience.getJobTitle());
    assertThat(workingExperienceResponse.getPlaceOfWork()).isEqualTo(workingExperience.getPlaceOfWork());
    assertThat(workingExperienceResponse.getFocalPoint1()).isEqualTo(workingExperience.getFocalPoint1());
    assertThat(workingExperienceResponse.getFocalPoint2()).isEqualTo(workingExperience.getFocalPoint2());
    assertThat(workingExperienceResponse.getFocalPoint3()).isEqualTo(workingExperience.getFocalPoint3());
    assertThat(workingExperienceResponse.getFocalPoint4()).isEqualTo(workingExperience.getFocalPoint4());
    assertThat(workingExperienceResponse.getUserId()).isEqualTo(workingExperience.getUserId());
  }
}